package nl.hsleiden.gamecenter.controllers;

import nl.hsleiden.gamecenter.DAOs.ProductDAO;
import nl.hsleiden.gamecenter.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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
        return new ResponseEntity(this.productDAO.createProduct(product), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity getProducts() {
        return new ResponseEntity(productDAO.getProducts(), HttpStatus.OK);
    }

    @PutMapping(path = "{id}")
    public ResponseEntity addStock(@PathVariable("id") UUID id, @RequestBody int addedStock) {
        this.productDAO.addStock(id, addedStock);
        return new ResponseEntity(HttpStatus.OK);
    }

}
