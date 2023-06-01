package dev.davidjuanes.cart.api.dto;

import dev.davidjuanes.domain.AbstractDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class NewOrUpdatedCartDto extends AbstractDto {
    private List<Long> items = new ArrayList<>();
}
