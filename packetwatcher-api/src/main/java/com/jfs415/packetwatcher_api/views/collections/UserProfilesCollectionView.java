package com.jfs415.packetwatcher_api.views.collections;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Immutable;

import com.jfs415.packetwatcher_api.views.UserProfileView;

@Immutable
public class UserProfilesCollectionView implements Serializable {

	private final List<UserProfileView> profiles;

	public UserProfilesCollectionView() {
		this.profiles = new ArrayList<>();
	}

	public UserProfilesCollectionView(List<UserProfileView> profiles) {
		this.profiles = profiles;
	}

	public List<UserProfileView> getProfiles() {
		return profiles;
	}

}
