package com.jfs415.packetwatcher_api.events.authentication;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationFailureLockedEvent;
import org.springframework.stereotype.Component;

import com.jfs415.packetwatcher_api.events.PacketWatcherParentEvent;
import com.jfs415.packetwatcher_api.model.services.EventService;

@Component
public class LockedCredentialEvent extends PacketWatcherParentEvent implements ApplicationListener<AuthenticationFailureLockedEvent> {

	@Autowired
	public LockedCredentialEvent(HttpServletRequest request, EventService eventService) {
		super(request, eventService);
	}

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
