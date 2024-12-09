package com.moses.code.dto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class OrderItemDto {
    private Long id;
    private int quantity;
    private BigDecimal price;
    private ProductDto product;
}
