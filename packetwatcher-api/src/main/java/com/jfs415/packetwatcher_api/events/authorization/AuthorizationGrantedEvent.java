package com.jfs415.packetwatcher_api.events.authorization;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.jfs415.packetwatcher_api.events.PacketWatcherParentEvent;

public class AuthorizationGrantedEvent extends PacketWatcherParentEvent implements ApplicationListener<org.springframework.security.authorization.event.AuthorizationGrantedEvent<?>> {

	@Autowired
	private HttpServletRequest request;
	
	@Override
	public void onApplicationEvent(org.springframework.security.authorization.event.AuthorizationGrantedEvent event) {
		final String ipAddress = getIpAddressFromRequest(request);

		if (event.getSource() instanceof UsernamePasswordAuthenticationToken) {
			saveVerifiedAuthorizationEvent(event, ipAddress, AuthorizationEventType.GRANTED);
		} else {
			saveDefaultAuthorizationEvent(event, ipAddress, AuthorizationEventType.GRANTED);
		}
	}

}
