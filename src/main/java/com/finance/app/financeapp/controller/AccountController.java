package com.finance.app.financeapp.controller;

import com.finance.app.financeapp.dto.Account;
import com.finance.app.financeapp.service.impl.AccountServiceImpl;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * A controller for handling HTTP requests related to account operations.
 */
@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    Logger LOG = org.slf4j.LoggerFactory.getLogger(AccountController.class);
    /**
     * Service implementation for handling account-related operations.
     * This service is autowired into the controller to facilitate dependency injection.
     */
    @Autowired
    private AccountServiceImpl accountService;

    /**
     * Creates a new account for a specified user.
     *
     * @param userId  the ID of the user for whom the account will be created
     * @param account the account details to be created
     * @return a ResponseEntity containing the created account
     */
    @PostMapping("/{userId}")
    public ResponseEntity<Account> createAccount(@PathVariable Long userId, @RequestBody Account account) {
        Account createdAccount = accountService.createAccount(userId, account);
        LOG.info("Account created: {}", createdAccount);
        return ResponseEntity.ok(createdAccount);
    }

    /**
     * Retrieves a list of accounts associated with a specific user.
     *
     * @param userId the ID of the user whose accounts are to be retrieved
     * @return a ResponseEntity containing a list of accounts associated with the specified user
     */
    @GetMapping("/{userId}")
    public ResponseEntity<List<Account>> getAccountsByUser(@PathVariable Long userId) {
        List<Account> accounts = accountService.getAccountsByUser(userId);
        LOG.info("Accounts retrieved: {}", accounts);
        return ResponseEntity.ok(accounts);
    }

    /**
     * Updates the account details for a specific account.
     *
     * @param accountId      the ID of the account to be updated
     * @param accountDetails the new details for the account
     * @return a ResponseEntity containing the updated account details
     */
    @PutMapping("/{accountId}")
    public ResponseEntity<Account> updateAccount(@PathVariable Long accountId, @RequestBody Account accountDetails) {
        Account updatedAccount = accountService.updateAccount(accountId, accountDetails);
        LOG.info("Account updated: {}", updatedAccount);
        return ResponseEntity.ok(updatedAccount);
    }

    /**
     * Deletes an account based on the given account ID.
     *
     * @param accountId the ID of the account to be deleted
     * @return a ResponseEntity containing a success message if the account is deleted successfully
     */
    @DeleteMapping("/{accountId}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long accountId) {
        accountService.deleteAccount(accountId);
        LOG.info("Account deleted: {}", accountId);
        return ResponseEntity.ok("Account deleted successfully");
    }
}