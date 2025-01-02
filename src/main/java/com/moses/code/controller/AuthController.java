package com.moses.code.controller;

import com.moses.code.dto.AuthResponse;
import com.moses.code.dto.LoginRequest;
import com.moses.code.dto.RegisterRequest;
import com.moses.code.entity.User;
import com.moses.code.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    // Sign-In Endpoint
    @PostMapping("/sign-in")
    public ResponseEntity<AuthResponse> authenticateUser(
            @Valid @RequestBody LoginRequest loginRequest,
            HttpServletResponse response) {
        log.info("[AuthController:authenticateUser] Sign-in attempt for user: {}", loginRequest.getEmail());

        AuthResponse tokens = authService.authenticateUser(loginRequest, response);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .body(tokens);
    }

    // Refresh Token Endpoint
    @PreAuthorize("hasAuthority('SCOPE_REFRESH_TOKEN')")
    @PostMapping("/refresh-token")
    public ResponseEntity<AuthResponse> getAccessToken(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        log.info("[AuthController:getAccessToken] Refreshing access token");

        AuthResponse accessToken = authService.getAccessTokenUsingRefreshToken(authorizationHeader);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .body(accessToken);
    }

    // Sign-Up Endpoint
    @PostMapping("/sign-up")
    public ResponseEntity<?> registerUser(
            @Valid @RequestBody User registerRequest,
            HttpServletResponse httpServletResponse) {

        log.info("[AuthController:registerUser] Signup Process Started for user: {}", registerRequest.getEmail());

        AuthResponse registeredUser = authService.registerUser(registerRequest, httpServletResponse);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .body(Map.of(
                        "message", "User successfully registered",
                        "user", registeredUser
                ));
    }
}
