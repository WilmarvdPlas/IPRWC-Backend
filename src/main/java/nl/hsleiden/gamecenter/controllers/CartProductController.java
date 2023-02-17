package nl.hsleiden.gamecenter.controllers;

import nl.hsleiden.gamecenter.DAOs.CartProductDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/cart_product")
public class CartProductController {

    private final CartProductDAO cartProductDAO;

    @Autowired
    public CartProductController(CartProductDAO cartProductDAO) {
        this.cartProductDAO = cartProductDAO;
    }

}
