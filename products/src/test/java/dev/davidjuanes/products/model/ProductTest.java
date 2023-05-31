package dev.davidjuanes.products.model;

import dev.davidjuanes.products.api.dto.ProductDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class ProductTest {

    @BeforeAll
    static void beforeAll() {
        log.info("Mappings for Product -> ProductDto");
        new Product().getModelMapper().getTypeMap(Product.class, ProductDto.class).getMappings().forEach(mapping -> log.info("{}", mapping));
        log.info("Mappings for ProductDto -> Product");
        new Product().getModelMapper().getTypeMap(ProductDto.class, Product.class).getMappings().forEach(mapping -> log.info("{}", mapping));
    }

    @Test
    void testMapToDto() {
        Product product = new Product();
        product.setId(2L);
        product.setName("myFirstProduct");
        product.setDescription("myDescription");
        product.setPrice(10.5);
        product.setTaxPercentage(10.0);
        product.setStockUnits(5);

        //Call under test
        ProductDto dtoUnderTest = product.mapToDto(product);

        //Checks
        assertEquals(2L, dtoUnderTest.getId());
        assertEquals("myFirstProduct", dtoUnderTest.getName());
        assertEquals("myDescription", dtoUnderTest.getDescription());
        assertEquals(11.55, dtoUnderTest.getPrice());
        assertTrue(dtoUnderTest.isAvailable());
        assertEquals(10.0, dtoUnderTest.getTaxPercentage());
        assertEquals(1.05, dtoUnderTest.getTaxAmount());
    }

    @Test
    void testMapToDtoNoStock() {
        Product product = new Product();
        product.setStockUnits(0);

        //Call under test
        ProductDto dtoUnderTest = product.mapToDto(product);

        //Checks
        assertFalse(dtoUnderTest.isAvailable());
    }

    @Test
    void testMapToEntity() {
        ProductDto productDto = new ProductDto();
        productDto.setId(5L);
        productDto.setName("Product1");
        productDto.setDescription("myCoolDescription");
        productDto.setPrice(43.5);
        productDto.setTaxPercentage(10.0);

        Product product = new Product().mapToEntity(productDto);

        assertEquals(5L, product.getId());
        assertEquals("Product1", product.getName());
        assertEquals("myCoolDescription", product.getDescription());
        assertEquals(39.55, product.getPrice());
        assertEquals(10.0, product.getTaxPercentage());
    }

    @Test
    void testBack2BackMapping() {
        Product product = new Product();
        product.setId(2L);
        product.setName("myFirstProduct");
        product.setDescription("myDescription");
        product.setPrice(10.5);
        product.setTaxPercentage(10.0);
        product.setStockUnits(5);

        //Call under test
        ProductDto dtoUnderTest = product.mapToDto(product);

        //Checks
        assertEquals(2L, dtoUnderTest.getId());
        assertEquals("myFirstProduct", dtoUnderTest.getName());
        assertEquals("myDescription", dtoUnderTest.getDescription());
        assertEquals(11.55, dtoUnderTest.getPrice());
        assertTrue(dtoUnderTest.isAvailable());
        assertEquals(10.0, dtoUnderTest.getTaxPercentage());
        assertEquals(1.05, dtoUnderTest.getTaxAmount());

        //Call under test 2
        Product productUnderTest = new Product().mapToEntity(dtoUnderTest);

        //Checks 2
        assertEquals(2L, productUnderTest.getId());
        assertEquals("myFirstProduct", productUnderTest.getName());
        assertEquals("myDescription", productUnderTest.getDescription());
        assertEquals(10.5, productUnderTest.getPrice());
        assertEquals(10.0,  productUnderTest.getTaxPercentage());
    }
}