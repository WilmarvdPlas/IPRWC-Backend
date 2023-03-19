package nl.hsleiden.gamecenter.controllers;

import lombok.RequiredArgsConstructor;
import nl.hsleiden.gamecenter.DAOs.TransactionProductDAO;
import nl.hsleiden.gamecenter.models.TransactionProduct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/transaction_product")
public class TransactionProductController {

    private final TransactionProductDAO transactionProductDAO;

    @PostMapping
    public ResponseEntity<Object> createTransactionProduct(@RequestBody TransactionProduct transactionProduct) {
        transactionProductDAO.createTransactionProduct(transactionProduct);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(path = "transaction={transactionId}")
    public ResponseEntity<ArrayList<TransactionProduct>> getTransactionProductsByTransaction(@PathVariable UUID transactionId) {
        return new ResponseEntity<>(transactionProductDAO.getTransactionProductsByTransaction(transactionId), HttpStatus.OK);
    }

}
