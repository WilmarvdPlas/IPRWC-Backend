package nl.hsleiden.gamecenter.repositories;

import nl.hsleiden.gamecenter.models.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CartProductRepository extends JpaRepository<CartProduct, UUID> {

}
