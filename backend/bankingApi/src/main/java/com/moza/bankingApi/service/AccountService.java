package com.moza.bankingApi.service;

import com.moza.bankingApi.dto.request.AccountRequest;
import com.moza.bankingApi.model.Account;

import java.util.List;

public interface AccountService {
    String createAccount(AccountRequest request);
    List<Account> getAllAccounts();
    AccountRequest getAccountByUsername(String username);
}
