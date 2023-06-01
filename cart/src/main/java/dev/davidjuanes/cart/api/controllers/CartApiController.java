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

@RestController
@RequestMapping("/api/v1/carts")
@Slf4j
public class CartApiController {

    @Autowired
    private CartService cartService;

    @GetMapping
    public List<CartDto> getAllCarts() {
        log.info("Getting all carts...");
        return cartService.getAllCarts().stream().map(DetailedCart::mapToDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CartDto getCart(@PathVariable Long id) {
        log.info("Getting cart {}...", id);
        return cartService.getCartById(id).orElseThrow(() -> new CartNotFoundException(id)).mapToDto();
    }

    @PostMapping
    public CartDto addCart(@RequestBody NewOrUpdatedCartDto cartDto) {
        log.info("Creating new cart...");
        return cartService.addCart(new Cart().mapToEntity(cartDto)).mapToDto();
    }

    @PutMapping("/{id}")
    public CartDto updateCart(@PathVariable Long id, @RequestBody NewOrUpdatedCartDto cartDto) {
        log.info("Updating cart {}...", id);
        //Ensure ID matches
        cartDto.setId(id);
        // Check that cart exists
        getCart(id);
        return cartService.updateCart(new Cart().mapToEntity(cartDto)).mapToDto();
    }

    @DeleteMapping
    public void deleteAllCarts() {
        log.info("Deleting all carts...");
        cartService.deleteAllCarts();
    }

    @DeleteMapping("/{id}")
    public void deleteCart(@PathVariable Long id) {
        log.info("Deleting cart {}", id);
        cartService.deleteCartById(id);
    }
}
