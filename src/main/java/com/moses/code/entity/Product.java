package com.moses.code.entity;

import com.moses.code.dto.ListToJsonConverter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "product")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(description = "Product entity")
    private Long id;

    @Schema(description = "Name of the product")
    private String name;

    @Schema(description = "Price of the product")
    private BigDecimal price;

    @Schema(description = "Quantity of the product")
    private int quantity;

    @Schema(description = "Description of the product")
    private String description;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id", nullable = true)
    private Category category;

    @Schema(description = "Image URLs stored as JSON")
    @Column(name = "image_urls", columnDefinition = "TEXT")
    @Convert(converter = ListToJsonConverter.class)
    @Basic(fetch = FetchType.LAZY)
    private List<String> imageUrls;

    public Product(String name, String brand, BigDecimal price, int quantity, String description, Category category) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.category = category;
    }
}
