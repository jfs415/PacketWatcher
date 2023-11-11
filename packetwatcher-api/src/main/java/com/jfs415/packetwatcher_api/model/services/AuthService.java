package com.jfs415.packetwatcher_api.model.services;

import com.jfs415.packetwatcher_api.auth.JwtUtil;
import com.jfs415.packetwatcher_api.exceptions.UserNotFoundException;
import com.jfs415.packetwatcher_api.exceptions.args.InvalidArgumentException;
import com.jfs415.packetwatcher_api.model.services.inf.UserService;
import com.jfs415.packetwatcher_api.model.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthService.class);

    private final UserAuthCacheService userAuthCacheService;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    @Autowired
    public AuthService(UserAuthCacheService userAuthCacheService, JwtUtil jwtUtil, UserService userService) {
        this.userAuthCacheService = userAuthCacheService;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    public String logUserIn(Authentication authentication) throws InvalidArgumentException {
        User user = (User) authentication.getPrincipal();
        String token = jwtUtil.generateToken(user);
        userAuthCacheService.addUserToken(token);

        return token;
    }

    public boolean logUserOut(String token) throws UserNotFoundException, InvalidArgumentException {
        try {
            String username = jwtUtil.getUsername(token);
            userService.getUserByUsername(username);
            userAuthCacheService.removeUserToken(token);
            
            return !userAuthCacheService.hasToken(token);
        } catch (UserNotFoundException e) {
            LOGGER.error(e.getMessage(), e);
            return false;
        }
    }

}
