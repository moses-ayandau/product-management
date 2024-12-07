package com.moses.code.mongodb;

import com.moses.code.entity.Product;
import com.moses.code.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/images")
public class ImageController {
    @Autowired
    private ImageService imageService;

    @Autowired
    private ProductService productService;

    /**
     * Upload multiple images and link them to a product.
     */
    @PostMapping("/upload")
    public ResponseEntity<List<Image>> uploadImages(
            @RequestParam("files") List<MultipartFile> files,
            @RequestParam Long productId) {
        try {
            if (files.isEmpty()) {
                return ResponseEntity.badRequest().body(null);
            }

            // Upload all images
            List<Image> uploadedImages = imageService.uploadImages(files);

            // Link images to the product
            Product product = productService.getProductById(productId);
            for (Image image : uploadedImages) {
                product.getImageUrls().add(image.getImageUrl());
            }
//            productService.saveProduct(product);

            return ResponseEntity.ok(uploadedImages);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }
}
