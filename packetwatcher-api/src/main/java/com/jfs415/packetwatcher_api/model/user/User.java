package com.jfs415.packetwatcher_api.model.user;

import com.jfs415.packetwatcher_api.PacketWatcherApi;
import com.jfs415.packetwatcher_api.views.UserProfileView;
import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users", schema = "packetwatcher")
public class User implements Serializable, UserDetails {

    @Id
    @Column(name = "username", length = 16, unique = true)
    private String username;

    @Column(name = "password", length = 256)
    private String password;

    @Column(name = "first")
    private String first;

    @Column(name = "last")
    private String last;

    @Column(name = "email")
    private String email;

    @Column(name = "phone", length = 12)
    private String phone;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "level")
    private Authority level;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "activation_state")
    private UserActivationState userActivationState;

    @Column(name = "password_reset_token")
    private String passwordResetToken;

    @Column(name = "failed_login_attempts", nullable = false)
    private int failedLoginAttempts;

    public User(UserParams userParams) {
        this.username = userParams.getUsername();
        this.password = userParams.getPassword();
        this.first = userParams.getFirst();
        this.last = userParams.getLast();
        this.email = userParams.getEmail();
        this.phone = userParams.getPhone();
        this.level = Authority.USER;
        this.userActivationState = UserActivationState.UNCONFIRMED; // Unconfirmed until they activate via email
        this.passwordResetToken = null;
        this.failedLoginAttempts = 0;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return userActivationState != UserActivationState.EXPIRED;
    }

    @Override
    public boolean isAccountNonLocked() {
        return userActivationState != UserActivationState.LOCKED;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return userActivationState != UserActivationState.CREDENTIALS_EXPIRED;
    }

    @Override
    public boolean isEnabled() {
        return userActivationState == UserActivationState.ENABLED;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return PacketWatcherApi.getAuthoritySet();
    }

    @Override
    public String getPassword() {
        return password;
    }

    public UserProfileView toUserProfileView() {
        return new UserProfileView(username, first, last, email, phone, level);
    }

    public void updateFromProfile(UserProfileView updatedProfile) {
        this.first = updatedProfile.firstName();
        this.last = updatedProfile.lastName();
        this.email = updatedProfile.email();
        this.phone = updatedProfile.phone();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User user) {
            return this.email.equals(user.email)
                    && this.first.equals(user.first)
                    && this.last.equals(user.last)
                    && this.username.equals(user.username)
                    && this.level == user.level
                    && this.password.equals(user.password)
                    && ((this.phone == null && user.phone == null)
                            || Objects.requireNonNullElse(this.phone, "")
                                    .equals(Objects.requireNonNullElse(user.phone, "")))
                    && this.userActivationState == user.userActivationState
                    && failedLoginAttempts == user.failedLoginAttempts
                    && ((this.passwordResetToken == null && user.passwordResetToken == null)
                            || Objects.requireNonNullElse(this.passwordResetToken, "")
                                    .equals(Objects.requireNonNullElse(user.passwordResetToken, "")));
        }

        return false;
    }

    @Override
    public int hashCode() {
        return 31
                * (email.hashCode()
                        + first.hashCode()
                        + last.hashCode()
                        + username.hashCode()
                        + level.hashCode()
                        + password.hashCode()
                        + userActivationState.hashCode()
                        + failedLoginAttempts);
    }
}
