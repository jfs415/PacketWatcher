package com.jfs415.packetwatcher_api.events.authorization;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import com.jfs415.packetwatcher_api.events.PacketWatcherParentEvent;
import com.jfs415.packetwatcher_api.model.services.EventService;

@Component
public class AuthorizationGrantedEvent extends PacketWatcherParentEvent implements ApplicationListener<org.springframework.security.authorization.event.AuthorizationGrantedEvent<?>> {

	@Autowired
	public AuthorizationGrantedEvent(HttpServletRequest request, EventService eventService) {
		super(request, eventService);
	}

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
