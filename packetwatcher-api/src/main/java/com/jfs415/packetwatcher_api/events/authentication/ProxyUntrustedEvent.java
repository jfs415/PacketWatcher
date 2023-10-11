package com.jfs415.packetwatcher_api.events.authentication;

import com.jfs415.packetwatcher_api.events.PacketWatcherParentEvent;
import com.jfs415.packetwatcher_api.model.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationFailureProxyUntrustedEvent;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class ProxyUntrustedEvent extends PacketWatcherParentEvent implements ApplicationListener<AuthenticationFailureProxyUntrustedEvent> {

    @Autowired
    public ProxyUntrustedEvent(HttpServletRequest request, EventService eventService) {
        super(request, eventService);
    }

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
