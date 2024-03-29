package com.jfs415.packetwatcher_api.model.services;

import com.jfs415.packetwatcher_api.auth.JwtUtilImpl;
import com.jfs415.packetwatcher_api.auth.SecurityConfigImpl;
import com.jfs415.packetwatcher_api.auth.inf.JwtUtil;
import com.jfs415.packetwatcher_api.auth.inf.SecurityConfig;
import com.jfs415.packetwatcher_api.exceptions.UserNotFoundException;
import com.jfs415.packetwatcher_api.model.repositories.UserRepository;
import com.jfs415.packetwatcher_api.model.services.inf.UserService;
import com.jfs415.packetwatcher_api.model.user.User;
import com.jfs415.packetwatcher_api.model.user.UserActivationState;
import com.jfs415.packetwatcher_api.model.user.UserParams;
import com.jfs415.packetwatcher_api.views.UserProfileView;
import com.jfs415.packetwatcher_api.views.collections.UserProfilesCollectionView;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Concrete implementation class that handles functionality outlined in the UserService interface.
 * The adding, accessing, and removal of password reset requests in the underlying storage
 * of this implementation is thread safe.
 *
 * @see UserService
 */
@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    private final UserRepository userRepo;

    private final JavaMailSender mailSender;

    private final SecurityConfig securityConfigImpl;

    private final JwtUtil jwtUtilImpl;

    @Value("${spring.mail.password}")
    private String sender;

    private final ConcurrentHashMap<String, Long> passwordResetTimestamps = new ConcurrentHashMap<>();

    private static final long FIVE_MINUTES = 300000;

    @Autowired
    public UserServiceImpl(
            UserRepository userRepo,
            JavaMailSender mailSender,
            SecurityConfigImpl securityConfigImpl,
            JwtUtilImpl jwtUtilImpl) {
        this.userRepo = userRepo;
        this.mailSender = mailSender;
        this.securityConfigImpl = securityConfigImpl;
        this.jwtUtilImpl = jwtUtilImpl;
    }

    @Override
    public User createUser(UserParams userParams) {
        return new User(userParams);
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        userRepo.saveAndFlush(user);
    }

    @Override
    @Transactional
    public void deleteUser(User user) {
        userRepo.deleteById(user.getUsername());
    }

    @Override
    @Transactional
    public UserProfileView updateUser(UserProfileView updatedProfile) {
        Optional<User> user = userRepo.findByUsername(updatedProfile.username());

        if (user.isEmpty()) {
            throw new UserNotFoundException();
        }

        User existingUser = user.get();
        existingUser.updateFromProfile(updatedProfile);
        saveUser(existingUser);

        return updatedProfile;
    }

    @Override
    public UserProfileView getUserProfile(String token) throws UserNotFoundException {
        String username = jwtUtilImpl.getUsername(token);
        return userRepo.findByUsername(username)
                .orElseThrow(UserNotFoundException::new)
                .toUserProfileView();
    }

    @Override
    @Transactional
    public User getUserByUsername(String username) throws UserNotFoundException {
        return userRepo.findByUsername(username).orElseThrow(UserNotFoundException::new);
    }

    @Override
    @Transactional
    public User getUserByEmail(String email) throws UserNotFoundException {
        return userRepo.findByEmail(email).orElseThrow(UserNotFoundException::new);
    }

    @Override
    @Transactional
    public UserProfilesCollectionView getAllUserProfilesWithLevelLessThanEqual(String token)
            throws UserNotFoundException {
        String username = jwtUtilImpl.getUsername(token);
        Optional<User> optionalUser = userRepo.findByUsername(username);

        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException();
        }

        return new UserProfilesCollectionView(
                userRepo.findAllByLevelIsLessThanEqual(optionalUser.get().getLevel()).stream()
                        .map(User::toUserProfileView)
                        .toList());
    }

    @Override
    @Transactional
    public UserProfilesCollectionView getLockedUserProfiles() {
        return new UserProfilesCollectionView(userRepo.findAllByUserActivationState(UserActivationState.LOCKED).stream()
                .map(User::toUserProfileView)
                .toList());
    }

    @Override
    @Transactional
    public boolean isEmailInUse(String email) {
        return userRepo.existsByEmail(email);
    }

    @Override
    @Transactional
    public boolean isUsernameInUse(String username) {
        return userRepo.existsByUsername(username);
    }

    @Override
    @Transactional
    public boolean isCorrectPasswordResetToken(String requestToken, String storedToken) {
        return securityConfigImpl.passwordEncoder().matches(requestToken, storedToken);
    }

    @Override
    @Transactional
    public void setPasswordResetToken(String email, String token) throws UserNotFoundException {
        User user = getUserByEmail(email);
        user.setPasswordResetToken(securityConfigImpl.passwordEncoder().encode(token));

        saveUser(user);
    }

    @Override
    @Transactional
    public void updatePassword(User user, String password) {
        String encodedPassword = securityConfigImpl.passwordEncoder().encode(password);
        user.setPassword(encodedPassword);
        user.setPasswordResetToken(null);

        passwordResetTimestamps.remove(user.getEmail());

        saveUser(user);
    }

    @Override
    public void sendPasswordResetEmail(String email, String token) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(sender);
        helper.setTo(email);

        String subject = "Password reset link";

        String content = "<p>Hello,</p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><a href=\"" + "http://localhost:3000/accounts/reset/" + token + "\">Change my password</a></p>"
                + "<br>"
                + "<p>Please ignore this email if you have not made the request.</p>";

        helper.setSubject(subject);
        helper.setText(content, true);
        mailSender.send(message);
    }

    @Override
    public void addPasswordResetTimestamp(String email, long timestamp) {
        passwordResetTimestamps.put(email, timestamp);
    }

    @Override
    public void purgeExpiredPasswordResetRequests() {
        passwordResetTimestamps.entrySet().removeIf(e -> e.getValue() <= System.currentTimeMillis() - FIVE_MINUTES);
    }

    @Override
    @Transactional
    public void handleAccountRecoveryInitiation(long timestamp, String email) throws MessagingException {
        String token = RandomString.make(30);
        setPasswordResetToken(email, token);
        sendPasswordResetEmail(email, token);
        addPasswordResetTimestamp(email, timestamp);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }
}
