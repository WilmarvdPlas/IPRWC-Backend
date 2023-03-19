package nl.hsleiden.gamecenter.DAOs;

import lombok.RequiredArgsConstructor;
import nl.hsleiden.gamecenter.models.TransactionProduct;
import nl.hsleiden.gamecenter.repositories.TransactionProductRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TransactionProductDAO {

    private final TransactionProductRepository repository;

    public void createTransactionProduct(TransactionProduct transactionProduct) {
        repository.save(transactionProduct);
    }

    public ArrayList<TransactionProduct> getTransactionProductsByTransaction(UUID transactionId) {
        return repository.getTransactionProductByTransaction(transactionId);
    }

}
