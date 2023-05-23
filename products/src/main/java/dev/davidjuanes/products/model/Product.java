package dev.davidjuanes.products.model;

import dev.davidjuanes.domain.AbstractEntity;
import dev.davidjuanes.domain.product.ProductDto;
import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter @Setter
@ToString @EqualsAndHashCode(callSuper = false)
public class Product extends AbstractEntity<Product, ProductDto> {
    private Long id;
    private String name;
    private String description;
    private double price;
    private double taxPercentage = 21.0; //Defaulting to 21% TAX
    private int stockUnits;

    public Product() {
        super(Product.class, ProductDto.class);
        // Define de mapping between Product and ProductDto. There's a map that the tool cannot implicitly match, and that is
        // the price: in the DTO, the price is the price with taxes, and in the Product, we have the price without taxes and
        // the tax to be applied. Here we add a explicit rule to map this field
        modelMapper.typeMap(Product.class, ProductDto.class).addMappings(
                mapper ->  {
                    mapper.map(Product::getRetailPrice, ProductDto::setPrice);
                    mapper.map(Product::getTaxAmount, ProductDto::setTaxAmount);
                    mapper.map(Product::hasStock, ProductDto::setAvailable);
                }
        );

        modelMapper.typeMap(ProductDto.class, Product.class).addMappings(
                mapper -> mapper.map(ProductDto::getPriceWithoutTax, Product::setPrice)
        );

    }

    public double getRetailPrice() {
        return round(price * (1 + (taxPercentage/100)));
    }

    public double getTaxAmount() {
        return round(getRetailPrice() - getPrice());
    }

    public boolean hasStock() {
        return stockUnits > 0;
    }

    private double round(double valueToRound) {
        BigDecimal bigDecimal = new BigDecimal(valueToRound);
        return bigDecimal.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
