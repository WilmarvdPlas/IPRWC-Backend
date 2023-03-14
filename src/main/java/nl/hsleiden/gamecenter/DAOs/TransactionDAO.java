package nl.hsleiden.gamecenter.DAOs;

import nl.hsleiden.gamecenter.models.Transaction;
import nl.hsleiden.gamecenter.repositories.TransactionRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.UUID;

@Component
public class TransactionDAO {

    private final TransactionRepository repository;

    public TransactionDAO(TransactionRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public UUID createTransaction(Transaction transaction) {
        repository.save(transaction);
        return transaction.getId();
    }

    public ArrayList<Transaction> getTransactionsByAccount(UUID accountId) {
        return repository.getTransactionsByAccount(accountId);
    }

}
