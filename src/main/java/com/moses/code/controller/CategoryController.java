package com.moses.code.controller;

import com.moses.code.entity.Category;
import com.moses.code.exception.CategoryNotFoundException;
import com.moses.code.service.category.ICategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@Tag(name = "Categories", description = "Operations related to categories")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    @PostMapping
    @Operation(summary = "Add a new category", description = "Creates a new category in the system.")
    public ResponseEntity<Category> addCategory(@RequestBody Category categoryRequest) {
        Category category = categoryService.addCategory(categoryRequest);
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all categories", description = "Retrieves a list of all categories.")
    public ResponseEntity<List<Category>> getAllCategories() {
        return new ResponseEntity<>(categoryService.getCategories(), HttpStatus.OK);
    }

    @GetMapping("/{categoryId}")
    @Operation(summary = "Get category by ID", description = "Retrieves a category by its ID.")
    public ResponseEntity<Category> getCategoryByID(@PathVariable Long categoryId) throws CategoryNotFoundException {
        return new ResponseEntity<>(categoryService.getCategoryById(categoryId), HttpStatus.OK);
    }

    @DeleteMapping("/{categoryId}")
    @Operation(summary = "Delete a category", description = "Deletes a category by its ID.")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId) throws CategoryNotFoundException {
        categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>("Category deleted successfully", HttpStatus.NO_CONTENT);
    }
}
