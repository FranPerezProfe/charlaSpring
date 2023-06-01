package dev.davidjuanes.cart.service.product;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ProductService {
    @Value( "${myshop.products.apiRoot}" )
    private String apiRoot;
    private static final String PRODUCTS_URI = "/products";
    private final RestTemplate restTemplate = new RestTemplate();

    public void validateProductsExist(List<Long> productIds) {
        List<Product> products = restTemplate.exchange(apiRoot + PRODUCTS_URI,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Product>>() {}).getBody();
        if (products == null) {
            throw new RuntimeException("Unexpected issue fetching products while checking if the products in the cart exist");
        }
        List<Long> productIdsFromApi = products.stream().map(Product::getId).toList();
        List<Long> compareIds = new ArrayList<>(productIds);
        compareIds.removeAll(productIdsFromApi);

        if (!compareIds.isEmpty()) {
            throw new ProductsNotFoundException(compareIds);
        }
    }

    public Product getProductDetails(Long id) {
        log.info("Getting product details for product ID {}", id);
        return restTemplate.exchange(apiRoot + PRODUCTS_URI + "/" + id,
                HttpMethod.GET, null,
                Product.class).getBody();
    }
}
