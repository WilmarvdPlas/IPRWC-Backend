package nl.hsleiden.gamecenter.DAOs;

import nl.hsleiden.gamecenter.models.Account;
import nl.hsleiden.gamecenter.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccountDAO {

    private final AccountRepository repository;

    @Autowired
    public AccountDAO(AccountRepository repository) {
        this.repository = repository;
    }

    public List<Account> getAccounts() {
        return repository.findAll();
    }

}
