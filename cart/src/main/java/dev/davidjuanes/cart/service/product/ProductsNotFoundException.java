package dev.davidjuanes.cart.service.product;

import java.util.List;

public class ProductsNotFoundException extends RuntimeException {
    public ProductsNotFoundException(List<Long> productIdsNotFound) {
        super("The following products cannot be added to the cart because they don't exist: " + productIdsNotFound);
    }

}
