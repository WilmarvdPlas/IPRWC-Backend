package nl.hsleiden.gamecenter.DAOs;

import nl.hsleiden.gamecenter.models.Account;
import nl.hsleiden.gamecenter.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    public void saveAccount(Account account) {
        repository.save(account);
    }

    public Optional<Account> findAccountByEmail(String email) {
        return repository.findAccountByEmail(email);
    }

    public Optional<Account> findAccountById(UUID id) {
        return repository.findById(id);
    }

    public Optional<Account> getAccount(UUID id) {
        return repository.findById(id);
    }

    public void setAdministrator(UUID accountId) {
        Account account = repository.findById(accountId).get();
        account.setAdministrator(true);

        repository.save(account);
    }

    public void deleteAccount(UUID accountId) {
        Account account = repository.findById(accountId).get();

        repository.delete(account);
    }

}
