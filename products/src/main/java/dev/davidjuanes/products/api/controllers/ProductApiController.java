package dev.davidjuanes.products.api.controllers;

import dev.davidjuanes.products.api.dto.NewOrUpdatedProductDto;
import dev.davidjuanes.products.api.dto.ProductDto;
import dev.davidjuanes.products.api.errors.ProductNotFoundException;
import dev.davidjuanes.products.repository.ProductRepository;
import dev.davidjuanes.products.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/products")
@CrossOrigin
@Slf4j
public class ProductApiController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public List<ProductDto> getAllProducts() {
        log.info("Getting all products...");
        return productRepository.findAll().stream().map(Product::mapToDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ProductDto getProduct(@PathVariable Long id) {
//        Optional<Product> productOptional = productRepository.findById(id);
//        if(productOptional.isPresent()) {
//            return productOptional.get().mapToDto();
//        } else {
//            throw new ProductNotFoundException(id);
//        }
        log.info("Getting product {}...", id);
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id)).mapToDto();
    }
    @PostMapping
    public ProductDto addProduct(@RequestBody NewOrUpdatedProductDto product) {
        return productRepository.save(new Product().mapToEntity(product)).mapToDto();
    }

    @PutMapping("/{id}")
    public ProductDto updateProduct(@RequestBody NewOrUpdatedProductDto updatedProduct, @PathVariable Long id) {
        log.info("Updating product {}...", id);
        //Ensure ID is correctly set
        updatedProduct.setId(id);
        //Check if the product exists, otherwise throw not found exception
        productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        return productRepository.save(new Product().mapToEntity(updatedProduct)).mapToDto();
    }

    @DeleteMapping
    public void deleteAllProducts() {
        log.info("Deleting all products...");
        productRepository.deleteAll();
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        log.info("Deleting product {}", id);
        productRepository.deleteById(id);
    }
}
