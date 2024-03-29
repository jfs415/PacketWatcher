package com.jfs415.packetwatcher_api.events.authentication;

import com.jfs415.packetwatcher_api.events.PacketWatcherParentEvent;
import com.jfs415.packetwatcher_api.model.services.inf.EventService;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationFailureCredentialsExpiredEvent;
import org.springframework.stereotype.Component;

@Component
public class CredentialExpiredEvent extends PacketWatcherParentEvent
        implements ApplicationListener<AuthenticationFailureCredentialsExpiredEvent> {

    @Autowired
    public CredentialExpiredEvent(HttpServletRequest request, EventService eventService) {
        super(request, eventService);
    }

    @Override
    public void onApplicationEvent(AuthenticationFailureCredentialsExpiredEvent event) {
        final String ipAddress = getIpAddressFromRequest(request);

        if (event.getSource() instanceof UsernamePasswordAuthenticationToken) {
            saveVerifiedAuthenticationEvent(event, ipAddress, AuthenticationEventType.EXPIRED_CREDENTIALS_ATTEMPT);
        } else {
            saveDefaultAuthenticationEvent(event, ipAddress, AuthenticationEventType.EXPIRED_CREDENTIALS_ATTEMPT);
        }
    }
}
