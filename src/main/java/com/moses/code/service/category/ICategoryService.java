package com.moses.code.service.category;

import com.moses.code.entity.Category;
import com.moses.code.exception.NotFoundException;

import java.util.List;

public interface ICategoryService {
    Category addCategory(Category category);
    List<Category> getCategories();
    Category getCategoryById(Long categoryId) throws NotFoundException;
    void deleteCategory(Long categoryId) throws NotFoundException;
}
