package com.moza.bankingApi.service.impl;

import com.moza.bankingApi.dto.request.AccountRequest;
import com.moza.bankingApi.exception.EntityNotFountException;
import com.moza.bankingApi.model.Account;
import com.moza.bankingApi.model.Transaction;
import com.moza.bankingApi.model.User;
import com.moza.bankingApi.repository.AccountRepository;
import com.moza.bankingApi.repository.TransactionRepository;
import com.moza.bankingApi.repository.UserRepository;
import com.moza.bankingApi.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * The {@code AccountServiceImpl} class is a Spring service component responsible for
 * managing account-related business logic, including account creation,
 * retrieval of all accounts, and fetching account details by username.
 * <p>
 * It interacts with repositories to persist and query {@link Account} and {@link User} entities.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    /**
     * Repository interface for CRUD operations on {@link Account} entities.
     */
    private final AccountRepository accountRepo;

    /**
     * Repository interface to fetch {@link User} entities.
     */
    private final UserRepository userRepo;

    /**
     * Repository interface for CRUD operations on {@link Transaction} entities (unused in current methods).
     */
    private final TransactionRepository transactionRepo;

    /**
     * Creates a new account based on the given {@link AccountRequest}, associating it with
     * the user identified by username in the request. Throws exception if user is not found.
     *
     * @param request the {@link AccountRequest} containing data for the new account
     * @return a string with a message about the status of account creation
     * @throws EntityNotFountException if the user specified in the request does not exist
     */

    @Override
    public String createAccount(AccountRequest request) {


        if(!userRepo.existsByUsername(request.getUsername()))
            return "User does not exist";

        User user = userRepo.findByUsername(request.getUsername()).orElseThrow(() -> new EntityNotFountException("Error Creating account"));

        Account account = new Account();
        account.setUser(user);
        account.setUserName(request.getUserName());
        account.setNuit(request.getNuit());
        account.setAccountNumber(request.getAccountNumber());
        account.setBalance(request.getBalance());
        accountRepo.save(account);

        return "Account created successful";
    }

    /**
     * Retrieves a list of all accounts present in the database.
     *
     * @return a {@link List} of all {@link Account} entities
     */
    @Override
    public List<Account> getAllAccounts() {
        return accountRepo.findAll();
    }

    /**
     * Retrieves account details associated with the user identified by the given username.
     * Returns the details as an {@link AccountRequest} DTO.
     *
     * @param username the username of the user whose account details are requested
     * @return an {@link AccountRequest} containing the account information
     * @throws EntityNotFountException if the user or the account is not found
     */
    @Override
    public AccountRequest getAccountByUsername(String username) {
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new EntityNotFountException("Usuário não encontrado: " + username));

        Account account = accountRepo.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada para o usuário"));

        // Preenche AccountRequest usando setters (Lombok já gera para você)
        AccountRequest accountRequest = new AccountRequest();
        accountRequest.setUserName(account.getUserName());
        accountRequest.setNuit(account.getNuit());
        accountRequest.setAccountNumber(account.getAccountNumber());
        accountRequest.setBalance(account.getBalance());
        accountRequest.setUsername(user.getUsername());

        return accountRequest;
    }

}
