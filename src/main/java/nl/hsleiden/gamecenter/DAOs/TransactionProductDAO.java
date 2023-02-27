package nl.hsleiden.gamecenter.DAOs;

import nl.hsleiden.gamecenter.repositories.TransactionProductRepository;
import org.springframework.stereotype.Component;

@Component
public class TransactionProductDAO {

    private final TransactionProductRepository repository;

    public TransactionProductDAO(TransactionProductRepository repository) {
        this.repository = repository;
    }

}
