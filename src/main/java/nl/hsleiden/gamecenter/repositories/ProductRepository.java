package nl.hsleiden.gamecenter.repositories;

import nl.hsleiden.gamecenter.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {

}
