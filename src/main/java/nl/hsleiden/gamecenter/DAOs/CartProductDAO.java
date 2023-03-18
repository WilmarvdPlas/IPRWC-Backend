package nl.hsleiden.gamecenter.DAOs;

import nl.hsleiden.gamecenter.models.CartProduct;
import nl.hsleiden.gamecenter.repositories.CartProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.UUID;

@Component
public class CartProductDAO {

    private final CartProductRepository repository;

    @Autowired
    public CartProductDAO(CartProductRepository repository) {
        this.repository = repository;
    }

    public void saveCartProduct(CartProduct cartProduct) {
        repository.save(cartProduct);
    }

    public CartProduct getCartProductByProductAndAccount(UUID productId, UUID accountId) {
        return repository.findCartProductByProductAndAccount(productId, accountId);
    }

    public ArrayList<CartProduct> getCartProductsByAccount(UUID accountId) {
        return repository.findByAccount(accountId);
    }

    public void updateCount(UUID id, int count) {
        CartProduct cartProduct = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        cartProduct.setCount(count);
        repository.save(cartProduct);
    }

    public void deleteCartProduct(UUID id) {
        CartProduct cartProduct = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        repository.delete(cartProduct);
    }

}
