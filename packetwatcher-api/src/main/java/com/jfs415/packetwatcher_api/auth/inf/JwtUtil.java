package com.jfs415.packetwatcher_api.auth.inf;

import io.jsonwebtoken.Claims;
import java.util.Date;
import java.util.function.Function;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtUtil {

    String getUsername(String token);

    Date getExpiration(String token);

    <T> T getClaim(String token, Function<Claims, T> claimsResolver);

    String generateToken(UserDetails userDetails);

    String generateToken(String username);

    Boolean validateToken(String token, String username);
}
