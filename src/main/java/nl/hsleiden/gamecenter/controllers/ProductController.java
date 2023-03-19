package nl.hsleiden.gamecenter.controllers;

import jakarta.persistence.EntityNotFoundException;
import nl.hsleiden.gamecenter.DAOs.ProductDAO;
import nl.hsleiden.gamecenter.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
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
    public ResponseEntity<Object> createProduct(@RequestBody Product product) {
        return new ResponseEntity<>(this.productDAO.createProduct(product), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getProducts() {
        return new ResponseEntity<>(productDAO.getProducts(), HttpStatus.OK);
    }

    @PutMapping(path = "{id}/edit_stock")
    public ResponseEntity<Object> editStock(@PathVariable("id") UUID id, @RequestBody int stockDifference) {
        try {
            this.productDAO.editStock(id, stockDifference);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EntityNotFoundException exc) {
            return new ResponseEntity<>("Entity could not be found.", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Optional<Product>> getProduct(@PathVariable("id") UUID id) {
        return new ResponseEntity<>(productDAO.getProduct(id), HttpStatus.OK);
    }

}
