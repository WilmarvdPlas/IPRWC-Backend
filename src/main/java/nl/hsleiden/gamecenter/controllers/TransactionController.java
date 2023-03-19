package nl.hsleiden.gamecenter.controllers;

import lombok.RequiredArgsConstructor;
import nl.hsleiden.gamecenter.DAOs.TransactionDAO;
import nl.hsleiden.gamecenter.models.Transaction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/transaction")
public class TransactionController {

    private final TransactionDAO transactionDAO;

    @PostMapping
    public ResponseEntity<UUID> createTransaction(@RequestBody Transaction transaction) {
        return new ResponseEntity<>(transactionDAO.createTransaction(transaction), HttpStatus.CREATED);
    }

    @GetMapping(path = "account={accountId}")
    public ResponseEntity<ArrayList<Transaction>> getTransactionsByAccount(@PathVariable UUID accountId) {
        return new ResponseEntity<>(transactionDAO.getTransactionsByAccount(accountId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getTransactions() {
        return new ResponseEntity<>(transactionDAO.getTransactions(), HttpStatus.OK);
    }

}
