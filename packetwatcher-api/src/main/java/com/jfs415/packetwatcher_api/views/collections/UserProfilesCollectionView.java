package com.jfs415.packetwatcher_api.views.collections;

import com.jfs415.packetwatcher_api.views.UserProfileView;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Immutable;

@AllArgsConstructor
@Getter
@Immutable
public class UserProfilesCollectionView {

    private final List<UserProfileView> profiles;

    public UserProfilesCollectionView() {
        this.profiles = new ArrayList<>();
    }
}
