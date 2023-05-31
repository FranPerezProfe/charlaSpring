package dev.davidjuanes.products.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = { "id", "taxAmount", "available", "priceWithoutTax" })
public class NewOrUpdatedProductDto extends ProductDto {
}
