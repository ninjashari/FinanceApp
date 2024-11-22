package com.finance.app.financeapp.service;

import com.finance.app.financeapp.dto.Account;

import java.util.List;

public interface AccountService {
    Account createAccount(Long userId, Account account);

    List<Account> getAccountsByUser(Long userId);

    Account updateAccount(Long accountId, Account accountDetails);

    void deleteAccount(Long accountId);
}
