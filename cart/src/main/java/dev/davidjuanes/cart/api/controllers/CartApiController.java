package dev.davidjuanes.cart.api.controllers;

import dev.davidjuanes.cart.api.dto.CartDto;
import dev.davidjuanes.cart.api.dto.NewOrUpdatedCartDto;
import dev.davidjuanes.cart.model.Cart;
import dev.davidjuanes.cart.model.DetailedCart;
import dev.davidjuanes.cart.service.cart.CartNotFoundException;
import dev.davidjuanes.cart.service.cart.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

//TODO Add annotations to mark this class as as REST Controller
@Slf4j
public class CartApiController {

    //TODO Inject dependency on cart service to perform the operations

    //TODO Add annotation to map this method to a GET call (GET /api/v1/carts)
    public List<CartDto> getAllCarts() {
        log.info("Getting all carts...");
        //TODO Implement call to cartService to get all carts. Don't forget to translate to dto before returning.
        return null; //FIXME remove me
    }

    //TODO Add annotation to map this method to a GET call (GET /api/v1/carts/{id})
    //TODO Don't forget to add @PathVariable to the part of the URL that should be passed as variable (hint: the id)
    public CartDto getCart(Long id) {
        log.info("Getting cart {}...", id);
        //TODO call to the cart service to find the product by ID, and if not found, throw a CartNotFoundException
        return null; //FIXME remove me
    }

    //TODO Add annotation to map this method to a POST call (POST /api/v1/carts)
    //TODO Don't forget to add @RequestBody to the object that holds the cart to create
    public CartDto addCart(NewOrUpdatedCartDto cartDto) {
        log.info("Creating new cart...");
        //TODO add call to cart service to save the new product. Don't forget to translate it from DTO to Entity
        return null; //FIXME remove me
    }

    //TODO Add annotation to map this method to a PUT call (PUT /api/v1/carts/{id})
    //TODO Don't forget to add @RequestBody to the object that holds the cart to update
    //TODO Don't forget to add @PathVariable to the part of the URL that should be passed as variable (hint: the id)
    public CartDto updateCart(Long id, NewOrUpdatedCartDto cartDto) {
        log.info("Updating cart {}...", id);
        //TODO Ensure ID is correctly set by setting the ID in the path to the id property in the DTO

        //TODO call to the cart service to check if the cart exists, otherwise throw not found exception

        //TODO Call to the cart service to update the product
        return null; //FIXME remove me
    }

    //TODO Add annotation to map this method to a DELETE call (DELETE /api/v1/carts)
    public void deleteAllCarts() {
        log.info("Deleting all carts...");
        //TODO Call to the cart service to delete all products
    }

    //TODO Add annotation to map this method to a DELETE call (DELETE /api/v1/carts/{id})
    //TODO Don't forget to add @PathVariable to the part of the URL that should be passed as variable (hint: the id)
    public void deleteCart(Long id) {
        log.info("Deleting cart {}", id);
        //TODO Call to the repository to delete the product by id
    }
}
