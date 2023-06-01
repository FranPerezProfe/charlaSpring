package dev.davidjuanes.cart.model;

import dev.davidjuanes.cart.api.dto.CartItemDto;
import dev.davidjuanes.domain.AbstractEntity;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
public class CartItem extends AbstractEntity<CartItem, CartItemDto> {
    private Long id;
    private String name;
    private double priceWithoutTaxes;
    private double priceWithTaxes;

    public CartItem() {
        super(CartItem.class, CartItemDto.class);
    }
}
