package nl.hsleiden.gamecenter.controllers;

import nl.hsleiden.gamecenter.DAOs.TransactionProductDAO;
import nl.hsleiden.gamecenter.models.TransactionProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/transaction_product")
public class TransactionProductController {

    private final TransactionProductDAO transactionProductDAO;

    @Autowired
    public TransactionProductController(TransactionProductDAO transactionProductDAO) {
        this.transactionProductDAO = transactionProductDAO;
    }

    @PostMapping
    public ResponseEntity<Object> createTransactionProduct(@RequestBody TransactionProduct transactionProduct) {
        transactionProductDAO.createTransactionProduct(transactionProduct);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
