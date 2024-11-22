package com.finance.app.financeapp.service;

import com.finance.app.financeapp.dto.Bank;

import java.util.List;

public interface BankService {
    public Bank addBank(Long userId, Bank bank);

    public List<Bank> getBanksByUser(Long userId);

    public void deleteBank(Long bankId);
}
