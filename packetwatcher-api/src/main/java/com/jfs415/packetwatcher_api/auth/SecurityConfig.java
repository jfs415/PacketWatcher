package com.jfs415.packetwatcher_api.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.jfs415.packetwatcher_api.PacketWatcherApi;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private PasswordEncoder encoder;

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		if (encoder == null) {
			encoder = new BCryptPasswordEncoder(PacketWatcherApi.getPropertiesManager().getPasswordStrength());
		}

		return encoder;
	}

	@Bean
	public SecurityFilterChain configure(HttpSecurity http) throws Exception {
		return http.csrf(AbstractHttpConfigurer::disable)
				   .cors().disable()
		           .authorizeRequests(auth -> auth.antMatchers("/", "/login", "/accounts/**").permitAll())
		           .httpBasic(Customizer.withDefaults()).build();
	}

}
