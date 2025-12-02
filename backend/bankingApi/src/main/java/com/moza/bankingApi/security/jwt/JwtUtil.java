package com.moza.bankingApi.security.jwt;

import com.moza.bankingApi.config.KeyProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.util.Date;


/**
 * {@code JwtUtil} is a utility class responsible for handling operations related to
 * JSON Web Tokens (JWT), such as generating tokens, extracting claims, and validating tokens.
 *
 * <p>It is used primarily in conjunction with Spring Security to implement stateless authentication
 * by embedding user identity and role information into signed JWTs.</p>
 *
 * <p><b>Token Format:</b></p>
 * <ul>
 *   <li>HS256 signature algorithm.</li>
 *   <li>Base64-decoded symmetric secret key.</li>
 *   <li>Includes claims: subject (username), role, issuedAt, and expiration.</li>
 * </ul>
 *
 * <p><b>Security Note:</b> The secret key should be stored securely and rotated periodically in production environments.</p>
 *
 * @author
 * @see io.jsonwebtoken.Jwts
 * @see org.springframework.security.core.userdetails.UserDetails
 */

@Component
public class JwtUtil {

    @Autowired
    private KeyProvider keyProvider;

    /**
     * Token expiration time in milliseconds. Default is 1 day (86400000 ms).
     */
    private final long EXPIRATION = 86400000; // 1 dia

    /**
     * Generates a JWT token containing the username and role of the authenticated user.
     *
     * @param userDetails the authenticated user details
     * @return a signed JWT token as a {@code String}
     */
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("role", userDetails.getAuthorities().iterator().next().getAuthority())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(Keys.hmacShaKeyFor(keyProvider.getSecret().getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Extracts the username (subject) from the given JWT token.
     *
     * @param token the JWT token
     * @return the username contained in the token
     */
    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    /**
     * Validates the JWT token by checking the username and verifying that the token has not expired.
     *
     * @param token       the JWT token
     * @param userDetails the user details to match with the token's subject
     * @return {@code true} if the token is valid, {@code false} otherwise
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * Retrieves all claims from the token after verifying its signature.
     *
     * @param token the JWT token
     * @return the claims embedded in the token
     */
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(keyProvider.getSecret().getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    /**
     * Checks if the JWT token has expired by comparing the expiration date to the current time.
     *
     * @param token the JWT token
     * @return {@code true} if the token has expired, {@code false} otherwise
     */

    private boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }
}
