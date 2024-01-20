package com.jfs415.packetwatcher_api.views;

import com.jfs415.packetwatcher_api.model.user.Authority;

public record UserProfileView(
        String username, String firstName, String lastName, String email, String phone, Authority level) {}
