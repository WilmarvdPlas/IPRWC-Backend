package nl.hsleiden.gamecenter.DAOs;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import nl.hsleiden.gamecenter.models.CartProduct;
import nl.hsleiden.gamecenter.repositories.CartProductRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CartProductDAO {

    private final CartProductRepository repository;

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
