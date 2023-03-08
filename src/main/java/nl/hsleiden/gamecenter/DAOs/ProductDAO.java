package nl.hsleiden.gamecenter.DAOs;

import nl.hsleiden.gamecenter.models.Product;
import nl.hsleiden.gamecenter.repositories.ProductRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.UUID;

@Component
public class ProductDAO {

    private final ProductRepository repository;

    public ProductDAO(ProductRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public UUID createProduct(Product product) {
        this.repository.save(product);
        return product.getId();
    }

    public ArrayList<Product> getProducts() {
        return this.repository.getNonArchivedProducts();
    }

    @Transactional
    public void addStock(UUID id, int addedStock) {
        Product product = this.repository.findById(id).get();
        int productStock = product.getStock();
        product.setStock(productStock + addedStock);
    }

}
