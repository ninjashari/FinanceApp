package com.finance.app.service;

import com.finance.app.model.Account;
import com.finance.app.model.User;
import com.finance.app.repository.AccountRepository;
import com.finance.app.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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
     * @param token The Authorization token for authentication.
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

    public List<Account> getAllAccounts(Long userId) {
        return accountRepository.findByUserId(userId);
    }

    public Account updateAccount(Account account) {
        account.setUpdatedAt(LocalDateTime.now());
        return accountRepository.save(account);
    }

    public void deleteAccount(Long accountId) {
        accountRepository.deleteById(accountId);
    }
}
