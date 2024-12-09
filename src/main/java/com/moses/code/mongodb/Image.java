package com.moses.code.mongodb;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "images")
@Data
public class Image {
    @Id
    private String id;
    private String productId;
    private String imageUrl;
    private String contentType;
    private long size;
}
