package nl.hsleiden.gamecenter.DAOs;

import lombok.RequiredArgsConstructor;
import nl.hsleiden.gamecenter.models.Transaction;
import nl.hsleiden.gamecenter.repositories.TransactionRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TransactionDAO {

    private final TransactionRepository repository;

    @Transactional
    public UUID createTransaction(Transaction transaction) {
        repository.save(transaction);
        return transaction.getId();
    }

    public ArrayList<Transaction> getTransactionsByAccount(UUID accountId) {
        return repository.getTransactionsByAccount(accountId);
    }

    public List<Transaction> getTransactions() {
        return repository.findAll();
    }

}
