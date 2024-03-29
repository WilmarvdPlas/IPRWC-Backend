package nl.hsleiden.gamecenter.controllers;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import nl.hsleiden.gamecenter.DAOs.ProductDAO;
import nl.hsleiden.gamecenter.models.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/product")
public class ProductController {

    private final ProductDAO productDAO;

    @PostMapping
    public ResponseEntity<Object> createProduct(@RequestBody Product product) {
        if (product.getDiscountPercentage() < 0 || product.getDiscountPercentage() > 100) {
            return new ResponseEntity<>("Discount percentage must be between 0 and 100.", HttpStatus.CONFLICT);
        } else if (product.getPriceEuro() < 0.01) {
            return new ResponseEntity<>("Price must be bigger than €0.01", HttpStatus.CONFLICT);
        }
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
    public ResponseEntity<Object> getProduct(@PathVariable("id") UUID id) {
        try {
            Product product = productDAO.getProduct(id).orElseThrow(EntityNotFoundException::new);
            return new ResponseEntity<>(product, HttpStatus.OK);
        } catch (EntityNotFoundException exception) {
            return new ResponseEntity<>("Entity could not be found.", HttpStatus.NOT_FOUND);
        }
    }
}
