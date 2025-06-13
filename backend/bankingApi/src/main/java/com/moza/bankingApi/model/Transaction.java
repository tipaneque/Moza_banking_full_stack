package com.moza.bankingApi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;


/**
 * The {@code Transaction} class represents a core financial operation within the digital banking domain.
 * It encapsulates the transfer of monetary value between two distinct {@link Account} entities,
 * and serves as a vital audit trail component within the transactional ledger.
 *
 * <p><b>Core Responsibilities:</b></p>
 * <ul>
 *   <li>Models a domain event representing the movement of funds from a source account to a destination account.</li>
 *   <li>Stores immutable data about the transaction including its amount, origin, destination, timestamp, and description.</li>
 *   <li>Acts as a persistence-friendly entity, suitable for audit logs, reporting, and real-time analytics.</li>
 * </ul>
 *
 * <p><b>Design Features:</b></p>
 * <ul>
 *   <li>Implements full JPA and Hibernate integration for ORM-based persistence and lazy loading support.</li>
 *   <li>Utilizes Lombok annotations to reduce boilerplate code and promote cleaner architecture.</li>
 *   <li>Supports extensibility for integration with fraud detection engines, transaction processors, and notification services.</li>
 * </ul>
 *
 * <p><b>Usage Considerations:</b></p>
 * <ul>
 *   <li>Each instance is expected to be immutable post-creation, aligning with event-sourcing principles.</li>
 *   <li>Timestamps should reflect server-side creation time for consistency and traceability.</li>
 *   <li>Amount should always be positive and non-null to preserve financial data integrity.</li>
 * </ul>
 *
 * @author
 * @version 1.0
 * @since 2025-06-13
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Transaction {

    /**
     * A unique identifier for the transaction.
     * Automatically generated and used as a primary key in the relational database.
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * The originating account from which the funds are debited.
     * Maintains a many-to-one relationship with the {@link Account} entity.
     */
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "source_id")
    private Account source;

    /**
     * The recipient account to which the funds are credited.
     * Also maintains a many-to-one relationship with the {@link Account} entity.
     */
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "destination_id")
    private Account destination;

    private BigDecimal amount;
    private String description;
    private LocalDateTime timestamp;
}
