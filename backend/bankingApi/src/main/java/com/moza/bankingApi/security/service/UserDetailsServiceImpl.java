package com.moza.bankingApi.security.service;

import com.moza.bankingApi.exception.EntityNotFountException;
import com.moza.bankingApi.model.User;
import com.moza.bankingApi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * {@code UserDetailsServiceImpl} is a custom implementation of {@link UserDetailsService}
 * used by Spring Security to retrieve user details from the database based on the username.
 *
 * <p>This service is crucial during the authentication process, allowing Spring Security
 * to validate credentials and assign appropriate authorities based on the user's role.</p>
 *
 * <p>It transforms the application's {@link User} entity into a Spring Security-compatible
 * {@link org.springframework.security.core.userdetails.User} object.</p>
 */

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    /**
     * Repository to access and retrieve user entities from the database.
     */
    private final UserRepository userRepo;

    /**
     * Loads a user by their username for authentication and authorization purposes.
     *
     * @param username the username to search for
     * @return a Spring Security {@link UserDetails} object containing the user's credentials and authorities
     * @throws EntityNotFountException if the user is not found in the database
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepo.findByUsername(username).orElseThrow(() -> new EntityNotFountException("Usuário ex " + username + " não encontrado!"));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
        );
    }
}
