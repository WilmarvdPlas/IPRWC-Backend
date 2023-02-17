package nl.hsleiden.gamecenter.DAOs;

import nl.hsleiden.gamecenter.repositories.CartProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CartProductDAO {

    private final CartProductRepository repository;

    @Autowired
    public CartProductDAO(CartProductRepository repository) {
        this.repository = repository;
    }

}
