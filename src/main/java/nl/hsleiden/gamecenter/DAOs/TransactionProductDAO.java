package nl.hsleiden.gamecenter.DAOs;

import nl.hsleiden.gamecenter.models.TransactionProduct;
import nl.hsleiden.gamecenter.repositories.TransactionProductRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.UUID;

@Component
public class TransactionProductDAO {

    private final TransactionProductRepository repository;

    public TransactionProductDAO(TransactionProductRepository repository) {
        this.repository = repository;
    }

    public void createTransactionProduct(TransactionProduct transactionProduct) {
        repository.save(transactionProduct);
    }

    public ArrayList<TransactionProduct> getTransactionProductsByTransaction(UUID transactionId) {
        return repository.getTransactionProductByTransaction(transactionId);
    }

}
