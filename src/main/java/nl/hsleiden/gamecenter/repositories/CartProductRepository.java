package nl.hsleiden.gamecenter.repositories;

import nl.hsleiden.gamecenter.models.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.UUID;

@Repository
public interface CartProductRepository extends JpaRepository<CartProduct, UUID> {

    @Query("SELECT cartProduct FROM CartProduct cartProduct WHERE cartProduct.account.id = ?1")
    ArrayList<CartProduct> findByAccount(UUID accountId);

}
