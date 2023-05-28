package dev.davidjuanes.products.api.controllers;

import dev.davidjuanes.domain.AbstractEntity;
import dev.davidjuanes.domain.product.ProductDto;
import dev.davidjuanes.products.api.errors.ProductNotFoundException;
import dev.davidjuanes.products.api.repositories.ProductRepository;
import dev.davidjuanes.products.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/products")
public class ProductApiController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public List<ProductDto> getAllProducts() {
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
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id)).mapToDto();
    }
    @PostMapping
    public ProductDto addProduct(@RequestBody Product product) {
        return productRepository.save(product).mapToDto();
    }

    @PutMapping("/{id}")
    public ProductDto updateProduct(@RequestBody  Product updatedProduct, @PathVariable Long id) {
        updatedProduct.setId(id); //Ensure ID is correctly set
        return productRepository.save(updatedProduct).mapToDto();
    }

    @DeleteMapping
    public void deleteAllProducts() {
        productRepository.deleteAll();
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productRepository.deleteById(id);
    }
}
