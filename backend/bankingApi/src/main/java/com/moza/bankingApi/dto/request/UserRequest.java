package com.moza.bankingApi.dto.request;

import com.moza.bankingApi.model.enums.Role;
import lombok.Data;


/**
 * {@code UserRequest} is a Data Transfer Object (DTO) used to encapsulate
 * the necessary information for creating or registering a new user in the system.
 *
 * <p>This object is typically used in the body of HTTP requests during user
 * registration or administrative user creation.</p>
 *
 * <p>It includes fields for username, password, and the user's role.</p>
 *
 * @author Miro Pedro T.Lino
 */

@Data
public class UserRequest {

    /**
     * The desired username of the new user.
     * <p>This value must be unique and not null.</p>
     */
    private String username;

    /**
     * The raw password for the user account.
     * <p>This value will be encoded before being stored in the database.</p>
     */
    private String password;

    /**
     * The role assigned to the user (e.g., ADMIN, CLIENTE).
     * <p>Must be one of the predefined roles in the {@link Role} enum.</p>
     */
    private Role role;
}
