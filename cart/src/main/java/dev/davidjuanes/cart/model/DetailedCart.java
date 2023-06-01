package dev.davidjuanes.cart.model;

import dev.davidjuanes.cart.api.dto.CartDto;
import dev.davidjuanes.domain.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class DetailedCart extends AbstractEntity<DetailedCart, CartDto> {
    private Long id;
    private List<CartItem> items = new ArrayList<>();
    public DetailedCart(Long cartId) {
        super(DetailedCart.class, CartDto.class);
        this.setId(cartId);
    }
}
