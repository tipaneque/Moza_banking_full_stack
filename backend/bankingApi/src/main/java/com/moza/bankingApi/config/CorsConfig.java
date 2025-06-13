package com.moza.bankingApi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

/**
 * {@code CorsConfig} defines the Cross-Origin Resource Sharing (CORS) configuration
 * for the entire backend application. It enables the backend API to be accessible
 * from external domains—commonly used by frontend clients hosted separately (e.g., Angular, React).
 *
 * <p><b>Purpose:</b></p>
 * <ul>
 *   <li>Allows the frontend (e.g., running at <code>http://localhost:4200</code>) to interact with secured backend endpoints.</li>
 *   <li>Prevents CORS-related client-side errors (e.g., blocked requests due to same-origin policy violations).</li>
 *   <li>Enables support for credentials such as cookies or Authorization headers.</li>
 * </ul>
 *
 * <p><b>Configuration Highlights:</b></p>
 * <ul>
 *   <li><b>Allowed Origins:</b> Only <code>http://localhost:4200</code> is permitted (typical for Angular dev environment).</li>
 *   <li><b>Allowed Methods:</b> Standard HTTP verbs like GET, POST, PUT, DELETE, and OPTIONS are explicitly allowed.</li>
 *   <li><b>Allowed Headers:</b> Wildcard ("*") allows all headers.</li>
 *   <li><b>Allow Credentials:</b> Enabled to permit cross-origin requests with cookies or authorization tokens.</li>
 * </ul>
 *
 * <p><b>Best Practices:</b></p>
 * <ul>
 *   <li>In production, restrict <code>allowedOrigins</code> to trusted domains only.</li>
 *   <li>Avoid using wildcard headers with credentials in production unless necessary and secure.</li>
 *   <li>Consider environment-based configurations (dev vs prod).</li>
 * </ul>
 *
 * @see org.springframework.web.cors.CorsConfiguration
 * @see org.springframework.web.cors.UrlBasedCorsConfigurationSource
 * @see org.springframework.web.cors.CorsConfigurationSource
 */

@Configuration
public class CorsConfig {

    /**
     * Defines the CORS policy for all incoming HTTP requests.
     * Configures allowed origins, methods, headers, and credentials.
     *
     * @return a {@link CorsConfigurationSource} object that Spring Security uses to apply CORS rules.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Allow requests from the local Angular frontend (development environment)
        configuration.setAllowedOrigins(List.of("http://localhost:4200"));

        // Permit typical RESTful HTTP methods
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // Allow all headers in requests (e.g., Content-Type, Authorization)
        configuration.setAllowedHeaders(List.of("*"));

        // Enable credentials (e.g., cookies, Authorization headers) in cross-origin requests
        configuration.setAllowCredentials(true);

        // Register the configuration for all routes (/**)
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
