package com.jfs415.packetwatcher_api.events.authentication;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationFailureProxyUntrustedEvent;

import com.jfs415.packetwatcher_api.events.PacketWatcherParentEvent;

public class ProxyUntrustedEvent extends PacketWatcherParentEvent implements ApplicationListener<AuthenticationFailureProxyUntrustedEvent> {

	@Autowired
	private HttpServletRequest request;
	
	@Override
	public void onApplicationEvent(AuthenticationFailureProxyUntrustedEvent event) {
		final String ipAddress = getIpAddressFromRequest(request);

		if (event.getSource() instanceof UsernamePasswordAuthenticationToken) {
			saveVerifiedAuthenticationEvent(event, ipAddress, AuthenticationEventType.PROXY_UNTRUSTED_ATTEMPT);
		} else {
			saveDefaultAuthenticationEvent(event, ipAddress, AuthenticationEventType.PROXY_UNTRUSTED_ATTEMPT);
		}
	}

}
