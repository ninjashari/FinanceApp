package com.finance.app.financeapp.service;

import com.finance.app.financeapp.dto.Bank;

import java.util.List;

public interface BankService {
    Bank addBank(Long userId, Bank bank);

    List<Bank> getBanksByUser(Long userId);

    void deleteBank(Long bankId);
}
