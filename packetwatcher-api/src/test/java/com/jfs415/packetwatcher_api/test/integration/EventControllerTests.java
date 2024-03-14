package com.jfs415.packetwatcher_api.test.integration;

import com.jfs415.packetwatcher_api.PacketWatcherApi;
import com.jfs415.packetwatcher_api.controllers.AuthenticationEventController;
import com.jfs415.packetwatcher_api.controllers.AuthorizationEventController;
import com.jfs415.packetwatcher_api.views.collections.EventsCollectionView;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = PacketWatcherApi.class)
class EventControllerTests {
    
    private static final String USERNAME = "jhealy";
    private static final String IPV4_ADDRESS = "127.0.0.1";
    private static final String IPV6_ADDRESS = "::1";

    private final AuthenticationEventController authenticationEventController;

    private final AuthorizationEventController authorizationEventController;

    @Autowired
    public EventControllerTests(AuthenticationEventController authenticationEventController, AuthorizationEventController authorizationEventController) {
        this.authenticationEventController = authenticationEventController;
        this.authorizationEventController = authorizationEventController;
    }

    @Test
    void testAuthenticationEventsBefore() {
        ResponseEntity<EventsCollectionView> entity =
                authenticationEventController.getAuthenticationEventsBefore(System.currentTimeMillis());
        assertSuccessfulAndNonEmpty(entity);

        entity =
                authenticationEventController.getAuthenticationEventsBefore(-1);
        assertFailed(entity);

        entity =
                authenticationEventController.getAuthenticationEventsBefore(0);
        assertFailed(entity);

        entity =
                authenticationEventController.getAuthenticationEventsWithUsernameBefore(USERNAME, System.currentTimeMillis());
        assertSuccessful(entity);

        entity =
                authenticationEventController.getAuthenticationEventsWithUsernameBefore(USERNAME, -1);
        assertFailed(entity);

        entity =
                authenticationEventController.getAuthenticationEventsWithIpAddressBefore(IPV4_ADDRESS, System.currentTimeMillis());
        assertSuccessful(entity);

        entity =
                authenticationEventController.getAuthenticationEventsWithIpAddressBefore(IPV4_ADDRESS, -1);
        assertFailed(entity);

        entity =
                authenticationEventController.getAuthenticationEventsWithIpAddressBefore(IPV4_ADDRESS, 0);
        assertFailed(entity);

        entity =
                authenticationEventController.getAuthenticationEventsWithUsernameAndIpAddressBefore(USERNAME, IPV4_ADDRESS, System.currentTimeMillis());
        assertSuccessful(entity);

        entity =
                authenticationEventController.getAuthenticationEventsWithUsernameAndIpAddressBefore(USERNAME, IPV4_ADDRESS, -1);
        assertFailed(entity);

        entity =
                authenticationEventController.getAuthenticationEventsWithUsernameAndIpAddressBefore(USERNAME, IPV4_ADDRESS, 0);
        assertFailed(entity);
        
        entity =
                authenticationEventController.getAuthenticationEventsWithIpAddressBefore(IPV6_ADDRESS, System.currentTimeMillis());
        assertSuccessful(entity);

        entity =
                authenticationEventController.getAuthenticationEventsWithIpAddressBefore(IPV6_ADDRESS, -1);
        assertFailed(entity);

        entity =
                authenticationEventController.getAuthenticationEventsWithIpAddressBefore(IPV6_ADDRESS, 0);
        assertFailed(entity);

        entity =
                authenticationEventController.getAuthenticationEventsWithUsernameAndIpAddressBefore(USERNAME, IPV6_ADDRESS, System.currentTimeMillis());
        assertSuccessful(entity);

        entity =
                authenticationEventController.getAuthenticationEventsWithUsernameAndIpAddressBefore(USERNAME, IPV6_ADDRESS, -1);
        assertFailed(entity);

        entity =
                authenticationEventController.getAuthenticationEventsWithUsernameAndIpAddressBefore(USERNAME, IPV6_ADDRESS, 0);
        assertFailed(entity);
    }

