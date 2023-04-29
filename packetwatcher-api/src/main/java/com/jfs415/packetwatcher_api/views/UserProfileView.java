package com.jfs415.packetwatcher_api.views;

import java.io.Serializable;

import org.springframework.data.annotation.Immutable;

import com.jfs415.packetwatcher_api.model.user.User;

@Immutable
public final class UserProfileView implements Serializable {

	private final String username;
	private final String firstName;
	private final String lastName;
	private final String email;
	private final String phone;

	public UserProfileView(User user) {
		this.username = user.getUsername();
		this.firstName = user.getFirst();
		this.lastName = user.getLast();
		this.email = user.getEmail();
		this.phone = user.getPhone();
	}

	public UserProfileView(String username, String firstName, String lastName, String email, String phone) {
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
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

	public String getEmail() {
		return email;
	}

	public String getPhone() {
		return phone;
	}

}