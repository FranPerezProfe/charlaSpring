package dev.davidjuanes.products.repository;

import dev.davidjuanes.products.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
