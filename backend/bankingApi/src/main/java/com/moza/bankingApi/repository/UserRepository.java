package com.moza.bankingApi.repository;

import com.moza.bankingApi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * {@code UserRepository} is a Spring Data JPA repository interface responsible for
 * providing CRUD operations and custom queries related to the {@link User} entity.
 *
 * <p>This interface extends {@link JpaRepository}, which provides methods such as
 * {@code save}, {@code findById}, {@code findAll}, {@code deleteById}, among others.</p>
 *
 * <p>It also defines a custom finder method {@code findByUsername}, which allows querying
 * a user entity based on the username field, commonly used during authentication.</p>
 *
 * <p>This repository is automatically implemented by Spring at runtime, eliminating
 * the need for boilerplate DAO code.</p>
 *
 * @see User
 * @see JpaRepository
 * @see org.springframework.data.repository.CrudRepository
 * @author
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Retrieves a {@link User} entity based on the provided username.
     *
     * <p>This method returns an {@link Optional} which will contain the user
     * if a match is found, or be empty otherwise.</p>
     *
     * @param username the unique identifier (login) of the user
     * @return an {@code Optional<User>} containing the matched user or empty if not found
     */
    Optional<User> findByUsername(String username);
}
