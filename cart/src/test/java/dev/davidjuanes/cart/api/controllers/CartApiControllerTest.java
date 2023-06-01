package dev.davidjuanes.cart.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.davidjuanes.cart.api.dto.NewOrUpdatedCartDto;
import dev.davidjuanes.cart.model.CartItem;
import dev.davidjuanes.cart.model.DetailedCart;
import dev.davidjuanes.cart.service.cart.CartNotFoundException;
import dev.davidjuanes.cart.service.cart.CartService;
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
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CartApiController.class)
@AutoConfigureRestDocs(outputDir = "build/generated-snippets", uriPort = 8002)
class CartApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartService cartService;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private HttpHeaders headers;

    @BeforeEach
    void setup() {
        headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
    }

    @Test
    void getAllCarts() throws Exception {
        //Prepare mocked data
        DetailedCart cart1 = new DetailedCart(1L);

        CartItem item1 = new CartItem();
        item1.setId(1L);
        item1.setName("Toothbrush for dogs");
        item1.setPriceWithoutTaxes(10);
        item1.setPriceWithTaxes(12.10);

        CartItem item2 = new CartItem();
        item2.setId(1L);
        item2.setName("A night sunglasses");
        item2.setPriceWithoutTaxes(10);
        item2.setPriceWithTaxes(12.10);
        cart1.getItems().add(item1);
        cart1.getItems().add(item2);

        DetailedCart cart2 = new DetailedCart(2L);
        cart2.getItems().add(item2);

        when(cartService.getAllCarts()).thenReturn(Lists.list(cart1, cart2));

        //Call under test
        this.mockMvc.perform(get("/api/v1/carts"))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("carts-get", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()), responseFields(
                        fieldWithPath("[].id").description("Cart ID"),
                        fieldWithPath("[].items").description("Product items in this cart"),
                        fieldWithPath("[].items[].id").description("Product ID"),
                        fieldWithPath("[].items[].name").description("Product Name"),
                        fieldWithPath("[].items[].priceWithTaxes").description("Retail Price (Including Taxes)"),
                        fieldWithPath("[].items[].priceWithoutTaxes").description("Price of the product before adding the taxes"),
                        fieldWithPath("[].totalPrice").description("Total Retail price of all items in the cart"),
                        fieldWithPath("[].totalPriceWithoutTaxes").description("Total price of all items in the cart without taxes")
                )));
    }

    @Test
    void getCart() throws Exception {
        //Prepare mocked data
        DetailedCart cart1 = new DetailedCart(1L);

        CartItem item1 = new CartItem();
        item1.setId(1L);
        item1.setName("Toothbrush for dogs");
        item1.setPriceWithoutTaxes(10);
        item1.setPriceWithTaxes(12.10);

        CartItem item2 = new CartItem();
        item2.setId(1L);
        item2.setName("A night sunglasses");
        item2.setPriceWithoutTaxes(10);
        item2.setPriceWithTaxes(12.10);
        cart1.getItems().add(item1);
        cart1.getItems().add(item2);

        when(cartService.getCartById(eq(1L))).thenReturn(Optional.of(cart1));

        //Call under test
        this.mockMvc.perform(get("/api/v1/carts/1"))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("cart-get", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()), responseFields(
                        fieldWithPath("id").description("Cart ID"),
                        fieldWithPath("items").description("Product items in this cart"),
                        fieldWithPath("items[].id").description("Product ID"),
                        fieldWithPath("items[].name").description("Product Name"),
                        fieldWithPath("items[].priceWithTaxes").description("Retail Price (Including Taxes)"),
                        fieldWithPath("items[].priceWithoutTaxes").description("Price of the product before adding the taxes"),
                        fieldWithPath("totalPrice").description("Total Retail price of all items in the cart"),
                        fieldWithPath("totalPriceWithoutTaxes").description("Total price of all items in the cart without taxes")
                )));
    }

    @Test
    void getCartNotFound() throws Exception {

        //Call under test
        this.mockMvc.perform(get("/api/v1/carts/2"))
                .andExpect(status().isNotFound())
                .andDo(print())
                .andDo(document("cart-get-not-found", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()), responseFields(
                        fieldWithPath("errorCode").description("HTTP Error code produced"),
                        fieldWithPath("errorMessage").description("Explanation of what went wrong")
                )));
    }

    @Test
    void addCart() throws Exception {

        NewOrUpdatedCartDto cartDto = new NewOrUpdatedCartDto();
        cartDto.getItems().add(1L);

        DetailedCart createdCart = new DetailedCart(12L);
        CartItem item1 = new CartItem();
        item1.setId(1L);
        item1.setName("Toothbrush for dogs");
        item1.setPriceWithoutTaxes(10);
        item1.setPriceWithTaxes(12.10);
        createdCart.getItems().add(item1);

        when(cartService.addCart(any())).thenReturn(createdCart);

        //Call under test
        this.mockMvc.perform(post("/api/v1/carts")
                        .content(objectMapper.writeValueAsBytes(cartDto))
                        .headers(headers))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("carts-post", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("id").ignored(),
                                fieldWithPath("items").description("List of product IDs that are in the cart")

                        ),
                        responseFields(
                                fieldWithPath("id").description("Cart ID"),
                                fieldWithPath("items").description("Product items in this cart"),
                                fieldWithPath("items[].id").description("Product ID"),
                                fieldWithPath("items[].name").description("Product Name"),
                                fieldWithPath("items[].priceWithTaxes").description("Retail Price (Including Taxes)"),
                                fieldWithPath("items[].priceWithoutTaxes").description("Price of the product before adding the taxes"),
                                fieldWithPath("totalPrice").description("Total Retail price of all items in the cart"),
                                fieldWithPath("totalPriceWithoutTaxes").description("Total price of all items in the cart without taxes")
                        )));

    }

    @Test
    void updateCart() throws Exception {
        NewOrUpdatedCartDto cartDto = new NewOrUpdatedCartDto();
        cartDto.getItems().add(1L);

        DetailedCart updatedCart = new DetailedCart(12L);
        CartItem item1 = new CartItem();
        item1.setId(1L);
        item1.setName("Toothbrush for dogs");
        item1.setPriceWithoutTaxes(10);
        item1.setPriceWithTaxes(12.10);
        updatedCart.getItems().add(item1);

        when(cartService.updateCart(any())).thenReturn(updatedCart);
        when(cartService.getCartById(eq(12L))).thenReturn(Optional.of(new DetailedCart(12L)));

        //Call under test
        this.mockMvc.perform(put("/api/v1/carts/12")
                        .content(objectMapper.writeValueAsBytes(cartDto))
                        .headers(headers))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("carts-put", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("id").ignored(),
                                fieldWithPath("items").description("List of product IDs that are in the cart")
                        ),
                        responseFields(
                                fieldWithPath("id").description("Cart ID"),
                                fieldWithPath("items").description("Product items in this cart"),
                                fieldWithPath("items[].id").description("Product ID"),
                                fieldWithPath("items[].name").description("Product Name"),
                                fieldWithPath("items[].priceWithTaxes").description("Retail Price (Including Taxes)"),
                                fieldWithPath("items[].priceWithoutTaxes").description("Price of the product before adding the taxes"),
                                fieldWithPath("totalPrice").description("Total Retail price of all items in the cart"),
                                fieldWithPath("totalPriceWithoutTaxes").description("Total price of all items in the cart without taxes")
                        )));

    }

    @Test
    void updateCartNotFound() throws Exception {

        NewOrUpdatedCartDto cartDto = new NewOrUpdatedCartDto();
        cartDto.getItems().add(1L);

        DetailedCart updatedCart = new DetailedCart(12L);
        CartItem item1 = new CartItem();
        item1.setId(1L);
        item1.setName("Toothbrush for dogs");
        item1.setPriceWithoutTaxes(10);
        item1.setPriceWithTaxes(12.10);
        updatedCart.getItems().add(item1);

        when(cartService.updateCart(any())).thenThrow(new CartNotFoundException(13L));

        //Call under test
        this.mockMvc.perform(put("/api/v1/carts/13")
                        .content(objectMapper.writeValueAsBytes(cartDto))
                        .headers(headers))
                .andExpect(status().isNotFound())
                .andDo(print())
                .andDo(document("carts-put-not-found", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("id").ignored(),
                                fieldWithPath("items").description("List of product IDs that are in the cart")
                        ),
                        responseFields(
                                fieldWithPath("errorCode").description("HTTP Error code produced"),
                                fieldWithPath("errorMessage").description("Explanation of what went wrong")
                        )));

    }

    @Test
    void deleteAllCarts() throws Exception {
        //Call under test
        this.mockMvc.perform(delete("/api/v1/carts"))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("carts-delete", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));
    }

    @Test
    void deleteCarts() throws Exception {
        //Call under test
        this.mockMvc.perform(delete("/api/v1/carts/12"))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("cart-delete", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));
    }

}