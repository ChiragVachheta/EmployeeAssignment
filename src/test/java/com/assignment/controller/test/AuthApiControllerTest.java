package com.assignment.controller.test;

import com.assignment.model.EndUser;
import com.assignment.security.JwtTokenUtil;
import com.assignment.security.controller.AuthApi;
import com.assignment.security.model.AuthRequest;
import com.assignment.security.model.AuthResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.assignment.security.model.AuthResponse; // Assuming AuthResponse is in this package

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AuthApiControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @InjectMocks
    private AuthApi authApiController;

    @Test
    public void testLogin_Success() {
        // Mock auth request
        AuthRequest authRequest = new AuthRequest();
        authRequest.setEmail("test@example.com");
        authRequest.setPassword("password");

        // Mock authentication and principal
        Authentication authentication = mock(Authentication.class);
        EndUser user = new EndUser();
        user.setEmail("test@example.com");
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(user);

        // Mock JWT token generation
        when(jwtTokenUtil.generateAccessToken(any(EndUser.class))).thenReturn("mocked_token");

        // Perform the POST request
        ResponseEntity<?> responseEntity = authApiController.login(authRequest);

        // Assertions
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertTrue(responseEntity.getBody() instanceof AuthResponse);
        assertEquals("test@example.com", ((AuthResponse) responseEntity.getBody()).getEmail());
        assertNotNull(((AuthResponse) responseEntity.getBody()).getAccessToken());
    }

    // Similar test cases for error scenarios can be written
}

