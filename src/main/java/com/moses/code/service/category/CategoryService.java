package com.moses.code.service.category;

import com.moses.code.entity.Category;
import com.moses.code.exception.NotFoundException;
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
    public Category getCategoryById(Long categoryId) throws NotFoundException {
        return categoryRepository.findById(categoryId).orElseThrow(()->new NotFoundException("Category not found"));
    }

    @Override
    public void deleteCategory(Long categoryId) throws NotFoundException {
        Category category = getCategoryById(categoryId);
        categoryRepository.delete(category);

    }
}
