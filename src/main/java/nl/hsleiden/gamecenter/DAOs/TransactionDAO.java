package nl.hsleiden.gamecenter.DAOs;

import nl.hsleiden.gamecenter.repositories.TransactionRepository;
import org.springframework.stereotype.Component;

@Component
public class TransactionDAO {

    private final TransactionRepository repository;

    public TransactionDAO(TransactionRepository repository) {
        this.repository = repository;
    }

}