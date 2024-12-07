package com.moses.code.mongodb;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "images")
@Data
public class Image {
    @Id
    private String id;            // Unique ID for the image
    private String productId;     // Optional: link to a product
    private String imageUrl;      // URL for the image
    private String contentType;   // e.g., "image/png"
    private long size;            // Size in bytes
}
