package com.finance.app.service;

import com.finance.app.model.*;
import com.finance.app.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TransactionService {

    @Autowired
    UserService userService;

    @Autowired
    AccountService accountService;

    @Autowired
    TransactionRepository transactionRepository;

    /**
     * Adds a transaction to an account based on the provided details.
     *
     * @param token       The token for user authentication and authorization.
     * @param accountId   The unique identifier of the account where the transaction needs to be added.
     * @param transaction The Transaction object representing the details of the transaction to be added.
     * @return The newly added Transaction object if the user, account, and transaction details are valid and the transaction is processed successfully.
     * @throws Exception if the user is not authenticated, the account is not found, the transaction type is invalid, or any other processing error occurs.
     */
    public Transaction addTransaction(String token, Long accountId, Transaction transaction) throws Exception {
        Transaction newTransaction = null;

        // Retrieve user using the provided token
        User user = userService.getUser(token);
        if (user != null) {

            // Retrieve account using the provided account ID
            Account account = accountService.getAccount(accountId);
            if (account != null) {

                // Determine transaction type based on the provided transaction details
                ETransactionType transactionType = ETransactionType.valueOf(transaction.getType());

                switch (transactionType) {
                    case DEPOSIT:
                        // If transaction type is DEPOSIT, decrease the account's current balance by the transaction amount
                        account.setCurrentBalance(account.getCurrentBalance().subtract(transaction.getAmount()));
                        // If the account type is CREDIT_CARD, increase the credit limit by the transaction amount
                        if (account.getType().equals(EAccountType.CREDIT_CARD.name())) {
                            account.setCreditLimit(account.getCreditLimit().add(transaction.getAmount()));
                        }
                        // Update the account in the database
                        account.setUpdatedDate(LocalDateTime.now());
                        accountService.updateAccount(accountId, account);
                        break;
                    case WITHDRAWAL:
                        // If transaction type is WITHDRAWAL, increase the account's current balance by the transaction amount
                        account.setCurrentBalance(account.getCurrentBalance().add(transaction.getAmount()));
                        // If the account type is CREDIT_CARD, decrease the credit limit by the transaction amount
                        if (account.getType().equals(EAccountType.CREDIT_CARD.name())) {
                            account.setCreditLimit(account.getCreditLimit().subtract(transaction.getAmount()));
                        }
                        // Update the account in the database
                        account.setUpdatedDate(LocalDateTime.now());
                        accountService.updateAccount(accountId, account);
                        break;
                    case TRANSFER:
                        // If transaction type is TRANSFER, subtract the transaction amount from the fromAccount's balance
                        Account fromAccount = accountService.getAccount(transaction.getFromAccount().getId());
                        Account toAccount = accountService.getAccount(transaction.getToAccount().getId());

                        fromAccount.setCurrentBalance(fromAccount.getCurrentBalance().subtract(transaction.getAmount()));
                        // And decrease the fromAccount's credit limit if account type is CREDIT_CARD
                        if (fromAccount.getType().equals(EAccountType.CREDIT_CARD.name())) {
                            fromAccount.setCreditLimit(fromAccount.getCreditLimit().subtract(transaction.getAmount()));
                        }

                        // then add the transaction amount to the toAccount's balance
                        toAccount.setCurrentBalance(toAccount.getCurrentBalance().add(transaction.getAmount()));
                        // and increase the toAccount's credit limit if account type is CREDIT_CARD
                        if (toAccount.getType().equals(EAccountType.CREDIT_CARD.name())) {
                            toAccount.setCreditLimit(toAccount.getCreditLimit().add(transaction.getAmount()));
                        }

                        // Update both accounts in the database
                        fromAccount.setUpdatedDate(LocalDateTime.now());
                        accountService.updateAccount(fromAccount.getId(), fromAccount);
                        toAccount.setUpdatedDate(LocalDateTime.now());
                        accountService.updateAccount(toAccount.getId(), toAccount);
                        break;
                }

                // Save the new transaction to the database
                transaction.setCreatedDate(LocalDateTime.now());
                transaction.setUpdatedDate(LocalDateTime.now());
                newTransaction = transactionRepository.save(transaction);
            }
        }

        // Return the newly created transaction object or null if any pre-conditions were not met
        return newTransaction;
    }
}
