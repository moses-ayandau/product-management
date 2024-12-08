package com.moses.code.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
public class ProductDto {
    private Long id;
    private String name;
    private BigDecimal price;
    private int quantity;
    private String description;
    private String categoryName;
    private List<String> imageUrls;
}
