package com.jfs415.packetwatcher_api.auth;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends AbstractHttpConfigurer<SecurityConfig, HttpSecurity> {

	@Autowired
	private JwtFilter jwtFilter;
	
	private PasswordEncoder encoder;

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		if (encoder == null) {
			encoder = new BCryptPasswordEncoder(16);
		}

		return encoder;
	}

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder(16);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http = http.csrf().disable().cors().disable();
		http = http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and();
		http = http.exceptionHandling().authenticationEntryPoint((request, response, ex) -> {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
		}).and();

		http.authorizeRequests().antMatchers("/api/auth/**").permitAll().anyRequest().authenticated();
		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
	}

}
