package com.jfs415.packetwatcher_api.model.services.inf;

import com.jfs415.packetwatcher_api.exceptions.UserNotFoundException;
import com.jfs415.packetwatcher_api.exceptions.args.InvalidArgumentException;
import org.springframework.security.core.Authentication;

public interface AuthService {

    String logUserIn(Authentication authentication) throws InvalidArgumentException;

    boolean logUserOut(String token) throws UserNotFoundException, InvalidArgumentException;
}
