package nl.hsleiden.gamecenter.DAOs;

import nl.hsleiden.gamecenter.repositories.ProductRepository;
import org.springframework.stereotype.Component;

@Component
public class ProductDAO {

    private final ProductRepository repository;

    public ProductDAO(ProductRepository repository) {
        this.repository = repository;
    }

}
