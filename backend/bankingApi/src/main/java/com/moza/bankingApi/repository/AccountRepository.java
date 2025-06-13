package com.moza.bankingApi.repository;

import com.moza.bankingApi.model.Account;
import com.moza.bankingApi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * {@code AccountRepository} is a Spring Data JPA repository interface responsible for
 * performing CRUD operations and custom queries related to the {@link Account} entity.
 *
 * <p>It allows interaction with the database layer without the need for boilerplate code,
 * providing easy access to account data using method name-based query derivation.</p>
 */

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    /**
     * Finds an {@link Account} by its unique account number.
     *
     * @param accountNumber the account number to search for
     * @return an {@link Optional} containing the matching account, if found
     */
    Optional<Account> findByAccountNumber(String accountNumber);

    /**
     * Finds an {@link Account} associated with a specific {@link User}.
     *
     * @param user the user to whom the account belongs
     * @return an {@link Optional} containing the user's account, if found
     */
    Optional<Account> findByUser(User user);
}