    @Test
    void testAuthenticationEventsAfter() {
        ResponseEntity<EventsCollectionView> entity =
                authenticationEventController.getAuthenticationEventsAfter(System.currentTimeMillis() + 10000L);
        assertSuccessful(entity);

        entity =
                authenticationEventController.getAuthenticationEventsAfter(-1);
        assertFailed(entity);

        entity =
                authenticationEventController.getAuthenticationEventsAfter(0);
        assertFailed(entity);

        entity =
                authenticationEventController.getAuthenticationEventsWithUsernameAfter(USERNAME, System.currentTimeMillis());
        assertSuccessful(entity);

        entity =
                authenticationEventController.getAuthenticationEventsWithUsernameAfter(USERNAME, -1);
        assertFailed(entity);

        entity =
                authenticationEventController.getAuthenticationEventsWithIpAddressAfter(IPV4_ADDRESS, System.currentTimeMillis());
        assertSuccessful(entity);

        entity =
                authenticationEventController.getAuthenticationEventsWithIpAddressAfter(IPV4_ADDRESS, -1);
        assertFailed(entity);

        entity =
                authenticationEventController.getAuthenticationEventsWithIpAddressAfter(IPV4_ADDRESS, 0);
        assertFailed(entity);

        entity =
                authenticationEventController.getAuthenticationEventsWithUsernameAndIpAddressAfter(USERNAME, IPV4_ADDRESS, System.currentTimeMillis());
        assertSuccessful(entity);

        entity =
                authenticationEventController.getAuthenticationEventsWithUsernameAndIpAddressAfter(USERNAME, IPV4_ADDRESS, -1);
        assertFailed(entity);

        entity =
                authenticationEventController.getAuthenticationEventsWithUsernameAndIpAddressAfter(USERNAME, IPV4_ADDRESS, 0);
        assertFailed(entity);

        entity =
                authenticationEventController.getAuthenticationEventsWithIpAddressAfter(IPV6_ADDRESS, System.currentTimeMillis());
        assertSuccessful(entity);

        entity =
                authenticationEventController.getAuthenticationEventsWithIpAddressAfter(IPV6_ADDRESS, -1);
        assertFailed(entity);

        entity =
                authenticationEventController.getAuthenticationEventsWithIpAddressAfter(IPV6_ADDRESS, 0);
        assertFailed(entity);

        entity =
                authenticationEventController.getAuthenticationEventsWithUsernameAndIpAddressAfter(USERNAME, IPV6_ADDRESS, System.currentTimeMillis());
        assertSuccessful(entity);

        entity =
                authenticationEventController.getAuthenticationEventsWithUsernameAndIpAddressAfter(USERNAME, IPV6_ADDRESS, -1);
        assertFailed(entity);

        entity =
                authenticationEventController.getAuthenticationEventsWithUsernameAndIpAddressAfter(USERNAME, IPV6_ADDRESS, 0);
        assertFailed(entity);
    }

