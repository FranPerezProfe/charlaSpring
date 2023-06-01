package dev.davidjuanes.cart.service.product;

import lombok.Data;

@Data
public class Product {
    private Long id;
    private String name;
    private double price;
    private double taxAmount;

    public double getPriceWithoutTaxes() {
        return price - taxAmount;
    }
}
