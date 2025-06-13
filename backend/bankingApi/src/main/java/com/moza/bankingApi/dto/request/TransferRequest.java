package com.moza.bankingApi.dto.request;

import lombok.Data;

import java.math.BigDecimal;


/**
 * {@code TransferRequest} is a Data Transfer Object (DTO) used to encapsulate
 * the necessary information for performing a money transfer between two accounts.
 *
 * <p>This object is typically used in HTTP request bodies when initiating a
 * transfer operation in the banking API.</p>
 *
 * <p>It includes details such as the source and destination account numbers,
 * the amount to be transferred, and an optional description.</p>
 *
 * <p>Validation should be handled externally to ensure all fields are valid
 * and the amount is positive.</p>
 *
 * @author
 */
@Data
public class TransferRequest {

    /**
     * The account number from which the funds will be debited.
     * <p>Must be a valid and existing account in the system.</p>
     */
    private String fromAccountNumber;

    /**
     * The account number to which the funds will be credited.
     * <p>Must be a valid and existing account in the system.</p>
     */
    private String toAccountNumber;

    /**
     * The monetary amount to transfer between accounts.
     * <p>Must be greater than zero.</p>
     */
    private BigDecimal amount;

    /**
     * An optional textual description or reference for the transfer.
     * <p>Used for transaction history and auditing purposes.</p>
     */
    private String description;

}