    @Test
    void testAuthenticationEventsBetween() {
        ResponseEntity<EventsCollectionView> entity =
                authenticationEventController.getAuthenticationEventsBetween(System.currentTimeMillis(), System.currentTimeMillis() + 10000L);
        assertSuccessful(entity);

        entity =
                authenticationEventController.getAuthenticationEventsBetween(-1, 0);
        assertFailed(entity);

        entity =
                authenticationEventController.getAuthenticationEventsBetween(0, -1);
        assertFailed(entity);

        entity =
                authenticationEventController.getAuthenticationEventsWithUsernameBetween(USERNAME, System.currentTimeMillis(), System.currentTimeMillis() + 10000L);
        assertSuccessful(entity);

        entity =
                authenticationEventController.getAuthenticationEventsWithUsernameBetween(USERNAME, -1, 0);
        assertFailed(entity);
        entity =
                authenticationEventController.getAuthenticationEventsWithUsernameBetween(USERNAME, 0, -1);
        assertFailed(entity);

        entity =
                authenticationEventController.getAuthenticationEventsWithIpAddressBetween(IPV4_ADDRESS, System.currentTimeMillis(), System.currentTimeMillis() + 10000L);
        assertSuccessful(entity);

        entity =
                authenticationEventController.getAuthenticationEventsWithIpAddressBetween(IPV4_ADDRESS, -1, 0);
        assertFailed(entity);

        entity =
                authenticationEventController.getAuthenticationEventsWithIpAddressBetween(IPV4_ADDRESS, 0, -1);
        assertFailed(entity);

        entity =
                authenticationEventController.getAuthenticationEventsWithUsernameAndIpAddressBetween(USERNAME, IPV4_ADDRESS, System.currentTimeMillis(), System.currentTimeMillis() + 10000L);
        assertSuccessful(entity);

        entity =
                authenticationEventController.getAuthenticationEventsWithUsernameAndIpAddressBetween(USERNAME, IPV4_ADDRESS, -1, 0);
        assertFailed(entity);

        entity =
                authenticationEventController.getAuthenticationEventsWithUsernameAndIpAddressBetween(USERNAME, IPV4_ADDRESS, 0, -1);
        assertFailed(entity);

        entity =
                authenticationEventController.getAuthenticationEventsWithIpAddressBetween(IPV6_ADDRESS, System.currentTimeMillis(), System.currentTimeMillis() + 10000L);
        assertSuccessful(entity);

        entity =
                authenticationEventController.getAuthenticationEventsWithIpAddressBetween(IPV6_ADDRESS, -1, 0);
        assertFailed(entity);

        entity =
                authenticationEventController.getAuthenticationEventsWithIpAddressBetween(IPV6_ADDRESS, 0, -1);
        assertFailed(entity);

        entity =
                authenticationEventController.getAuthenticationEventsWithUsernameAndIpAddressBetween(USERNAME, IPV6_ADDRESS, System.currentTimeMillis(), System.currentTimeMillis() + 10000L);
        assertSuccessful(entity);

        entity =
                authenticationEventController.getAuthenticationEventsWithUsernameAndIpAddressBetween(USERNAME, IPV6_ADDRESS, -1, 0);
        assertFailed(entity);

        entity =
                authenticationEventController.getAuthenticationEventsWithUsernameAndIpAddressBetween(USERNAME, IPV6_ADDRESS, 0, -1);
        assertFailed(entity);
    }

    @Test
    void testAuthorizationEventsBefore() {
        ResponseEntity<EventsCollectionView> entity =
                authorizationEventController.getAuthorizationEventsBefore(System.currentTimeMillis());
        assertSuccessful(entity);

        entity =
                authorizationEventController.getAuthorizationEventsBefore(-1);
        assertFailed(entity);

        entity =
                authorizationEventController.getAuthorizationEventsBefore(0);
        assertFailed(entity);

        entity =
                authorizationEventController.getAuthorizationEventsWithUsernameBefore(USERNAME, System.currentTimeMillis());
        assertSuccessful(entity);

        entity =
                authorizationEventController.getAuthorizationEventsWithUsernameBefore(USERNAME, -1);
        assertFailed(entity);

        entity =
                authorizationEventController.getAuthorizationEventsWithIpAddressBefore(IPV4_ADDRESS, System.currentTimeMillis());
        assertSuccessful(entity);

        entity =
                authorizationEventController.getAuthorizationEventsWithIpAddressBefore(IPV4_ADDRESS, -1);
        assertFailed(entity);

        entity =
                authorizationEventController.getAuthorizationEventsWithIpAddressBefore(IPV4_ADDRESS, 0);
        assertFailed(entity);

        entity =
                authorizationEventController.getAuthorizationEventsWithUsernameAndIpAddressBefore(USERNAME, IPV4_ADDRESS, System.currentTimeMillis());
        assertSuccessful(entity);

        entity =
                authorizationEventController.getAuthorizationEventsWithUsernameAndIpAddressBefore(USERNAME, IPV4_ADDRESS, -1);
        assertFailed(entity);

        entity =
                authorizationEventController.getAuthorizationEventsWithUsernameAndIpAddressBefore(USERNAME, IPV4_ADDRESS, 0);
        assertFailed(entity);

        entity =
                authorizationEventController.getAuthorizationEventsWithIpAddressBefore(IPV6_ADDRESS, System.currentTimeMillis());
        assertSuccessful(entity);

        entity =
                authorizationEventController.getAuthorizationEventsWithIpAddressBefore(IPV6_ADDRESS, -1);
        assertFailed(entity);

        entity =
                authorizationEventController.getAuthorizationEventsWithIpAddressBefore(IPV6_ADDRESS, 0);
        assertFailed(entity);

        entity =
                authorizationEventController.getAuthorizationEventsWithUsernameAndIpAddressBefore(USERNAME, IPV6_ADDRESS, System.currentTimeMillis());
        assertSuccessful(entity);

        entity =
                authorizationEventController.getAuthorizationEventsWithUsernameAndIpAddressBefore(USERNAME, IPV6_ADDRESS, -1);
        assertFailed(entity);

        entity =
                authorizationEventController.getAuthorizationEventsWithUsernameAndIpAddressBefore(USERNAME, IPV6_ADDRESS, 0);
        assertFailed(entity);
    }

