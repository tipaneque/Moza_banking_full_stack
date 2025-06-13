package com.moza.bankingApi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO representing a transaction details in the response.
 * Contains information about the amount, date/time, type of transaction,
 * and the other account involved.
 */
@AllArgsConstructor
@Data
public class TransactionResponse {
    private BigDecimal amount;
    private LocalDateTime dateTime;
    private String type;         // ENVIADA ou RECEBIDA
    private String otherAccount;
}
