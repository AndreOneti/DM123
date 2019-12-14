package br.com.siecola.aws_project01.controller;

import br.com.siecola.aws_project01.enums.EventType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.siecola.aws_project01.model.Product;
import br.com.siecola.aws_project01.service.ProductPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import br.com.siecola.aws_project01.repository.ProductRepository;

import java.util.Optional;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductPublisher productPublisher;
    private ProductRepository productRepository;

    @Autowired
    public ProductController(ProductRepository productRepositoryy,
                             ProductPublisher productPublisher) {
        this.productRepository = productRepositoryy;
        this.productPublisher = productPublisher;
    }

    @GetMapping
    public Iterable<Product> findAll() {
        return productRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable long id) {
        Optional<Product> optProduct = productRepository.findById(id);
        if (optProduct.isPresent()) {
            return new ResponseEntity<Product>(optProduct.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Product> saveProduct(
            @RequestBody @Valid Product product) {
        Product productSaved = productRepository.save(product);
        productPublisher.publishProductEvent(productSaved, EventType.PRODUCT_CREATED, "matilde");
        return new ResponseEntity<Product>(productSaved
                , HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Product> updateProduct(
            @RequestBody @Valid Product product, @PathVariable("id") long id) {
        if (productRepository.existsById(id)) {
            product.setId(id);
            Product productSaved = productRepository.save(product);
            productPublisher.publishProductEvent(productSaved, EventType.PRODUCT_UPDATE, "doralice");
            return new ResponseEntity<Product>(productSaved,
                    HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable("id") long id) {
        Optional<Product> optProduct = productRepository.findById(id);
        if (optProduct.isPresent()) {
            Product product = optProduct.get();
            productRepository.delete(product);
            productPublisher.publishProductEvent(product, EventType.PRODUCT_DELETED, "hannah");
            return new ResponseEntity<Product>(product, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/bycode")
    public ResponseEntity<Product> findByCode(@RequestParam String code) {
        Optional<Product> optProduct = productRepository.findByCode(code);
        if (optProduct.isPresent()) {
            return new ResponseEntity<Product>(optProduct.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
