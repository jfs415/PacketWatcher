package com.jfs415.packetwatcher_api.auth;

import com.jfs415.packetwatcher_api.auth.inf.JwtUtil;
import com.jfs415.packetwatcher_api.model.services.inf.UserService;
import io.jsonwebtoken.lang.Strings;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtilImpl;
    private final UserService userService;

    @Autowired
    public JwtFilter(JwtUtilImpl jwtUtilImpl, UserService userService) {
        this.jwtUtilImpl = jwtUtilImpl;
        this.userService = userService;
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // Get authorization header and validate
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null || Strings.hasText(header) || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        // Get jwt token and validate (bearer dsa.sadasdasdsa -> ([bearer] [dsa.sadasdasdsa])
        final String token = header.split(" ")[1].trim();

        // Get user identity and set it on the spring security context
        UserDetails userDetails = userService.getUserByUsername(jwtUtilImpl.getUsername(token));

        if (userDetails == null || !jwtUtilImpl.validateToken(token, userDetails.getUsername())) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }
}
