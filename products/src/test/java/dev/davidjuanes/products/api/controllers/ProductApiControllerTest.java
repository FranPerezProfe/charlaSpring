package dev.davidjuanes.products.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.davidjuanes.products.api.dto.NewOrUpdatedProductDto;
import dev.davidjuanes.products.api.errors.ProductNotFoundException;
import dev.davidjuanes.products.repository.ProductRepository;
import dev.davidjuanes.products.model.Product;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;


import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ProductApiController.class)
@AutoConfigureRestDocs(outputDir = "build/generated-snippets", uriPort = 8001)
class ProductApiControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductRepository productRepository;

    private ObjectMapper objectMapper = new ObjectMapper();
    private HttpHeaders headers;

    @BeforeEach
    void setup() {
        headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
    }

    @Test
    void getAllProducts() throws Exception {
        //Prepare mocked data
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("My First Product");
        product1.setDescription("This is the description of the product");
        product1.setPrice(10);
        product1.setTaxPercentage(21);

        Product product2 = new Product();
        product2.setId(1L);
        product2.setName("My Second Product");
        product2.setDescription("This is the description of the product");
        product2.setPrice(15);
        product2.setTaxPercentage(10);

        when(productRepository.findAll()).thenReturn(Lists.list(product1, product2));

        //Call under test
        this.mockMvc.perform(get("/api/v1/products"))
                .andExpect(status().isOk())
                .andDo(print())
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
    void getProduct() throws Exception {
        //Prepare mocked data
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("My First Product");
        product1.setDescription("This is the description of the product");
        product1.setPrice(10);
        product1.setTaxPercentage(21);

        when(productRepository.findById(eq(1L))).thenReturn(Optional.of(product1));

        //Call under test
        this.mockMvc.perform(get("/api/v1/products/1"))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("product-get", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()), responseFields(
                        fieldWithPath("id").description("Product ID"),
                        fieldWithPath("name").description("Product Name"),
                        fieldWithPath("description").description("Product Description"),
                        fieldWithPath("price").description("Retail Price (Including Taxes)"),
                        fieldWithPath("taxPercentage").description("Percentage of tax (VAT) applied to this product"),
                        fieldWithPath("taxAmount").description("Amount of tax included in the price"),
                        fieldWithPath("available").description("Tells if the product has stock or not"),
                        fieldWithPath("priceWithoutTax").description("Price without taxes")
                )));
    }

    @Test
    void getProductNotFound() throws Exception {
        //Prepare mocked data
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("My First Product");
        product1.setDescription("This is the description of the product");
        product1.setPrice(10);
        product1.setTaxPercentage(21);

        when(productRepository.findById(eq(1L))).thenReturn(Optional.of(product1));

        //Call under test
        this.mockMvc.perform(get("/api/v1/products/2"))
                .andExpect(status().isNotFound())
                .andDo(print())
                .andDo(document("product-get-not-found", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()), responseFields(
                        fieldWithPath("errorCode").description("HTTP Error code produced"),
                        fieldWithPath("errorMessage").description("Explanation of what went wrong")
                )));
    }

    @Test
    void addProduct() throws Exception {

        NewOrUpdatedProductDto productDto = new NewOrUpdatedProductDto();
        productDto.setName("My new product");
        productDto.setDescription("New product just launched! you should buy it");
        productDto.setPrice(25);
        productDto.setTaxPercentage(21);

        Product createdProduct = new Product().mapToEntity(productDto);
        createdProduct.setId(12L);

        when(productRepository.save(any())).thenReturn(createdProduct);

        //Call under test
        this.mockMvc.perform(post("/api/v1/products")
                        .content(objectMapper.writeValueAsBytes(productDto))
                        .headers(headers))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("products-post", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                        requestFields(
                            fieldWithPath("name").description("Product Name"),
                            fieldWithPath("description").description("Product Description"),
                            fieldWithPath("price").description("Retail Price (Including Taxes)"),
                            fieldWithPath("taxPercentage").description("Percentage of tax (VAT) applied to this product")
                        ),
                        responseFields(
                            fieldWithPath("id").description("Product ID given to the new product"),
                            fieldWithPath("name").description("Product Name"),
                            fieldWithPath("description").description("Product Description"),
                            fieldWithPath("price").description("Retail Price (Including Taxes)"),
                            fieldWithPath("taxPercentage").description("Percentage of tax (VAT) applied to this product"),
                            fieldWithPath("taxAmount").description("Amount of tax included in the price"),
                            fieldWithPath("available").description("Tells if the product has stock or not"),
                            fieldWithPath("priceWithoutTax").description("Price without taxes")
                )));

    }

    @Test
    void updateProduct() throws Exception {
        NewOrUpdatedProductDto productDto = new NewOrUpdatedProductDto();
        productDto.setName("My new product name");
        productDto.setDescription("Product just launched! you should buy it");
        productDto.setPrice(27);
        productDto.setTaxPercentage(21);

        Product updatedProduct = new Product().mapToEntity(productDto);
        updatedProduct.setId(12L);

        when(productRepository.save(any())).thenReturn(updatedProduct);
        when(productRepository.findById(eq(12L))).thenReturn(Optional.of(new Product()));

        //Call under test
        this.mockMvc.perform(put("/api/v1/products/12")
                        .content(objectMapper.writeValueAsBytes(productDto))
                        .headers(headers))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("products-put", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("name").description("Updated Product Name"),
                                fieldWithPath("description").description("Updated Product Description"),
                                fieldWithPath("price").description("Updated Retail Price (Including Taxes)"),
                                fieldWithPath("taxPercentage").description("Updated Percentage of tax (VAT) applied to this product")
                        ),
                        responseFields(
                                fieldWithPath("id").description("Product ID given to the new product"),
                                fieldWithPath("name").description("Product Name"),
                                fieldWithPath("description").description("Product Description"),
                                fieldWithPath("price").description("Retail Price (Including Taxes)"),
                                fieldWithPath("taxPercentage").description("Percentage of tax (VAT) applied to this product"),
                                fieldWithPath("taxAmount").description("Amount of tax included in the price"),
                                fieldWithPath("available").description("Tells if the product has stock or not"),
                                fieldWithPath("priceWithoutTax").description("Price without taxes")
                        )));

    }

    @Test
    void updateProductNotFound() throws Exception {
        NewOrUpdatedProductDto productDto = new NewOrUpdatedProductDto();
        productDto.setName("My new product name");
        productDto.setDescription("Product just launched! you should buy it");
        productDto.setPrice(27);
        productDto.setTaxPercentage(21);

        when(productRepository.findById(eq(13L))).thenThrow(new ProductNotFoundException(13L));

        //Call under test
        this.mockMvc.perform(put("/api/v1/products/13")
                        .content(objectMapper.writeValueAsBytes(productDto))
                        .headers(headers))
                .andExpect(status().isNotFound())
                .andDo(print())
                .andDo(document("products-put-not-found", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("name").description("Updated Product Name"),
                                fieldWithPath("description").description("Updated Product Description"),
                                fieldWithPath("price").description("Updated Retail Price (Including Taxes)"),
                                fieldWithPath("taxPercentage").description("Updated Percentage of tax (VAT) applied to this product")
                        ),
                        responseFields(
                                fieldWithPath("errorCode").description("HTTP Error code produced"),
                                fieldWithPath("errorMessage").description("Explanation of what went wrong")
                        )));

    }

    @Test
    void deleteAllProducts() throws Exception {
        //Call under test
        this.mockMvc.perform(delete("/api/v1/products"))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("products-delete", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));
    }

    @Test
    void deleteProduct() throws Exception {
        //Call under test
        this.mockMvc.perform(delete("/api/v1/products/12"))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("product-delete", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));
    }
}