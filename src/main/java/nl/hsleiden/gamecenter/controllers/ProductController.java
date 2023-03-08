package nl.hsleiden.gamecenter.controllers;

import nl.hsleiden.gamecenter.DAOs.ProductDAO;
import nl.hsleiden.gamecenter.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/product")
public class ProductController {

    private final ProductDAO productDAO;

    @Autowired
    public ProductController(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @PostMapping
    public ResponseEntity createProduct(@RequestBody Product product) {
        this.productDAO.createProduct(product);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity getProducts() {
        return new ResponseEntity(productDAO.getProducts(), HttpStatus.OK);
    }
    
}
