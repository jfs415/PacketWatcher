package com.jfs415.packetwatcher_api.events.authentication;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationFailureLockedEvent;

import com.jfs415.packetwatcher_api.events.PacketWatcherParentEvent;

public class LockedCredentialEvent extends PacketWatcherParentEvent implements ApplicationListener<AuthenticationFailureLockedEvent> {

	@Autowired
	private HttpServletRequest request;

	@Override
	public void onApplicationEvent(AuthenticationFailureLockedEvent event) {
		final String ipAddress = getIpAddressFromRequest(request);
		
		if (event.getSource() instanceof UsernamePasswordAuthenticationToken) {
			saveVerifiedAuthenticationEvent(event, ipAddress, AuthenticationEventType.LOCKED_LOGIN_ATTEMPT);
		} else {
			saveDefaultAuthenticationEvent(event, ipAddress, AuthenticationEventType.LOCKED_LOGIN_ATTEMPT);
		}
	}

}
