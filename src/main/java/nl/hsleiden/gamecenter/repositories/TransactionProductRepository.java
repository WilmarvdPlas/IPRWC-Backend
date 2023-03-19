package nl.hsleiden.gamecenter.repositories;

import nl.hsleiden.gamecenter.models.TransactionProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.UUID;

@Repository
public interface TransactionProductRepository extends JpaRepository<TransactionProduct, UUID> {

    @Query("SELECT transactionProduct FROM TransactionProduct transactionProduct WHERE transactionProduct.transaction.id = ?1")
    ArrayList<TransactionProduct> getTransactionProductByTransaction(UUID transactionId);

}
