package com.moses.code.service.category;

import com.moses.code.entity.Category;
import com.moses.code.exception.CategoryNotFoundException;

import java.util.List;

public interface ICategoryService {
    Category addCategory(Category category);
    List<Category> getCategories();
    Category getCategoryById(Long categoryId) throws CategoryNotFoundException;
    void deleteCategory(Long categoryId) throws CategoryNotFoundException;
}
