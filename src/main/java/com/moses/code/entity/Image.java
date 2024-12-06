package com.moses.code.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Blob;
@Entity
@Table(name = "images")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String fileName;
    private String fileType;

    @Lob
    private Blob image;
    private String downloadUrl;

    @ManyToOne(cascade = CascadeType.ALL)
    private Product product;



}
