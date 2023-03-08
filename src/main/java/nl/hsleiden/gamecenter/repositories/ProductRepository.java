package nl.hsleiden.gamecenter.repositories;

import nl.hsleiden.gamecenter.models.Account;
import nl.hsleiden.gamecenter.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    @Query("SELECT product FROM Product product WHERE product.archived = false")
    ArrayList<Product> getNonArchivedProducts();

}
