package nl.hsleiden.gamecenter.controllers;

import nl.hsleiden.gamecenter.DAOs.CartProductDAO;
import nl.hsleiden.gamecenter.models.CartProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/cart_product")
public class CartProductController {

    private final CartProductDAO cartProductDAO;

    @Autowired
    public CartProductController(CartProductDAO cartProductDAO) {
        this.cartProductDAO = cartProductDAO;
    }

    @PostMapping
    public ResponseEntity<Object> addCartProduct(@RequestBody CartProduct cartProduct) {
        CartProduct cartProductByProduct = cartProductDAO.getCartProductByProduct(cartProduct.getProduct().getId());

        if (cartProductByProduct == null) {
            this.cartProductDAO.saveCartProduct(cartProduct);
            return new ResponseEntity<>(HttpStatus.OK);
        } else if (cartProductByProduct.getCount() < 100) {
            cartProductByProduct.setCount(cartProductByProduct.getCount() + 1);
            this.cartProductDAO.saveCartProduct(cartProductByProduct);
            return new ResponseEntity<>(HttpStatus.OK);
        } else if (cartProductByProduct.getCount() >= 100) {
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "account={id}")
    public  ResponseEntity<ArrayList<CartProduct>> getCartProductsByAccount(@PathVariable("id") UUID accountId) {
        return new ResponseEntity<>(cartProductDAO.getCartProductsByAccount(accountId), HttpStatus.OK);
    }

    @PutMapping(path = "product={productId}/update_count")
    public ResponseEntity<Object> updateCount(@PathVariable("productId") UUID productId, @RequestBody int count) {
        cartProductDAO.updateCount(productId, count);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "product={id}")
    public ResponseEntity<Object> deleteCartProductByProductId(@PathVariable("id") UUID id) {
        cartProductDAO.deleteCartProduct(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
