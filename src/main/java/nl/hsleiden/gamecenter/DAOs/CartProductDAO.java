package nl.hsleiden.gamecenter.DAOs;

import nl.hsleiden.gamecenter.models.CartProduct;
import nl.hsleiden.gamecenter.repositories.CartProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.UUID;

@Component
public class CartProductDAO {

    private final CartProductRepository repository;

    @Autowired
    public CartProductDAO(CartProductRepository repository) {
        this.repository = repository;
    }

    public void addCartProduct(CartProduct cartProduct) {
        CartProduct cartProductByProduct = repository.findCartProductByProduct(cartProduct.getProduct().getId());

        if (cartProductByProduct == null) {
            repository.save(cartProduct);
        } else {
            cartProductByProduct.setCount(cartProductByProduct.getCount() + 1);
            repository.save(cartProductByProduct);
        }
    }

    public ArrayList<CartProduct> getCartProductsByAccount(UUID accountId) {
        return repository.findByAccount(accountId);
    }

}
