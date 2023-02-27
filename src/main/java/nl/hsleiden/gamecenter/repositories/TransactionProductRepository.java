package nl.hsleiden.gamecenter.repositories;

import nl.hsleiden.gamecenter.models.TransactionProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TransactionProductRepository extends JpaRepository<TransactionProduct, UUID> {

}
