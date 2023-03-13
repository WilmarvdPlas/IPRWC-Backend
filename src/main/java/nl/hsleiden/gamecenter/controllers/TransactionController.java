package nl.hsleiden.gamecenter.controllers;

import nl.hsleiden.gamecenter.DAOs.TransactionDAO;
import nl.hsleiden.gamecenter.models.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/transaction")
public class TransactionController {

    private final TransactionDAO transactionDAO;

    @Autowired
    public TransactionController(TransactionDAO transactionDAO) {
        this.transactionDAO = transactionDAO;
    }

    @PostMapping
    public ResponseEntity<Object> createTransaction(@RequestBody Transaction transaction) {
        transactionDAO.createTransaction(transaction);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
