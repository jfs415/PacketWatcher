package com.jfs415.packetwatcher_api.views;

import com.jfs415.packetwatcher_api.model.user.Authority;
import com.jfs415.packetwatcher_api.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Immutable;

@AllArgsConstructor
@Getter
@Immutable
public final class UserProfileView {

    private final String username;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String phone;
    private final Authority level;

    public UserProfileView(User user) {
        this.username = user.getUsername();
        this.firstName = user.getFirst();
        this.lastName = user.getLast();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.level = user.getLevel();
    }
}
