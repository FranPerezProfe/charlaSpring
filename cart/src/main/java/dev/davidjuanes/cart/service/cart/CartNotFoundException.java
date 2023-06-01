package dev.davidjuanes.cart.service.cart;

public class CartNotFoundException extends RuntimeException {

    public CartNotFoundException(Long id) {
        super("Cart with id " + id + " not found.");
    }
}
