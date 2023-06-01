package dev.davidjuanes.cart.api.dto;

import dev.davidjuanes.domain.AbstractDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CartItemDto extends AbstractDto {
    private String name;
    private double priceWithoutTaxes;
    private double priceWithTaxes;
}
