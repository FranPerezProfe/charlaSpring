package dev.davidjuanes.products.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.davidjuanes.domain.AbstractDto;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Data
public class ProductDto extends AbstractDto {
    private String name;
    private String description;
    private double price;
    private double taxPercentage;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private double taxAmount;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private boolean available;

    public double getPriceWithoutTax() {
        return round(price / (1 + (taxPercentage/100)));
    }

    private double round(double valueToRound) {
        BigDecimal bigDecimal = new BigDecimal(valueToRound);
        return bigDecimal.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

}
