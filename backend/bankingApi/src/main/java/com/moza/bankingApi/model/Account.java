package com.moza.bankingApi.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * The {@code Account} class encapsulates the core entity model for a customer's banking account
 * within the Internet Banking domain-driven architecture.
 * <p>
 * This class serves as a foundational data abstraction that adheres to the principles of
 * Object-Oriented Design (OOD), supporting encapsulation, immutability (when applicable),
 * and clear separation of concerns.
 * </p>
 *
 * <p><b>Key Responsibilities:</b></p>
 * <ul>
 *   <li>Represents a persistable JPA entity mapped to the underlying relational database.</li>
 *   <li>Maintains stateful properties of a bank account, such as balance and account type.</li>
 *   <li>Provides utility methods for domain-level operations like crediting and debiting funds.</li>
 * </ul>
 *
 * <p><b>Design Considerations:</b></p>
 * <ul>
 *   <li>Optimized for integration with Spring Data JPA and Hibernate ORM.</li>
 *   <li>Designed to be serializable and easily translatable into DTOs for RESTful API consumption.</li>
 *   <li>Enables high cohesion and low coupling within the service layer.</li>
 * </ul>
 *
 * @author Miro Pedro T. Lino
 * @version 1.0
 * @since 2025-06-13
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Account {

    /**
     * The unique identifier for the account entity.
     * Auto-generated to ensure database-level uniqueness and referential integrity.
     */
    @Id @GeneratedValue
    private Long id;

    /**
     * Represents the name of the account holder.
     * Should be validated against null and empty constraints.
     */
    private String userName;


    private String nuit;

    @Column(unique = true)
    private String accountNumber;

    /**
     * Indicates the current balance associated with the account.
     * BigDecimal is used to maintain financial accuracy and avoid floating-point anomalies.
     */
    private BigDecimal balance;

    @JsonManagedReference
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "source")
    private List<Transaction> sentTransfers;

    @OneToMany(mappedBy = "destination")
    private List<Transaction> receivedTransfers;

}
