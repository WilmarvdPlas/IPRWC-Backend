package nl.hsleiden.gamecenter.controllers;

import nl.hsleiden.gamecenter.DAOs.TransactionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/transaction_product")
public class TransactionProductController {

    private final TransactionDAO transactionDAO;

    @Autowired
    public TransactionProductController(TransactionDAO transactionDAO) {
        this.transactionDAO = transactionDAO;
    }

}
