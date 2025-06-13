package com.moza.bankingApi.security.config;

import com.moza.bankingApi.security.filter.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * {@code SecurityConfig} defines the core security configuration for the banking API,
 * enabling stateless authentication via JSON Web Tokens (JWT) using Spring Security 6+.
 *
 * <p><b>Key Responsibilities:</b></p>
 * <ul>
 *   <li>Configures endpoint-level access control with fine-grained role-based authorization.</li>
 *   <li>Disables CSRF protection to support stateless REST APIs.</li>
 *   <li>Integrates a custom {@link JwtFilter} for token validation and user context population.</li>
 *   <li>Establishes a security filter chain using {@link SecurityFilterChain} bean.</li>
 * </ul>
 *
 * <p><b>Security Model:</b></p>
 * <ul>
 *   <li><strong>Authentication:</strong> Performed via JWT tokens included in the <code>Authorization</code> header.</li>
 *   <li><strong>Authorization:</strong> Enforced using <code>@RolesAllowed</code> style URL matchers.</li>
 *   <li><strong>Session Management:</strong> Stateless. Sessions are not created or stored on the server.</li>
 * </ul>
 *
 * <p><b>Exposed Endpoints:</b></p>
 * <ul>
 *   <li><code>/api/v1/auth/**</code>: Public endpoints for login and authentication, accessible without authentication.</li>
 *   <li><code>admin/**</code>: Secured for users with <code>ROLE_ADMIN</code>.</li>
 *   <li><code>client/**</code>: Secured for users with <code>ROLE_CLIENTE</code>.</li>
 *   <li><code>/api/v1/transactions/**</code>: Accessible only to clients.</li>
 *   <li><code>/api/v1/accounts</code>: Accessible only to admins.</li>
 *   <li><code>/api/v1/accounts/transactions/**</code>: Accessible to both admins and clients.</li>
 * </ul>
 *
 * <p><b>Custom Components:</b></p>
 * <ul>
 *   <li>{@link JwtFilter}: Validates JWTs and loads authenticated user into the Spring Security context.</li>
 *   <li>{@link UserDetailsService}: Resolves user details for authentication validation.</li>
 * </ul>
 *
 * @see org.springframework.security.config.annotation.web.builders.HttpSecurity
 * @see org.springframework.security.authentication.AuthenticationManager
 * @see org.springframework.security.web.SecurityFilterChain
 * @see org.springframework.security.crypto.password.PasswordEncoder
 */

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    /**
     * Custom filter that intercepts incoming requests and validates JWT tokens
     * before Spring Security's {@link UsernamePasswordAuthenticationFilter}.
     */
    private final JwtFilter jwtFilter;

    /**
     * Custom user details service used to retrieve user-specific data from the database.
     * This service is injected into the authentication manager.
     */
    private final UserDetailsService userDetailsService;

    /**
     * Defines the HTTP security policies including endpoint authorization rules,
     * session creation policy, CSRF configuration, and filter chain ordering.
     *
     * @param http the {@link HttpSecurity} instance to be customized.
     * @return the finalized {@link SecurityFilterChain} bean.
     * @throws Exception in case of misconfiguration.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        .requestMatchers("admin/**").hasRole("ADMIN")
                        .requestMatchers("client/**").hasRole("CLIENTE")
                        .requestMatchers("/api/v1/transactions/**").hasRole("CLIENTE")
                        .requestMatchers("/api/v1/accounts").hasRole("ADMIN")
                        .requestMatchers("/api/v1/accounts/transactions/**").hasAnyRole("ADMIN", "CLIENTE")

                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Exposes the {@link AuthenticationManager} bean used by Spring Security to perform
     * authentication tasks.
     *
     * @param config {@link AuthenticationConfiguration} injected by Spring Boot.
     * @return the authentication manager bean.
     * @throws Exception if the authentication manager cannot be retrieved.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Password encoder bean that uses BCrypt hashing algorithm to securely
     * store and verify user passwords.
     *
     * @return a {@link BCryptPasswordEncoder} instance.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
