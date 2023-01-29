package com.jfs415.packetwatcher_api.model.services;

import java.util.Optional;

import javax.ws.rs.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jfs415.packetwatcher_api.model.repositories.UserRepository;
import com.jfs415.packetwatcher_api.model.user.User;
import com.jfs415.packetwatcher_api.model.user.UserParams;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepo;

	public User createUser(UserParams userParams) {
		String salt = BCrypt.gensalt(15); //Generate salt, larger number is better but slower. Recommended to play around with this to find the right balance
		//userParams.setPassword(); //Generate encrypted password with a hash

		return new User(userParams);
	}

	@Transactional
	public void saveUser(User user) {
		userRepo.saveAndFlush(user);
	}

	@Transactional
	public void deleteUser(User user) {
		userRepo.deleteById(user.getUuid());
	}

	public Optional<User> getUserByUsername(String username) {
		return userRepo.findByUsername(username);
	}

	public User getUserByEmail(String email) {
		return userRepo.findByEmail(email).orElseThrow(() -> new NotFoundException("User not found"));
	}

	public User getUserForRecovery(String usernameOrEmail) {
		return isValidEmail(usernameOrEmail) ? getUserByEmail(usernameOrEmail) : getUserByUsername(usernameOrEmail).orElse(null);
	}

	public User getUserByCredentials(String usernameOrEmail, String password) {
		User user = isValidEmail(usernameOrEmail) ? getUserByEmail(usernameOrEmail) : getUserByUsername(usernameOrEmail).orElse(null);
		return user != null ? BCrypt.checkpw(password, user.getPassword()) ? user : null : null;
	}

	private boolean isValidEmail(String usernameOrEmail) {
		return true;
	}

	public boolean isEmailInUse(String email) {
		return userRepo.existsByEmail(email);
	}

	public boolean isUsernameInUse(String username) {
		return userRepo.existsByUsername(username);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Invalid Credentials"));
	}

}
