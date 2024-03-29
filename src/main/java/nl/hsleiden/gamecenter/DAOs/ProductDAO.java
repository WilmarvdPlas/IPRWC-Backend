package nl.hsleiden.gamecenter.DAOs;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import nl.hsleiden.gamecenter.models.Product;
import nl.hsleiden.gamecenter.repositories.ProductRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ProductDAO {

    private final ProductRepository repository;

    @Transactional
    public UUID createProduct(Product product) {
        this.repository.save(product);
        return product.getId();
    }

    public ArrayList<Product> getProducts() {
        return repository.getNonArchivedProducts();
    }

    public void editStock(UUID id, int stockDifference) {
        Product product = this.repository.findById(id).orElseThrow(EntityNotFoundException::new);

        int productStock = product.getStock();
        product.setStock(productStock + stockDifference);

        repository.save(product);
    }

    public Optional<Product> getProduct(UUID id) {
        return repository.findById(id);
    }

}
