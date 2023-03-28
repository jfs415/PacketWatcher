package com.jfs415.packetwatcher_api.model.user;

import javax.validation.constraints.NotBlank;

import org.springframework.lang.Nullable;

public class UserParams {

	/**
	 * POJO to hold @RequestParam values to create a User object.
	 * Purpose of this class is to reduce number of parameters passed
	 * to NewUserController::process.
	 */

	@NotBlank(message = "First name cannot be empty!")
	private String first;

	@NotBlank(message = "Last name cannot be empty!")
	private String last;

	@NotBlank(message = "Email name cannot be empty!")
	private String email;

	@Nullable
	private String phone;

	@NotBlank(message = "Username cannot be empty!")
	private String username;

	@NotBlank(message = "Password cannot be empty!")
	private String password;

	public UserParams(String first, String last, String email, @Nullable String phone, String username, String password) {
		this.first = first;
		this.last = last;
		this.email = email;
		this.phone = phone;
		this.username = username;
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

	@Nullable
	public String getPhone() {
		return phone;
	}

	public void setPhone(@Nullable String phone) {
		this.phone = phone;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return first + " " + last + " " + email + " " + phone + " " + username + " " + password;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof UserParams) {
			UserParams other = (UserParams) obj;
			return this.username.equals(other.username) && this.password.equals(other.password) && this.first.equals(other.first) &&
			       this.last.equals(other.last) && this.email.equals(other.email);
		}

		return false;
	}

	@Override
	public int hashCode() {
		return 31 * (username.hashCode() + password.hashCode() + email.hashCode() + first.hashCode() + this.last.hashCode());
	}
	
}
