package com.jfs415.packetwatcher_api.model.user;

import java.io.Serializable;
import java.util.Collection;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.jfs415.packetwatcher_api.PacketWatcherApi;

@Entity
@Table(name = "users", schema = "packetwatcher")
public class User implements Serializable, UserDetails {
	
	@Id
	@Column(name = "uuid", unique = true)
	private final UUID uuid = UUID.randomUUID();

	@Column(name = "username", length = 16, unique = true)
	private String username;

	@Column(name = "password", length = 256)
	private String password;

	@Column(name = "first")
	private String first;

	@Column(name = "last")
	private String last = "Doe";

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

	public User() { }

	public User(UserParams userParams) {
		this.username = userParams.getUsername();
		this.password = userParams.getPassword();
		this.first = userParams.getFirst();
		this.last = userParams.getLast();
		this.email = userParams.getEmail();
		this.phone = userParams.getPhone();
		this.level = Authority.USER;
		this.userActivationState = UserActivationState.UNCONFIRMED; //Unconfirmed until they activate via email
	}

	public UUID getUuid() {
		return uuid;
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

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirst() {
		return first;
	}

	public void setFirst(String first) {
		this.first = first;
	}

	public String getLast() {
		return last;
	}

	public void setLast(String last) {
		this.last = last;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Authority getAuthority() {
		return level;
	}

	public void setAuthority(Authority level) {
		this.level = level;
	}

	public void save() {
		PacketWatcherApi.getUserService().saveUser(this);
	}
	
	public void delete() {
		PacketWatcherApi.getUserService().deleteUser(this);
	}
	
	public UserView toUserView() {
		return new UserView(this);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof User) {
			User other = (User) obj;
			return this.email.equals(other.email) && this.first.equals(other.first)
			       && this.last.equals(other.last) && this.username.equals(other.username) && this.level == other.level
			       && this.uuid.equals(other.uuid) && this.password.equals(other.password)
			       && (this.phone == null || other.phone == null || this.phone.equals(other.phone))
			       && this.userActivationState == other.userActivationState;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return 31 * (email.hashCode() + first.hashCode() + last.hashCode() + username.hashCode()
		             + level.hashCode() + uuid.hashCode() + password.hashCode()
		             + (phone != null ? phone.hashCode() : 0)
		             + userActivationState.hashCode());
	}

	public static class UserView {

		private final String username;
		private final String firstName;
		private final String lastName;
		
		public UserView(User user) {
			this.username = user.getUsername();
			this.firstName = user.getFirst();
			this.lastName = user.getLast();
		}

		public UserView(String username, String firstName, String lastName) {
			this.username = username;
			this.firstName = firstName;
			this.lastName = lastName;
		}

		public String getUsername() {
			return username;
		}

		public String getFirstName() {
			return firstName;
		}

		public String getLastName() {
			return lastName;
		}

	}

}
