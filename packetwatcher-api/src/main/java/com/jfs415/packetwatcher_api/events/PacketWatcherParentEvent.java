package com.jfs415.packetwatcher_api.events;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authorization.event.AuthorizationDeniedEvent;
import org.springframework.security.authorization.event.AuthorizationGrantedEvent;

import com.jfs415.packetwatcher_api.events.authentication.AuthenticationEventType;
import com.jfs415.packetwatcher_api.events.authorization.AuthorizationEventType;
import com.jfs415.packetwatcher_api.exceptions.InvalidEventArgumentException;
import com.jfs415.packetwatcher_api.model.events.AuthenticationEvent;
import com.jfs415.packetwatcher_api.model.events.AuthorizationEvent;
import com.jfs415.packetwatcher_api.model.services.EventService;

public class PacketWatcherParentEvent {

	@Autowired
	private EventService eventService;
	
	private static final String IP_ADDRESS_HEADER = "X-Forwarded-For";

	protected final void saveVerifiedAuthenticationEvent(AbstractAuthenticationFailureEvent e, String ipAddress, AuthenticationEventType eventType) {
		UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) e.getSource();
		eventService.saveAuthEvent(new AuthenticationEvent(e.getTimestamp(), ipAddress, (String) token.getPrincipal(), eventType));
	}

	protected final void saveVerifiedAuthorizationEvent(ApplicationEvent e, String ipAddress, AuthorizationEventType eventType) {
		UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) e.getSource();

		if (e instanceof AuthorizationDeniedEvent || e instanceof AuthorizationGrantedEvent) {
			eventService.saveAuthEvent(new AuthorizationEvent(e.getTimestamp(), ipAddress, (String) token.getPrincipal(), eventType));
		} else {
			throw new InvalidEventArgumentException();
		}
	}

	protected final void saveDefaultAuthenticationEvent(AbstractAuthenticationFailureEvent e, String ipAddress, AuthenticationEventType eventType) {
		eventService.saveAuthEvent(new AuthenticationEvent(e.getTimestamp(), ipAddress, eventType));
	}

	protected final void saveDefaultAuthorizationEvent(ApplicationEvent e, String ipAddress, AuthorizationEventType eventType) {
		if (e instanceof AuthorizationDeniedEvent || e instanceof AuthorizationGrantedEvent) {
			eventService.saveAuthEvent(new AuthorizationEvent(e.getTimestamp(), ipAddress, eventType));
		} else {
			throw new InvalidEventArgumentException();
		}
	}

	protected final String getIpAddressFromRequest(HttpServletRequest request) {
		final String xfHeader = request.getHeader(IP_ADDRESS_HEADER);
		return xfHeader == null || xfHeader.isEmpty() || !xfHeader.contains(request.getRemoteAddr()) ? request.getRemoteAddr() : xfHeader;
	}

}