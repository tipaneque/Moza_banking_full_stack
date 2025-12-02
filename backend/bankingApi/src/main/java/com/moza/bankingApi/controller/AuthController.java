package com.moza.bankingApi.controller;

import com.moza.bankingApi.dto.request.AuthRequest;
import com.moza.bankingApi.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


/**
 * The {@code AuthController} is a RESTful entry point responsible for handling
 * authentication requests within the banking API ecosystem.
 *
 * <p><b>Primary Responsibilities:</b></p>
 * <ul>
 *   <li>Authenticates incoming credentials using Spring Security’s {@link AuthenticationManager} abstraction.</li>
 *   <li>Generates stateless JSON Web Tokens (JWT) upon successful authentication.</li>
 *   <li>Exposes secure and well-defined HTTP POST endpoints for client-side login operations.</li>
 * </ul>
 *
 * <p><b>Security and Architecture Highlights:</b></p>
 * <ul>
 *   <li>Implements a token-based authentication mechanism, promoting stateless sessions and scalability in distributed systems.</li>
 *   <li>Delegates user validation to a customizable {@link UserDetailsService}, enabling database-backed or in-memory authentication strategies.</li>
 *   <li>Leverages {@link JwtUtil} to encapsulate JWT creation logic, enhancing separation of concerns and maintainability.</li>
 * </ul>
 *
 * <p><b>Usage:</b></p>
 * <p>
 * Clients send a POST request to <code>/api/v1/auth/login</code> with valid credentials (username and password).
 * Upon successful authentication, the controller issues a JWT that should be included in subsequent API requests
 * via the <code>Authorization</code> header using the <code>Bearer</code> schema.
 * </p>
 *
 * <p><b>Example Request:</b></p>
 * <pre>{@code
 * POST /api/v1/auth/login
 * Content-Type: application/json
 * {
 *   "username": "maria.luis",
 *   "password": "securePassword123"
 * }
 * }</pre>
 *
 * <p><b>Example Response:</b></p>
 * <pre>{@code
 * HTTP 200 OK
 * {
 *   "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
 * }
 * }</pre>
 *
 * @see AuthenticationManager
 * @see UsernamePasswordAuthenticationToken
 * @see UserDetailsService
 * @see JwtUtil
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    /**
     * The authentication manager used to validate user credentials.
     * Internally delegates to configured {@link org.springframework.security.authentication.AuthenticationProvider}s.
     */
    private final AuthenticationManager authManager;

    /**
     * Utility class responsible for generating and validating JWT tokens.
     * Encapsulates token signing and claims creation.
     */
    private final JwtUtil jwtUtil;

    /**
     * Service used to retrieve user-specific data.
     * Supports integration with persistent user stores such as databases or LDAP.
     */
    private final UserDetailsService userDetailsService;

    /**
     * Handles HTTP POST requests for login authentication.
     * Validates user credentials, and returns a signed JWT on success.
     *
     * @param request The authentication request payload containing {@code username} and {@code password}.
     * @return A {@link ResponseEntity} containing the JWT if authentication is successful.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        UserDetails user = (UserDetails) authentication.getPrincipal();
        String token = jwtUtil.generateToken(user);

        return ResponseEntity.ok(Map.of("token", token));
    }

}
