package nl.hsleiden.gamecenter.repositories;

import nl.hsleiden.gamecenter.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {

    @Query("SELECT account FROM Account account WHERE account.email = ?1 AND account.archived = false")
    Optional<Account> findAccountByEmail(String email);

    @Query("SELECT account FROM Account account WHERE account.archived = false")
    ArrayList<Account> getNonArchivedAccounts();

}
