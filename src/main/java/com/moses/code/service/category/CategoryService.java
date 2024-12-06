package com.moses.code.service.category;

import com.moses.code.entity.Category;
import com.moses.code.exception.CategoryNotFoundException;
import com.moses.code.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService implements ICategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category addCategory(Category category) {

        return categoryRepository.save(category);
    }

    @Override
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(Long categoryId) throws CategoryNotFoundException {
        return categoryRepository.findById(categoryId).orElseThrow(()->new CategoryNotFoundException("Category not found"));
    }

    @Override
    public void deleteCategory(Long categoryId) throws CategoryNotFoundException {
        categoryRepository.deleteById(categoryId);

    }
}
