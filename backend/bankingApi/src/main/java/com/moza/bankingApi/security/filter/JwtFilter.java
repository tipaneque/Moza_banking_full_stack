package com.moza.bankingApi.security.filter;

import com.moza.bankingApi.security.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


/**
 * {@code JwtFilter} is a custom security filter that intercepts every incoming HTTP request
 * to extract and validate a JSON Web Token (JWT). If a valid token is found, the filter
 * sets the corresponding authentication in the Spring Security context.
 *
 * <p><b>Main Responsibilities:</b></p>
 * <ul>
 *   <li>Extract the JWT from the "Authorization" header.</li>
 *   <li>Validate the token and extract the associated username.</li>
 *   <li>Load the {@link UserDetails} and set an authenticated {@link UsernamePasswordAuthenticationToken} in the security context.</li>
 *   <li>Ensure that authenticated requests can be processed securely.</li>
 * </ul>
 *
 * <p><b>Security Behavior:</b></p>
 * <ul>
 *   <li>If the token is missing or invalid, the request continues unauthenticated.</li>
 *   <li>Only sets authentication if the token is valid and the user is not already authenticated.</li>
 * </ul>
 *
 * <p>This filter extends {@link OncePerRequestFilter}, ensuring it runs once per request cycle.</p>
 *
 * @author
 * @see com.moza.bankingApi.security.util.JwtUtil
 * @see org.springframework.security.web.authentication.WebAuthenticationDetailsSource
 * @see org.springframework.security.core.context.SecurityContextHolder
 */

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    /**
     * Utility class for parsing and validating JWT tokens.
     */
    private final JwtUtil jwtUtil;

    /**
     * Loads user-specific data during authentication.
     */
    private final UserDetailsService userDetailsService;

    /**
     * Filters every HTTP request to check for a valid JWT token in the Authorization header.
     * If the token is valid and no authentication is present in the context, the user is authenticated.
     *
     * @param request     the incoming HTTP request
     * @param response    the outgoing HTTP response
     * @param chain       the filter chain
     * @throws ServletException if the filter encounters an error during processing
     * @throws IOException      if an input or output exception occurs
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {
        // Retrieve the Authorization header
        String authHeader = request.getHeader("Authorization");

        // Check if the header contains a Bearer token
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            String username = jwtUtil.extractUsername(token);

            // Proceed only if the token has a username and no existing authentication is set
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                // Validate token against user details
                if (jwtUtil.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // Set the authentication in the security context
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }
        // Continue the filter chain
        chain.doFilter(request, response);
    }
}
