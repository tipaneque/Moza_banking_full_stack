package com.moza.bankingApi.dto.request;

import lombok.Data;

import java.math.BigDecimal;

/**
 * {@code AccountRequest} is a Data Transfer Object (DTO) used for receiving
 * and encapsulating account-related data from client requests.
 *
 * <p>This class contains essential information required to create or update
 * an account, including user identification, unique tax number, account number,
 * and balance.</p>
 *
 * <p><b>Note:</b> This class has two similar fields: {@code userName} and {@code username},
 * which may lead to confusion. Ensure consistent use depending on context.</p>
 */
@Data
public class AccountRequest {
    private String userName;
    private String nuit;
    private String accountNumber;
    private BigDecimal balance;
    private String username;

    public String getUsername() {
        return username;
    }

    public String getUserName(){
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNuit() {
        return nuit;
    }

    public void setNuit(String nuit) {
        this.nuit = nuit;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
