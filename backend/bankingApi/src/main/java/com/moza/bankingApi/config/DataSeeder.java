package com.moza.bankingApi.config;

import com.moza.bankingApi.model.User;
import com.moza.bankingApi.model.enums.Role;
import com.moza.bankingApi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


/**
 * {@code DataSeeder} is a Spring Boot component responsible for seeding initial user data into
 * the system upon application startup. This class implements {@link CommandLineRunner}, allowing
 * it to hook into the application's bootstrap phase.
 *
 * <p><b>Primary Responsibilities:</b></p>
 * <ul>
 *   <li>Populates the database with predefined user entities (administrators and clients) if no users exist.</li>
 *   <li>Ensures idempotency by checking the {@link UserRepository#count()} before inserting data.</li>
 *   <li>Encodes plaintext passwords using the injected {@link PasswordEncoder} for secure storage.</li>
 * </ul>
 *
 * <p><b>Design Principles:</b></p>
 * <ul>
 *   <li>Leverages constructor-based dependency injection via {@code @RequiredArgsConstructor} for immutability and testability.</li>
 *   <li>Encapsulated within the {@code @Component} lifecycle annotation, allowing Spring to detect and register the seeder at runtime.</li>
 *   <li>Minimizes overhead by only running once under controlled conditions (empty user table).</li>
 * </ul>
 *
 * <p><b>Usage Scenario:</b></p>
 * <p>This class is useful in development and testing environments to quickly bootstrap a set of users with known credentials,
 * which can later be used for login and authorization testing.</p>
 *
 * <p><b>Generated Users:</b></p>
 * <ul>
 *   <li>Admins: {@code admin1}, {@code admin2} with password {@code admin123}</li>
 *   <li>Clients: {@code cliente1} to {@code cliente10} with password {@code senha123}</li>
 * </ul>
 *
 * <p><b>Security Notice:</b></p>
 * <p>These credentials are intended for non-production use only. In a production environment,
 * data seeding should be handled using secure CI/CD tools or environment-driven initialization.</p>
 *
 * @see org.springframework.boot.CommandLineRunner
 * @see com.moza.bankingApi.model.User
 * @see com.moza.bankingApi.model.enums.Role
 * @see com.moza.bankingApi.repository.UserRepository
 * @see org.springframework.security.crypto.password.PasswordEncoder
 */

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    /**
     * Repository used to persist and retrieve {@link User} entities.
     * Abstracts access to the underlying database.
     */
    private final UserRepository userRepo;

    /**
     * Password encoder used to hash raw passwords before storing them.
     * Typically, uses algorithms such as BCrypt.
     */
    private final PasswordEncoder encoder;

    /**
     * This method is executed automatically when the application context is fully initialized.
     * It seeds the database with two administrator accounts and ten client accounts if none exist.
     *
     * @param args command-line arguments passed to the application, ignored in this context.
     */
    @Override
    public void run(String... args) {
        if (userRepo.count() == 0) {

            for(int i = 0; i < 10; i ++){
                if(i < 2){
                    User admin = new User();
                    admin.setUsername("admin" + (i+1));
                    admin.setPassword(encoder.encode("admin123"));
                    admin.setRole(Role.ADMIN);
                    userRepo.save(admin);
                }
                User cliente = new User();
                cliente.setUsername("cliente" + (i+1));
                cliente.setPassword(encoder.encode("senha123"));
                cliente.setRole(Role.CLIENTE);
                userRepo.save(cliente);
            }
        }
    }
}
