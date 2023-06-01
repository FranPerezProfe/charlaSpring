package dev.davidjuanes.cart.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.davidjuanes.domain.AbstractDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class CartDto extends AbstractDto {
    private List<CartItemDto> items;

    @JsonProperty(value = "totalPrice", access = JsonProperty.Access.READ_ONLY)
    public double calcTotalPrice() {
        BigDecimal bigDecimal = BigDecimal.valueOf(items.stream().mapToDouble(CartItemDto::getPriceWithTaxes).sum());
        return bigDecimal.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    @JsonProperty(value = "totalPriceWithoutTaxes", access = JsonProperty.Access.READ_ONLY)
    public double calcTotalPriceWithoutTaxes() {
        BigDecimal bigDecimal = BigDecimal.valueOf(items.stream().mapToDouble(CartItemDto::getPriceWithoutTaxes).sum());
        return bigDecimal.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
