package dev.davidjuanes.products.repository;

import dev.davidjuanes.products.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

//TODO Extend from JpaRepository interface to make this interface a Spring JPA repository.
public interface ProductRepository extends JpaRepository<Product,Long>{
}
