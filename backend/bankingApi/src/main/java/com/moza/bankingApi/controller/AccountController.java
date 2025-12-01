package com.moza.bankingApi.controller;

import com.moza.bankingApi.dto.request.AccountRequest;
import com.moza.bankingApi.model.Account;
import com.moza.bankingApi.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * REST controller to manage bank accounts.
 * <p>
 * Provides endpoints for creating accounts, retrieving all accounts (admin only),
 * and retrieving the authenticated user's own account details.
 * </p>
 */
@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    /**
     * Creates a new bank account.
     * <p>
     * This endpoint is restricted to users with the ADMIN role.
     * </p>
     *
     * @param request The AccountRequest containing account details.
     * @return a String message about the account creation status.
     */
    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> createAccount(@RequestBody AccountRequest request) {
        return ResponseEntity.ok(accountService.createAccount(request));
    }

    /**
     * Retrieves a list of all bank accounts.
     * <p>
     * This endpoint is restricted to users with the ADMIN role.
     * </p>
     *
     * @return List of all Account entities.
     */
    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Account>> getAllAccounts() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }


    /**
     * Retrieves the authenticated user's own account details.
     * <p>
     * This endpoint uses the username from the JWT authentication token to fetch the account.
     * </p>
     *
     * @param authentication Injected Authentication object with user details.
     * @return AccountRequest containing the user's account details.
     */
    @GetMapping("/me")
    public ResponseEntity<AccountRequest> getMyAccount(Authentication authentication) {
        String username = authentication.getName(); // username do usuário autenticado via JWT
        AccountRequest account = accountService.getAccountByUsername(username);
        return ResponseEntity.ok(account);
    }

}
