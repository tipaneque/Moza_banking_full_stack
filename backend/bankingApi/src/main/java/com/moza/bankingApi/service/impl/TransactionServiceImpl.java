package com.moza.bankingApi.service.impl;

import com.moza.bankingApi.dto.request.TransferRequest;
import com.moza.bankingApi.dto.response.TransactionResponse;
import com.moza.bankingApi.exception.BalanceNotEnoughException;
import com.moza.bankingApi.exception.EntityNotFountException;
import com.moza.bankingApi.model.Account;
import com.moza.bankingApi.model.Transaction;
import com.moza.bankingApi.model.User;
import com.moza.bankingApi.repository.AccountRepository;
import com.moza.bankingApi.repository.TransactionRepository;
import com.moza.bankingApi.repository.UserRepository;
import com.moza.bankingApi.service.TransactionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/**
 * The {@code TransactionServiceImpl} class is a Spring service component responsible for handling
 * transactional business logic within the banking API, including the execution of transfers
 * between accounts and retrieval of transaction histories (statements).
 * <p>
 * This service ensures atomicity and data consistency via transactional operations and
 * integrates with repositories to fetch and persist entities such as {@link Account}, {@link Transaction}, and {@link User}.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    /**
     * Repository interface to perform CRUD operations on {@link Account} entities.
     */
    private final AccountRepository accountRepository;

    /**
     * Repository interface to persist and retrieve {@link Transaction} entities.
     */
    private final TransactionRepository transactionRepository;

    /**
     * Repository interface to retrieve {@link User} entities.
     */
    private final UserRepository userRepo;

    /**
     * Executes a funds transfer from one account to another using the information provided
     * in the {@link TransferRequest}. This method verifies account existence, validates
     * sufficient balance in the source account, updates account balances atomically,
     * creates a transaction record, and persists all changes.
     *
     * @param request the {@link TransferRequest} containing source account number,
     *                destination account number, amount to transfer, and optional description.
     * @throws EntityNotFountException if the source or destination accounts are not found.
     * @throws BalanceNotEnoughException if the source account does not have enough balance.
     */

    @Override
    @Transactional
    public void transfer(TransferRequest request) {
        Account sourceAccount = accountRepository.findByAccountNumber(request.getFromAccountNumber())
                .orElseThrow(() -> new EntityNotFountException("Conta de origem não encontrada"));

        Account destinationAccount = accountRepository.findByAccountNumber(request.getToAccountNumber())
                .orElseThrow(() -> new EntityNotFountException("Conta de destino não encontrada"));

        if (sourceAccount.getBalance().compareTo(request.getAmount()) < 0) {
            throw new BalanceNotEnoughException("Saldo insuficiente na conta de origem");
        }

        // Update balances atomically
        sourceAccount.setBalance(sourceAccount.getBalance().subtract(request.getAmount()));
        destinationAccount.setBalance(destinationAccount.getBalance().add(request.getAmount()));

        // Create transaction record
        Transaction transaction = new Transaction();
        transaction.setSource(sourceAccount);
        transaction.setDestination(destinationAccount);
        transaction.setAmount(request.getAmount());
        transaction.setAmount(request.getAmount()); // se desejar manter o campo duplicado
        transaction.setDescription(request.getDescription());
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setTimestamp(LocalDateTime.now());

        // Persist updates
        accountRepository.save(sourceAccount);
        accountRepository.save(destinationAccount);
        transactionRepository.save(transaction);
    }

    /**
     * Retrieves the transaction history (statement) for the currently authenticated user.
     * It collects both sent and received transfers, mapping each transaction to a
     * {@link TransactionResponse} DTO that includes the amount, timestamp, type of transfer,
     * and the counterparty's account number.
     *
     * @return a list of {@link TransactionResponse} objects representing the user's transaction history.
     * @throws EntityNotFountException if the user's account or user entity is not found.
     */

    @Override
    public List<TransactionResponse> getBankStatement() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        User user = userRepo.findByUsername(username).orElseThrow(() -> new EntityNotFountException("Conta nao encontrada"));
        Account account = user.getAccount();
        List<TransactionResponse> extrato = new ArrayList<>();

        for (Transaction t : account.getSentTransfers()) {
            extrato.add(new TransactionResponse(
                    t.getAmount(),
                    t.getTimestamp(),
                    "ENVIADA",
                    t.getDestination().getAccountNumber()
            ));
        }

        for (Transaction t : account.getReceivedTransfers()) {
            extrato.add(new TransactionResponse(
                    t.getAmount(),
                    t.getTimestamp(),
                    "RECEBIDA",
                    t.getSource().getAccountNumber()
            ));
        }

        return extrato;
    }
}
