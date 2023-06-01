package dev.davidjuanes.cart.service.cart;

import dev.davidjuanes.cart.model.Cart;
import dev.davidjuanes.cart.model.CartItem;
import dev.davidjuanes.cart.model.DetailedCart;
import dev.davidjuanes.cart.repository.CartRepository;
import dev.davidjuanes.cart.service.product.Product;
import dev.davidjuanes.cart.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private ProductService productService;
    @Autowired
    private CartRepository cartRepository;

    public List<DetailedCart> getAllCarts() {
        List<Cart> carts = cartRepository.findAll();
        List<DetailedCart> detailedCarts = new ArrayList<>();
        carts.forEach(cart -> detailedCarts.add(getDetailedCart(cart)));
        return detailedCarts;
    }

    public Optional<DetailedCart> getCartById(Long id) {
        return cartRepository.findById(id).map(this::getDetailedCart);
    }

    public DetailedCart addCart(Cart cartToAdd) {
        productService.validateProductsExist(cartToAdd.getItems());
        Cart addedCart = cartRepository.save(cartToAdd);
        return getDetailedCart(addedCart);
    }

    public DetailedCart updateCart(Cart cartToUpdate) {
        return addCart(cartToUpdate);
    }

    public void deleteAllCarts() {
        cartRepository.deleteAll();
    }

    public void deleteCartById(Long cartId) {
        cartRepository.deleteById(cartId);
    }


    private DetailedCart getDetailedCart(Cart cart) {
        DetailedCart detailedCart = new DetailedCart(cart.getId());
        if (cart.getItems() == null) return detailedCart;
        cart.getItems().forEach(productId -> detailedCart.getItems().add(getItemForProductId(productId)));
        return detailedCart;
    }

    private CartItem getItemForProductId(Long productId) {
        CartItem cartItem = new CartItem();
        cartItem.setId(productId);
        Product product = productService.getProductDetails(productId);
        cartItem.setName(product.getName());
        cartItem.setPriceWithTaxes(product.getPrice());
        cartItem.setPriceWithoutTaxes(product.getPriceWithoutTaxes());
        return cartItem;
    }

}
