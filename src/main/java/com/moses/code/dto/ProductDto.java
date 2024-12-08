package com.moses.code.dto;

import com.moses.code.entity.Category;
import com.moses.code.mongodb.Image;
import lombok.Data;


import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductDto {

    private String name;

    private BigDecimal price;
    private int quantity;
    private String description;



    private Category category;

    private List<String> imageUrls;


}
