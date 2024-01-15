package com.jfs415.packetwatcher_api.test.integration;

import static org.junit.jupiter.api.Assertions.*;

import com.jfs415.packetwatcher_api.PacketWatcherApi;
import com.jfs415.packetwatcher_api.auth.AuthenticationRequest;
import com.jfs415.packetwatcher_api.controllers.AuthController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(classes = PacketWatcherApi.class)
class AuthControllerTests {

    private final AuthController authController;

    private static final String USERNAME = "testuser2";
    private static final String PASSWORD = "TestUser@123";

    private String token;

    @Autowired
    public AuthControllerTests(AuthController authcontroller) {
        this.authController = authcontroller;
    }

    @Test
    void testUserLogin() {
        ResponseEntity<?> response = authController.processLogin(new AuthenticationRequest(USERNAME, PASSWORD));
        validateResponse(response);

        token = response.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        assertNotNull(token);
        assertFalse(token.isBlank());
    }

    @Test
    void testFullLoginLogoutFlow() {
        testUserLogin();
        testUserLogout();
    }

    void testUserLogout() {
        ResponseEntity<?> response = authController.processLogout(token);
        validateResponse(response);
        assertTrue(response.getBody() instanceof Boolean);
        assertTrue((boolean) response.getBody());
    }

    private void validateResponse(ResponseEntity<?> response) {
        assertNotNull(response);
        assertSame(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.hasBody());
    }
}
