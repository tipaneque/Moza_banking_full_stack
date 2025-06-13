package com.moza.bankingApi.service;

import com.moza.bankingApi.dto.request.UserRequest;
import com.moza.bankingApi.dto.response.UserResponse;
import com.moza.bankingApi.model.User;
import com.moza.bankingApi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * The {@code UserService} class is a Spring service component responsible for managing
 * user-related business logic, including creating new users in the system.
 * <p>
 * This service interacts with the {@link UserRepository} to persist user data
 * and leverages {@link PasswordEncoder} to securely encode user passwords
 * before storage.
 * </p>
 * <p>
 * It provides methods to convert incoming user requests into persistent entities
 * and to return user responses suitable for client consumption.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class UserService {

    /**
     * The repository interface for performing CRUD operations on {@link User} entities.
     */
    private final UserRepository userRepo;

    /**
     * The password encoder used to securely hash user passwords before saving.
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * Creates a new user in the system by processing the incoming {@link UserRequest},
     * encoding the raw password securely, and saving the user entity in the database.
     * <p>
     * After persistence, returns a {@link UserResponse} DTO containing
     * the created user's identifier, username, and assigned role.
     * </p>
     *
     * @param request the {@link UserRequest} containing the new user's data (username, password, role)
     * @return a {@link UserResponse} representing the saved user information
     */
    public UserResponse createUser(UserRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        User saved = userRepo.save(user);

        UserResponse response = new UserResponse();
        response.setId(saved.getId());
        response.setUsername(saved.getUsername());
        response.setRole(saved.getRole());
        return response;
    }
}
