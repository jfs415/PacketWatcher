package com.jfs415.packetwatcher_api.events.authorization;

import com.jfs415.packetwatcher_api.events.PacketWatcherParentEvent;
import com.jfs415.packetwatcher_api.model.services.inf.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class AuthorizationDeniedEvent extends PacketWatcherParentEvent implements ApplicationListener<org.springframework.security.authorization.event.AuthorizationDeniedEvent<?>> {

    @Autowired
    public AuthorizationDeniedEvent(HttpServletRequest request, EventService eventService) {
        super(request, eventService);
    }

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
