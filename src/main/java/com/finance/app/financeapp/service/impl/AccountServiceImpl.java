package com.finance.app.financeapp.service.impl;

import com.finance.app.financeapp.dto.Account;
import com.finance.app.financeapp.dto.User;
import com.finance.app.financeapp.repository.AccountRepository;
import com.finance.app.financeapp.repository.UserRepository;
import com.finance.app.financeapp.service.AccountService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Implementation of the service for managing accounts.
 */
@Service
public class AccountServiceImpl implements AccountService {
    private Logger LOG = org.slf4j.LoggerFactory.getLogger(AccountServiceImpl.class);

    /**
     * Repository for performing CRUD operations on Account entities.
     * This repository extends JpaRepository to inherit standard database operations
     * and includes custom methods for finding accounts by user ID.
     */
    @Autowired
    private AccountRepository accountRepository;

    /**
     * A repository for performing CRUD operations on User entities.
     * This repository extends JpaRepository to inherit standard database
     * operations and includes methods for finding a User by their ID or username.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Creates a new account associated with a specified user.
     *
     * @param userId  the ID of the user to whom the account will be associated
     * @param account the account entity to be created
     * @return the created account entity
     * @throws RuntimeException if the user with the specified ID is not found
     */
    @Override
    public Account createAccount(Long userId, Account account) {
        LOG.info("Creating account for user: {}", userId);
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        account.setUser(user);
        account.setCreatedDate(new Date());
        account.setUpdatedDate(new Date());
        LOG.info("Account created: {}", account);
        return accountRepository.save(account);
    }

    /**
     * Retrieves a list of accounts associated with a specific user.
     *
     * @param userId the ID of the user whose accounts are to be retrieved
     * @return a list of Account entities associated with the specified user
     */
    @Override
    public List<Account> getAccountsByUser(Long userId) {
        LOG.info("Retrieving accounts for user: {}", userId);
        return accountRepository.findByUserId(userId);
    }

    /**
     * Updates the account details for a given account ID.
     *
     * @param accountId      the ID of the account to update
     * @param accountDetails the new details of the account
     * @return the updated Account entity
     * @throws RuntimeException if the account with the specified ID is not found
     */
    @Override
    public Account updateAccount(Long accountId, Account accountDetails) {
        LOG.info("Updating account: {}", accountId);
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new RuntimeException("Account not found"));
        account.setName(accountDetails.getName());
        account.setType(accountDetails.getType());
        account.setCurrentBalance(accountDetails.getCurrentBalance());
        account.setCurrency(accountDetails.getCurrency());
        account.setUpdatedDate(new Date());
        return accountRepository.save(account);
    }

    /**
     * Deletes the account identified by the provided account ID.
     *
     * @param accountId the ID of the account to be deleted
     */
    @Override
    public void deleteAccount(Long accountId) {
        LOG.info("Deleting account: {}", accountId);
        accountRepository.deleteById(accountId);
        LOG.info("Account deleted");
    }
}
