package com.moza.bankingApi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.moza.bankingApi.model.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


/**
 * The {@code User} entity serves as the foundational authentication and identity model
 * within the banking platform's security infrastructure. It implements {@link UserDetails},
 * thus tightly integrating with Spring Security’s authentication and authorization ecosystem.
 *
 * <p><b>Core Responsibilities:</b></p>
 * <ul>
 *   <li>Acts as a secured principal representing an authenticated system user.</li>
 *   <li>Holds credential data such as {@code username} and {@code password}, along with a {@code role}-based access policy.</li>
 *   <li>Establishes a unidirectional relationship to an {@link Account} entity via one-to-one mapping.</li>
 * </ul>
 *
 * <p><b>Security Considerations:</b></p>
 * <ul>
 *   <li>Credentials are stored in an obfuscated/encoded format and excluded from JSON serialization using {@link JsonIgnore}.</li>
 *   <li>Implements method stubs from {@link UserDetails} to support fine-grained access control and session management.</li>
 *   <li>Employs {@link JsonBackReference} to prevent circular references during bidirectional serialization with the account entity.</li>
 * </ul>
 *
 * <p><b>Design Highlights:</b></p>
 * <ul>
 *   <li>Utilizes Lombok annotations such as {@code @Builder} and {@code @Getter} to eliminate boilerplate code and improve maintainability.</li>
 *   <li>Leverages the {@code Role} enum for centralized role management and scalable permission enforcement.</li>
 *   <li>Configurable via JPA and mapped with identity generation strategy for efficient persistence.</li>
 * </ul>
 *
 * <p><b>Integration Points:</b></p>
 * <ul>
 *   <li>Consumed by JWT-based authentication mechanisms and token issuance modules.</li>
 *   <li>Functions as a domain-level user entity, often mapped to API request/response DTOs.</li>
 *   <li>Decouples domain logic from infrastructure concerns by leveraging Spring’s security contract interface.</li>
 * </ul>
 *
 * @see org.springframework.security.core.userdetails.UserDetails
 * @see com.moza.bankingApi.model.Account
 * @see com.moza.bankingApi.model.enums.Role
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class User implements UserDetails {

    /**
     * Unique database identifier for the user.
     * Automatically generated via JPA identity strategy.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Represents the unique login name or email associated with the user.
     * Enforced at the database level to prevent duplication.
     */

    @Column(unique = true)
    private String username;

    /**
     * Stores the user’s encoded password.
     * Ignored from serialization output to protect sensitive information.
     */
    @JsonIgnore
    private String password;

    /**
     * Enumerated type representing the user's access role (e.g., ADMIN, CLIENT).
     * Used by authorization services to enforce access rules and policies.
     */
    @Enumerated(EnumType.STRING)
    private Role role;

    /**
     * One-to-one association with the {@link Account} entity.
     * This relationship is owned by the Account entity.
     * Annotated with {@link JsonBackReference} to avoid infinite recursion during JSON serialization.
     */
    @JsonBackReference
    @OneToOne(mappedBy = "user")
    private Account account;


    /**
     * Returns the authorities granted to the user.
     * Currently, returns an empty list; can be extended for role-based authority mapping.
     *
     * @return a collection of granted authorities
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }



    /**
     * Indicates whether the user's account has expired.
     * Default implementation provided by {@link UserDetails}.
     */
    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    /**
     * Indicates whether the user is locked or unlocked.
     * Default implementation assumes non-locked.
     */
    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    /**
     * Indicates whether the user's credentials (password) have expired.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    /**
     * Indicates whether the user is enabled or disabled.
     * Can be used to perform soft-deletion or temporary deactivation.
     */
    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
