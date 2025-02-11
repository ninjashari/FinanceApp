package com.finance.app.controller;

import com.finance.app.model.Status;
import com.finance.app.model.Transaction;
import com.finance.app.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transaction")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    /**
     * Adds a new transaction for a given account.
     *
     * @param token       The authorization token for the user.
     * @param accountId   The unique identifier of the account to add the transaction to.
     * @param transaction The Transaction object containing details of the transaction to be added.
     * @return ResponseEntity<Status> A response entity with status indicating the success or failure of the transaction addition.
     */
    @PostMapping("/{accountId}")
    public ResponseEntity<Status> addTransaction(@RequestHeader("Authorization") String token, @PathVariable Long accountId, @RequestBody Transaction transaction) {
        try {
            Transaction newTransaction = transactionService.addTransaction(token, accountId, transaction);
            if (newTransaction != null) {
                Status status = new Status();
                status.setCode(HttpStatus.CREATED.value());
                status.setMessage("Transaction added successfully");
                status.setStatus(HttpStatus.CREATED.toString());
                return ResponseEntity.status(HttpStatus.CREATED).body(status);
            } else {
                Status status = new Status();
                status.setCode(HttpStatus.BAD_REQUEST.value());
                status.setMessage("Transaction could not be added");
                status.setStatus(HttpStatus.BAD_REQUEST.toString());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(status);
            }

        } catch (Exception e) {
            Status status = new Status();
            status.setCode(HttpStatus.BAD_REQUEST.value());
            status.setMessage("Transaction add failed :: " + e.getMessage());
            status.setStatus(HttpStatus.BAD_REQUEST.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(status);
        }
    }
}
