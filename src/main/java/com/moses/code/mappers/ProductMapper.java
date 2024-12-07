package com.moses.code.mappers;

import com.moses.code.dto.ProductDto;
import com.moses.code.entity.Product;

public class ProductMapper {
    public static Product convertFromProductDtoToProduct(ProductDto productDto){
        Product product = new Product();
        product.setName(productDto.getName());
        product.setCategory(productDto.getCategory());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
//        product.setImages(productDto.getImages());
        product.setQuantity(productDto.getQuantity());
        return product;
    }

    public static ProductDto convertFromProductToProductDto(Product product){
        ProductDto productDto = new ProductDto();
        productDto.setCategory(product.getCategory());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setQuantity(product.getQuantity());
//        productDto.setImages(product.getImages());
        return productDto;
    }

}
