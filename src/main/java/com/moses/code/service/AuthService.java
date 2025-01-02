package com.moses.code.service;

import com.moses.code.dto.AuthResponse;
import com.moses.code.dto.LoginRequest;
import com.moses.code.entity.RefreshToken;
import com.moses.code.entity.User;
import com.moses.code.repository.RefreshTokenRepository;
import com.moses.code.repository.UserRepository;
import com.moses.code.utils.JwtTokenGenerator;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userInfoRepo;
    private final JwtTokenGenerator jwtTokenGenerator;
    private final RefreshTokenRepository refreshTokenRepo;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    /**
     * Authenticate using LoginRequest and return JWT tokens.
     */
    public AuthResponse authenticateUser(LoginRequest loginRequest, HttpServletResponse response) {
        try {

            User userInfoEntity = userInfoRepo.findByEmail(loginRequest.getEmail())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));


            if (!passwordEncoder.matches(loginRequest.getPassword(), userInfoEntity.getPassword())) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
            }


            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );

            String accessToken = jwtTokenGenerator.generateAccessToken(authentication);
            String refreshToken = jwtTokenGenerator.generateRefreshToken(authentication);

            saveUserRefreshToken(userInfoEntity, refreshToken);
            createRefreshTokenCookie(response, refreshToken);

            log.info("[AuthService:authenticateUser] Access token for user:{} has been generated", userInfoEntity.getEmail());

            return AuthResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();

        } catch (BadCredentialsException e) {
            log.error("[AuthService:authenticateUser] Invalid credentials for user: {}", loginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
        } catch (Exception e) {
            log.error("[AuthService:authenticateUser] Exception while authenticating the user: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Please try again later");
        }
    }
    /**
     * Save refresh token.
     */
    private void saveUserRefreshToken(User userInfoEntity, String refreshToken) {
        var refreshTokenEntity = RefreshToken.builder()
                .user(userInfoEntity)
                .refreshToken(refreshToken)
                .revoked(false)
                .build();
        refreshTokenRepo.save(refreshTokenEntity);
    }

    /**
     * Create refresh token as HTTP-only cookie.
     */
    private void createRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
        Cookie refreshTokenCookie = new Cookie("refresh_token", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true);
        refreshTokenCookie.setMaxAge(15 * 24 * 60 * 60); // 15 days
        response.addCookie(refreshTokenCookie);
    }

    /**
     * Generate new access token using refresh token.
     */
    public AuthResponse getAccessTokenUsingRefreshToken(String authorizationHeader) {
        if (!authorizationHeader.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid token format");
        }

        final String refreshToken = authorizationHeader.substring(7);

        var refreshTokenEntity = refreshTokenRepo.findByRefreshToken(refreshToken)
                .filter(tokens -> !tokens.isRevoked())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Refresh token revoked"));

        User userInfoEntity = refreshTokenEntity.getUser();
        Authentication authentication = createAuthenticationObject(userInfoEntity);

        String accessToken = jwtTokenGenerator.generateAccessToken(authentication);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .build();
    }

    /**
     * Build authentication object manually.
     */
    private static Authentication createAuthenticationObject(User userInfoEntity) {
        String username = userInfoEntity.getEmail();
        String password = userInfoEntity.getPassword();
        String roles = userInfoEntity.getRoles();

        return new UsernamePasswordAuthenticationToken(
                username,
                password,
                Arrays.stream(roles.split(","))
                        .map(role -> (GrantedAuthority) role::trim)
                        .toList()
        );
    }

    /**
     * Register a new user.
     */
    public AuthResponse registerUser(User userRequest, HttpServletResponse httpServletResponse) {
        try {
            log.info("[AuthService:registerUser] User Registration Started for: {}", userRequest.getEmail());

            if (userInfoRepo.findByEmail(userRequest.getEmail()).isPresent()) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "User Already Exists");
            }

            // Save the user first
            userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));
            User savedUserDetails = userInfoRepo.save(userRequest);

            // Authenticate user and generate tokens
            Authentication authentication = createAuthenticationObject(savedUserDetails);
            String accessToken = jwtTokenGenerator.generateAccessToken(authentication);
            String refreshToken = jwtTokenGenerator.generateRefreshToken(authentication);

            saveUserRefreshToken(savedUserDetails, refreshToken);
            createRefreshTokenCookie(httpServletResponse, refreshToken);

            log.info("[AuthService:registerUser] User:{} Successfully registered", savedUserDetails.getEmail());

            return AuthResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();

        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            log.error("[AuthService:registerUser] Exception during registration: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to register user");
        }
    }
}
