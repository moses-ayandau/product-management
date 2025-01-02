package com.moses.code.repository;

import com.moses.code.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT c FROM Category c WHERE c.name = :categoryName")
    Optional<Category> findCategoryByName(String categoryName);
}
