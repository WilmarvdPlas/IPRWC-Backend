package nl.hsleiden.gamecenter.controllers;

import nl.hsleiden.gamecenter.DAOs.CartProductDAO;
import nl.hsleiden.gamecenter.models.CartProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity createCartProduct(@RequestBody CartProduct cartProduct) {
        this.cartProductDAO.createCartProduct(cartProduct);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(path = "account={id}")
    public  ResponseEntity getCartProductsByAccount(@PathVariable("id") UUID accountId) {
        return new ResponseEntity(cartProductDAO.getCartProductsByAccount(accountId), HttpStatus.OK);
    }

}
