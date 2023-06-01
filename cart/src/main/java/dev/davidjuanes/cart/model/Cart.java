package dev.davidjuanes.cart.model;

import dev.davidjuanes.cart.api.dto.NewOrUpdatedCartDto;
import dev.davidjuanes.domain.AbstractEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Entity
public class Cart extends AbstractEntity<Cart, NewOrUpdatedCartDto> {
    @Id
    @GeneratedValue
    private Long id;
    private List<Long> items = new ArrayList<>();

    public Cart() {
        super(Cart.class, NewOrUpdatedCartDto.class);
    }

}
