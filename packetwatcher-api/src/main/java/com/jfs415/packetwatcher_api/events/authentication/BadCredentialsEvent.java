package com.jfs415.packetwatcher_api.events.authentication;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

import com.jfs415.packetwatcher_api.events.PacketWatcherParentEvent;
import com.jfs415.packetwatcher_api.model.services.EventService;
import com.jfs415.packetwatcher_api.model.services.UserActivationStateService;

@Component
public class BadCredentialsEvent extends PacketWatcherParentEvent implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

	private final UserActivationStateService userActivationStateService;

	@Autowired
	public BadCredentialsEvent(HttpServletRequest request, EventService eventService, UserActivationStateService userActivationStateService) {
		super(request, eventService);
		
		this.userActivationStateService = userActivationStateService;
	}

	@Override
	public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
		final String ipAddress = getIpAddressFromRequest(request);

		if (event.getSource() instanceof UsernamePasswordAuthenticationToken) {
			saveVerifiedAuthenticationEvent(event, ipAddress, AuthenticationEventType.BAD_CREDENTIALS_ATTEMPT);
			userActivationStateService.handleFailedUserLogin((String) ((UsernamePasswordAuthenticationToken) event.getSource()).getPrincipal(), event.getTimestamp());
		} else {
			saveDefaultAuthenticationEvent(event, ipAddress, AuthenticationEventType.BAD_CREDENTIALS_ATTEMPT);
		}
	}

}
