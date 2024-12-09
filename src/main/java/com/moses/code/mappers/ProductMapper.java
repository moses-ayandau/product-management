package com.moses.code.mappers;

import com.moses.code.dto.ProductDto;
import com.moses.code.entity.Product;

public class ProductMapper {

    public static ProductDto convertFromProductToProductDto(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .description(product.getDescription())
                .categoryName(product.getCategory() != null ? product.getCategory().getName() : null)
                .imageUrls(product.getImageUrls())
                .build();
    }


}