    @Test
    void testAuthorizationEventsAfter() {
        ResponseEntity<EventsCollectionView> entity =
                authorizationEventController.getAuthorizationEventsAfter(System.currentTimeMillis() + 10000L);
        assertSuccessful(entity);

        entity =
                authorizationEventController.getAuthorizationEventsAfter(-1);
        assertFailed(entity);

        entity =
                authorizationEventController.getAuthorizationEventsAfter(0);
        assertFailed(entity);

        entity =
                authorizationEventController.getAuthorizationEventsWithUsernameAfter(USERNAME, System.currentTimeMillis());
        assertSuccessful(entity);

        entity =
                authorizationEventController.getAuthorizationEventsWithUsernameAfter(USERNAME, -1);
        assertFailed(entity);

        entity =
                authorizationEventController.getAuthorizationEventsWithIpAddressAfter(IPV4_ADDRESS, System.currentTimeMillis());
        assertSuccessful(entity);

        entity =
                authorizationEventController.getAuthorizationEventsWithIpAddressAfter(IPV4_ADDRESS, -1);
        assertFailed(entity);

        entity =
                authorizationEventController.getAuthorizationEventsWithIpAddressAfter(IPV4_ADDRESS, 0);
        assertFailed(entity);

        entity =
                authorizationEventController.getAuthorizationEventsWithUsernameAndIpAddressAfter(USERNAME, IPV4_ADDRESS, System.currentTimeMillis());
        assertSuccessful(entity);

        entity =
                authorizationEventController.getAuthorizationEventsWithUsernameAndIpAddressAfter(USERNAME, IPV4_ADDRESS, -1);
        assertFailed(entity);

        entity =
                authorizationEventController.getAuthorizationEventsWithUsernameAndIpAddressAfter(USERNAME, IPV4_ADDRESS, 0);
        assertFailed(entity);

        entity =
                authorizationEventController.getAuthorizationEventsWithIpAddressAfter(IPV6_ADDRESS, System.currentTimeMillis());
        assertSuccessful(entity);

        entity =
                authorizationEventController.getAuthorizationEventsWithIpAddressAfter(IPV6_ADDRESS, -1);
        assertFailed(entity);

        entity =
                authorizationEventController.getAuthorizationEventsWithIpAddressAfter(IPV6_ADDRESS, 0);
        assertFailed(entity);

        entity =
                authorizationEventController.getAuthorizationEventsWithUsernameAndIpAddressAfter(USERNAME, IPV6_ADDRESS, System.currentTimeMillis());
        assertSuccessful(entity);

        entity =
                authorizationEventController.getAuthorizationEventsWithUsernameAndIpAddressAfter(USERNAME, IPV6_ADDRESS, -1);
        assertFailed(entity);

        entity =
                authorizationEventController.getAuthorizationEventsWithUsernameAndIpAddressAfter(USERNAME, IPV6_ADDRESS, 0);
        assertFailed(entity);
    }

