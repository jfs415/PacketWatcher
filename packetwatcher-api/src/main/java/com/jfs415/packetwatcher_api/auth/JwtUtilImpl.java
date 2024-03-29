package com.jfs415.packetwatcher_api.auth;

import com.jfs415.packetwatcher_api.auth.inf.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.Serializable;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtUtilImpl implements JwtUtil, Serializable {

    @Value("${packetwatcher-api.jwt.token-expiry}")
    private Duration tokenExpiry;

    @Value("${packetwatcher-api.jwt.secret}")
    private String secretKey = "secret";

    @Override
    public String getUsername(String token) {
        return getClaim(token, Claims::getSubject);
    }

    @Override
    public Date getExpiration(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    @Override
    public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        return generateToken(userDetails.getUsername());
    }

    @Override
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    @Override
    public Boolean validateToken(String token, String username) {
        final String lookedUpUsername = getUsername(token);
        return (lookedUpUsername.equals(username) && !isTokenExpired(token));
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiry.toMillis()))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return getExpiration(token).before(new Date());
    }
}
