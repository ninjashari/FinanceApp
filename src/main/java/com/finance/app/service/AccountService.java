package com.finance.app.service;

import com.finance.app.model.Account;
import com.finance.app.model.User;
import com.finance.app.repository.AccountRepository;
import com.finance.app.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    UserService userService;

    /**
     * Adds an account to the system.
     *
     * @param account The Account object to be added.
     * @param token   The Authorization token for authentication.
     * @throws Exception if the user is not found or the authentication token is invalid.
     */
    public void addAccount(Account account, String token) throws Exception {
        if (token != null && !token.isEmpty()) {
            User user = userService.getUserFromToken(CommonUtil.extractToken(token));
            if (user != null) {
                account.setUser(user);
                account.setCreatedAt(LocalDateTime.now());
                account.setUpdatedAt(LocalDateTime.now());
                accountRepository.save(account);
            } else {
                throw new Exception("User not found");
            }
        } else {
            throw new Exception("Auth token is invalid");
        }
    }

    /**
     * Retrieves an Account based on the provided accountId.
     *
     * @param accountId The unique identifier of the Account to be retrieved.
     * @return The Account object corresponding to the accountId.
     * @throws Exception if no Account is found with the provided accountId.
     */
    public Account getAccount(Long accountId) throws Exception {
        return accountRepository.findById(accountId).orElseThrow(() -> new Exception("Account not found"));
    }

    /**
     * Updates an existing account with the provided details.
     *
     * @param accountId The unique identifier of the account to be updated.
     * @param account   The Account object containing the updated information.
     * @return The updated Account object after saving it in the repository.
     * @throws Exception if the account with the provided accountId is not found in the repository.
     */
    public Account updateAccount(Long accountId, Account account) throws Exception {
        Account existingAccount = accountRepository.findById(accountId).orElseThrow(() -> new Exception("Account not found"));

        existingAccount.setName(account.getName());
        existingAccount.setType(account.getType());
        existingAccount.setCurrentBalance(account.getCurrentBalance());
        existingAccount.setUpdatedAt(LocalDateTime.now());
        return accountRepository.save(existingAccount);
    }

    public void deleteAccount(Long accountId) {
        accountRepository.deleteById(accountId);
    }
}
