package com.moza.bankingApi.controller;

import com.moza.bankingApi.dto.request.TransferRequest;
import com.moza.bankingApi.dto.response.TransactionResponse;
import com.moza.bankingApi.service.AccountService;
import com.moza.bankingApi.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller responsible for handling transaction-related HTTP requests.
 * Provides endpoints for performing money transfers and retrieving transaction extracts.
 * <p>
 * Access to the transfer endpoint is restricted to users with the role "CLIENTE".
 * </p>
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transactions")
public class TransactionController {
    private final TransactionService transactionService;
    private final AccountService accountService;

    /**
     * Executes a funds transfer between two accounts.
     * <p>
     * This operation requires the authenticated user to have the "CLIENTE" role.
     * </p>
     *
     * @param request the {@link TransferRequest} containing transfer details (from account, to account, amount, description)
     * @return a {@link ResponseEntity} with a success message if the transfer completes successfully
     */
    @PostMapping("/transfer")
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<?> transfer(@RequestBody TransferRequest request) {
        transactionService.transfer(request);
        return ResponseEntity.ok("Transferência realizada com sucesso");
    }

    /**
     * Retrieves the transaction extract (history) for the authenticated user.
     *
     * @return a {@link ResponseEntity} containing a list of {@link TransactionResponse} objects
     * representing the user's transaction history
     */
    @GetMapping("/extract")
    public ResponseEntity<List<TransactionResponse>> consultarExtrato() {
        return ResponseEntity.ok(transactionService.extrato());
    }
}
