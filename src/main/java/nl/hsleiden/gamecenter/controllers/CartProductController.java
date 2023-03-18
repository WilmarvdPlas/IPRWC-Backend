package nl.hsleiden.gamecenter.controllers;

import nl.hsleiden.gamecenter.DAOs.CartProductDAO;
import nl.hsleiden.gamecenter.models.CartProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
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
        CartProduct cartProductByProduct = cartProductDAO.getCartProductByProductAndAccount(cartProduct.getProduct().getId(), cartProduct.getAccount().getId());

        if (cartProductByProduct == null) {
            this.cartProductDAO.saveCartProduct(cartProduct);
            return new ResponseEntity<>(HttpStatus.OK);

        } else if (cartProductByProduct.getCount() < 100) {
            cartProductByProduct.setCount(cartProductByProduct.getCount() + 1);
            this.cartProductDAO.saveCartProduct(cartProductByProduct);
            return new ResponseEntity<>(HttpStatus.OK);

        } else if (cartProductByProduct.getCount() >= 100) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);

        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "account={id}")
    public  ResponseEntity<ArrayList<CartProduct>> getCartProductsByAccount(@PathVariable("id") UUID accountId) {
        return new ResponseEntity<>(cartProductDAO.getCartProductsByAccount(accountId), HttpStatus.OK);
    }

    @PutMapping(path = "{id}/update_count")
    public ResponseEntity<Object> updateCount(@PathVariable("id") UUID id, @RequestBody int count) {
        cartProductDAO.updateCount(id, count);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<Object> deleteCartProductByProductIdAndAccountId(@PathVariable("id") UUID id) {
        try {
            cartProductDAO.deleteCartProduct(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EntityNotFoundException exception) {
            return new ResponseEntity<>("Entity could not be found.", HttpStatus.NOT_FOUND);
        }

    }

}
