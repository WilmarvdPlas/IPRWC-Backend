package nl.hsleiden.gamecenter.DAOs;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import nl.hsleiden.gamecenter.models.Account;
import nl.hsleiden.gamecenter.repositories.AccountRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AccountDAO {

    private final AccountRepository repository;

    public List<Account> getAccounts() {
        return repository.getNonArchivedAccounts();
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
        Account account = repository.findById(accountId).orElseThrow(EntityNotFoundException::new);
        account.setAdministrator(true);

        repository.save(account);
    }

    public void setArchived(UUID accountId) {
        Account account = repository.findById(accountId).orElseThrow(EntityNotFoundException::new);

        account.setArchived(true);

        repository.save(account);
    }

}
