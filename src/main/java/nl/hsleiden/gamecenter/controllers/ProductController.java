package nl.hsleiden.gamecenter.controllers;

import nl.hsleiden.gamecenter.DAOs.ProductDAO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/product")
public class ProductController {

    private final ProductDAO productDAO;

    public ProductController(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

}