    @Test
    void testAuthorizationEventsBetween() {
        ResponseEntity<EventsCollectionView> entity =
                authorizationEventController.getAuthorizationEventsBetween(System.currentTimeMillis(), System.currentTimeMillis() + 10000L);
        assertSuccessful(entity);

        entity =
                authorizationEventController.getAuthorizationEventsBetween(-1, 0);
        assertFailed(entity);

        entity =
                authorizationEventController.getAuthorizationEventsBetween(0, -1);
        assertFailed(entity);

        entity =
                authorizationEventController.getAuthorizationEventsWithUsernameBetween(USERNAME, System.currentTimeMillis(), System.currentTimeMillis() + 10000L);
        assertSuccessful(entity);

        entity =
                authorizationEventController.getAuthorizationEventsWithUsernameBetween(USERNAME, -1, 0);
        assertFailed(entity);
        entity =
                authorizationEventController.getAuthorizationEventsWithUsernameBetween(USERNAME, 0, -1);
        assertFailed(entity);

        entity =
                authorizationEventController.getAuthorizationEventsWithIpAddressBetween(IPV4_ADDRESS, System.currentTimeMillis(), System.currentTimeMillis() + 10000L);
        assertSuccessful(entity);

        entity =
                authorizationEventController.getAuthorizationEventsWithIpAddressBetween(IPV4_ADDRESS, -1, 0);
        assertFailed(entity);

        entity =
                authorizationEventController.getAuthorizationEventsWithIpAddressBetween(IPV4_ADDRESS, 0, -1);
        assertFailed(entity);

        entity =
                authorizationEventController.getAuthorizationEventsWithUsernameAndIpAddressBetween(USERNAME, IPV4_ADDRESS, System.currentTimeMillis(), System.currentTimeMillis() + 10000L);
        assertSuccessful(entity);

        entity =
                authorizationEventController.getAuthorizationEventsWithUsernameAndIpAddressBetween(USERNAME, IPV4_ADDRESS, -1, 0);
        assertFailed(entity);

        entity =
                authorizationEventController.getAuthorizationEventsWithUsernameAndIpAddressBetween(USERNAME, IPV4_ADDRESS, 0, -1);
        assertFailed(entity);

        entity =
                authorizationEventController.getAuthorizationEventsWithIpAddressBetween(IPV6_ADDRESS, System.currentTimeMillis(), System.currentTimeMillis() + 10000L);
        assertSuccessful(entity);

        entity =
                authorizationEventController.getAuthorizationEventsWithIpAddressBetween(IPV6_ADDRESS, -1, 0);
        assertFailed(entity);

        entity =
                authorizationEventController.getAuthorizationEventsWithIpAddressBetween(IPV6_ADDRESS, 0, -1);
        assertFailed(entity);

        entity =
                authorizationEventController.getAuthorizationEventsWithUsernameAndIpAddressBetween(USERNAME, IPV6_ADDRESS, System.currentTimeMillis(), System.currentTimeMillis() + 10000L);
        assertSuccessful(entity);

        entity =
                authorizationEventController.getAuthorizationEventsWithUsernameAndIpAddressBetween(USERNAME, IPV6_ADDRESS, -1, 0);
        assertFailed(entity);

        entity =
                authorizationEventController.getAuthorizationEventsWithUsernameAndIpAddressBetween(USERNAME, IPV6_ADDRESS, 0, -1);
        assertFailed(entity);
    }
    
    private void assertSuccessfulAndNonEmpty(ResponseEntity<EventsCollectionView> entity) {
        assertSame(HttpStatus.OK, entity.getStatusCode());
        assertNotNull(entity.getBody());
        assertNotNull(entity.getBody().events());
        assertFalse(entity.getBody().events().isEmpty());
    }
    
    private void assertSuccessful(ResponseEntity<EventsCollectionView> entity) {
        assertSame(HttpStatus.OK, entity.getStatusCode());
        assertNotNull(entity.getBody());
        assertNotNull(entity.getBody().events());
    }
    
    private void assertFailed(ResponseEntity<EventsCollectionView> entity) {
        assertSame(HttpStatus.BAD_REQUEST, entity.getStatusCode());
        assertNull(entity.getBody());
    }
}
