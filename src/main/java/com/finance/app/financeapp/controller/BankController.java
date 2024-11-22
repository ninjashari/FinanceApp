package com.finance.app.financeapp.controller;

import com.finance.app.financeapp.dto.Bank;
import com.finance.app.financeapp.service.impl.BankServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * A controller for handling HTTP requests related to bank operations.
 */
@RestController
@RequestMapping("/api/banks")
public class BankController {

    /**
     * Service implementation for handling bank-related operations.
     * This service is autowired into the controller to facilitate dependency injection.
     */
    @Autowired
    private BankServiceImpl bankService;

    /**
     * Adds a new bank linked to a specific user.
     *
     * @param userId the ID of the user to whom the bank will be linked
     * @param bank   the bank details to be added
     * @return a ResponseEntity containing the saved bank
     */
    @PostMapping("/{userId}")
    public ResponseEntity<Bank> addBank(@PathVariable Long userId, @RequestBody Bank bank) {
        Bank savedBank = bankService.addBank(userId, bank);
        return ResponseEntity.ok(savedBank);
    }

    /**
     * Retrieves a list of banks associated with a specific user.
     *
     * @param userId the ID of the user whose banks are to be retrieved
     * @return a ResponseEntity containing a list of banks associated with the specified user
     */
    @GetMapping("/{userId}")
    public ResponseEntity<List<Bank>> getBanksByUser(@PathVariable Long userId) {
        List<Bank> banks = bankService.getBanksByUser(userId);
        return ResponseEntity.ok(banks);
    }

    /**
     * Deletes a bank by its ID.
     *
     * @param bankId the ID of the bank to be deleted
     * @return a ResponseEntity containing a success message
     */
    @DeleteMapping("/{bankId}")
    public ResponseEntity<String> deleteBank(@PathVariable Long bankId) {
        bankService.deleteBank(bankId);
        return ResponseEntity.ok("Bank deleted successfully");
    }
}
