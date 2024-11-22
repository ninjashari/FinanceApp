package com.finance.app.financeapp.service.impl;

import com.finance.app.financeapp.dto.Bank;
import com.finance.app.financeapp.dto.User;
import com.finance.app.financeapp.repository.BankRepository;
import com.finance.app.financeapp.repository.UserRepository;
import com.finance.app.financeapp.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the BankService interface. Provides methods for managing banks
 * associated with users.
 */
@Service
public class BankServiceImpl implements BankService {

    /**
     * Repository for performing CRUD operations on Bank entities.
     * This repository is used to interact with the database layer and
     * provides methods to save, retrieve, and delete Bank entities.
     */
    @Autowired
    private BankRepository bankRepository;

    /**
     * Repository for performing CRUD operations on User entities.
     * This repository extends JpaRepository to inherit standard
     * database operations and also includes custom methods such as
     * finding a User by their ID.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Adds a new bank associated with a specified user ID.
     *
     * @param userId the ID of the user to associate the bank with
     * @param bank the Bank entity to be added
     * @return the saved Bank entity associated with the specified user
     * @throws RuntimeException if the user with the specified ID is not found
     */
    @Override
    public Bank addBank(Long userId, Bank bank) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        bank.setUser(user);
        return bankRepository.save(bank);
    }

    /**
     * Retrieves a list of banks associated with a specific user.
     *
     * @param userId the ID of the user whose banks are to be retrieved
     * @return a list of Bank entities associated with the specified user
     */
    @Override
    public List<Bank> getBanksByUser(Long userId) {
        return bankRepository.findByUserId(userId);
    }

    /**
     * Deletes a bank entity by its ID.
     *
     * @param bankId the ID of the bank to be deleted
     */
    @Override
    public void deleteBank(Long bankId) {
        bankRepository.deleteById(bankId);
    }
}
