package com.moses.code.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Entity
@Table(name="category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(description = "Primary key of the category")
    private Long Id;

    @Schema(description = "Name of the category")
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "category")
    @Schema(description = "Products in the category")
    private List<Product> products;



}
