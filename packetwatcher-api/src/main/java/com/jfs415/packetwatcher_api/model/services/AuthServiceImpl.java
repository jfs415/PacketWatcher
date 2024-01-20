package com.jfs415.packetwatcher_api.model.services;

import com.jfs415.packetwatcher_api.auth.JwtUtilImpl;
import com.jfs415.packetwatcher_api.exceptions.UserNotFoundException;
import com.jfs415.packetwatcher_api.exceptions.args.InvalidArgumentException;
import com.jfs415.packetwatcher_api.model.services.inf.AuthService;
import com.jfs415.packetwatcher_api.model.services.inf.UserService;
import com.jfs415.packetwatcher_api.model.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final UserAuthCacheServiceImpl userAuthCacheServiceImpl;
    private final JwtUtilImpl jwtUtilImpl;
    private final UserService userService;

    @Autowired
    public AuthServiceImpl(
            UserAuthCacheServiceImpl userAuthCacheServiceImpl, JwtUtilImpl jwtUtilImpl, UserService userService) {
        this.userAuthCacheServiceImpl = userAuthCacheServiceImpl;
        this.jwtUtilImpl = jwtUtilImpl;
        this.userService = userService;
    }

    @Override
    public String logUserIn(Authentication authentication) throws InvalidArgumentException {
        User user = (User) authentication.getPrincipal();
        String token = jwtUtilImpl.generateToken(user);
        userAuthCacheServiceImpl.addUserToken(token);

        return token;
    }

    @Override
    public boolean logUserOut(String token) throws UserNotFoundException, InvalidArgumentException {
        try {
            String username = jwtUtilImpl.getUsername(token);
            userService.getUserByUsername(username);
            userAuthCacheServiceImpl.removeUserToken(token);

            return !userAuthCacheServiceImpl.hasToken(token);
        } catch (UserNotFoundException e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }
}
