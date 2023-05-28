package dev.davidjuanes.products.api.controllers;

import dev.davidjuanes.products.api.repositories.ProductRepository;
import dev.davidjuanes.products.model.Product;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ProductApiController.class)
@AutoConfigureRestDocs(outputDir = "build/generated-snippets", uriPort = 8001)
class ProductApiControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductRepository productRepository;

    @Test
    void getAllProducts() throws Exception {
        //Prepare mocked data
        Product product1 = new Product();
        product1.setId(1L);
        product1.setDescription("My First Product");
        product1.setDescription("This is the description of the product");
        product1.setPrice(10);
        product1.setTaxPercentage(21);

        Product product2 = new Product();
        product2.setId(1L);
        product2.setDescription("My Second Product");
        product2.setDescription("This is the description of the product");
        product2.setPrice(15);
        product2.setTaxPercentage(10);

        when(productRepository.findAll()).thenReturn(Lists.list(product1, product2));

        //Call under test
        this.mockMvc.perform(get("/api/v1/products")).andDo(print()).andExpect(status().isOk())
                .andDo(document("products-get", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()), responseFields(
                        fieldWithPath("[].id").description("Product ID"),
                        fieldWithPath("[].name").description("Product Name"),
                        fieldWithPath("[].description").description("Product Description"),
                        fieldWithPath("[].price").description("Retail Price (Including Taxes)"),
                        fieldWithPath("[].taxPercentage").description("Percentage of tax (VAT) applied to this product"),
                        fieldWithPath("[].taxAmount").description("Amount of tax included in the price"),
                        fieldWithPath("[].available").description("Tells if the product has stock or not"),
                        fieldWithPath("[].priceWithoutTax").description("Price without taxes")
                )));
    }

    @Test
    void getProduct() {
    }

    @Test
    void addProduct() {
    }

    @Test
    void updateProduct() {
    }

    @Test
    void deleteAllProducts() {
    }

    @Test
    void deleteProduct() {
    }
}