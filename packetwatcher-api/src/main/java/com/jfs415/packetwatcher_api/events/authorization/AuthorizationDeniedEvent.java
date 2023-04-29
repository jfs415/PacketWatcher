package com.jfs415.packetwatcher_api.events.authorization;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.jfs415.packetwatcher_api.events.PacketWatcherParentEvent;

public class AuthorizationDeniedEvent extends PacketWatcherParentEvent implements ApplicationListener<org.springframework.security.authorization.event.AuthorizationDeniedEvent<?>> {

	@Autowired
	private HttpServletRequest request;

	@Override
	public void onApplicationEvent(org.springframework.security.authorization.event.AuthorizationDeniedEvent<?> event) {
		final String ipAddress = getIpAddressFromRequest(request);

		if (event.getSource() instanceof UsernamePasswordAuthenticationToken) {
			saveVerifiedAuthorizationEvent(event, ipAddress, AuthorizationEventType.DENIED);
		} else {
			saveDefaultAuthorizationEvent(event, ipAddress, AuthorizationEventType.DENIED);
		}
	}

}
