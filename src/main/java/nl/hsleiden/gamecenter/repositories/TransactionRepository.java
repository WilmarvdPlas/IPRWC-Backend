package nl.hsleiden.gamecenter.repositories;

import nl.hsleiden.gamecenter.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    @Query("SELECT transaction FROM Transaction transaction WHERE transaction.account.id = ?1")
    ArrayList<Transaction> getTransactionsByAccount(UUID accountId);

}