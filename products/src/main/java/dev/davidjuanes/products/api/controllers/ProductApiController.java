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

//TODO Add annotations to mark this class as as REST Controller
@CrossOrigin
@Slf4j
public class ProductApiController {

    //TODO Inject dependency on the Repository to perform DB actions

    //TODO Add annotation to map this method to a GET call (GET /api/v1/products)
    public List<ProductDto> getAllProducts() {
        log.info("Getting all products...");
        //TODO Implement call to repository to get all products
        return null; //FIXME remove me
    }

    //TODO Add annotation to map this method to a GET call (GET /api/v1/products/{id})
    //TODO Don't forget to add @PathVariable to the part of the URL that should be passed as variable (hint: the id)
    public ProductDto getProduct(Long id) {
        log.info("Getting product {}...", id);
        //TODO call to the repository to find the product by ID, and if not found, throw a ProductNotFoundException
        return null; //FIXME remove me
    }
    //TODO Add annotation to map this method to a POST call (POST /api/v1/products)
    //TODO Don't forget to add @RequestBody to the object that holds the product to create
    public ProductDto addProduct(NewOrUpdatedProductDto product) {
        log.info("Adding new product...");
        //TODO add call to repository to save the new product. Don't forget to translate it from DTO to Entity
        return null; //FIXME remove me
    }

    //TODO Add annotation to map this method to a PUT call (PUT /api/v1/products/{id})
    //TODO Don't forget to add @RequestBody to the object that holds the product to update
    //TODO Don't forget to add @PathVariable to the part of the URL that should be passed as variable (hint: the id)
    public ProductDto updateProduct(NewOrUpdatedProductDto updatedProduct, Long id) {
        log.info("Updating product {}...", id);
        //TODO Ensure ID is correctly set by setting the ID in the path to the id property in the DTO
        
        //TODO call to the repository to check if the product exists, otherwise throw not found exception
        
        //TODO Call to the repository to update the product
        
        return null; //FIXME remove me
    }

    //TODO Add annotation to map this method to a DELETE call (DELETE /api/v1/products)
    public void deleteAllProducts() {
        log.info("Deleting all products...");
        //TODO Call to the repository to delete all products
    }

    //TODO Add annotation to map this method to a DELETE call (DELETE /api/v1/products/{id})
    //TODO Don't forget to add @PathVariable to the part of the URL that should be passed as variable (hint: the id)
    public void deleteProduct(Long id) {
        log.info("Deleting product {}", id);
        //TODO Call to the repository to delete the product by id
    }
}
