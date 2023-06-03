package com.jfs415.packetwatcher_api.events.authentication;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationFailureDisabledEvent;
import org.springframework.stereotype.Component;

import com.jfs415.packetwatcher_api.events.PacketWatcherParentEvent;
import com.jfs415.packetwatcher_api.model.services.EventService;

@Component
public class DisabledCredentialsEvent extends PacketWatcherParentEvent implements ApplicationListener<AuthenticationFailureDisabledEvent> {

	@Autowired
	private DisabledCredentialsEvent(HttpServletRequest httpServletRequest, EventService eventService) {
		super(httpServletRequest, eventService);
	}

	@Override
	public void onApplicationEvent(AuthenticationFailureDisabledEvent event) {
		final String ipAddress = getIpAddressFromRequest(request);

		if (event.getSource() instanceof UsernamePasswordAuthenticationToken) {
			saveVerifiedAuthenticationEvent(event, ipAddress, AuthenticationEventType.DISABLED_LOGIN_ATTEMPT);
		} else {
			saveDefaultAuthenticationEvent(event, ipAddress, AuthenticationEventType.DISABLED_LOGIN_ATTEMPT);
		}
	}

}
