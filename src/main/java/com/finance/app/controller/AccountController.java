package com.finance.app.controller;

import com.finance.app.model.Account;
import com.finance.app.model.Status;
import com.finance.app.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    /**
     * Adds an account to the system.
     *
     * @param token The Authorization token for authentication.
     * @param account The Account object to be added.
     * @return ResponseEntity with status and message indicating the outcome of the operation.
     */
    @PostMapping("/add")
    public ResponseEntity<Status> addAccount(@RequestHeader("Authorization") String token, @RequestBody Account account) {
        try {
            accountService.addAccount(account, token);

            Status status = new Status();
            status.setStatus(HttpStatus.OK.toString());
            status.setMessage("Account added successfully");
            status.setCode(HttpStatus.OK.value());
            return ResponseEntity.status(HttpStatus.OK).body(status);

        } catch (Exception exception) {
            Status status = new Status();
            status.setStatus(HttpStatus.BAD_REQUEST.toString());
            status.setMessage(exception.getMessage());
            status.setCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(status);
        }
    }

// Fetch account details

// Update account details

// Delete an account
}
