package com.bookrental.api.category.service;

import com.bookrental.api.category.model.entity.Category;
import com.bookrental.api.category.model.request.CategoryRequestDto;

import java.util.List;

public interface CategoryService {

    Category saveCategory(CategoryRequestDto categoryRequestDto);


    List<Category> getAllCategories();


    Category getCategoryById(Long id);

    Category updateCategory(Long id, CategoryRequestDto categoryRequestDto);


    void deleteCategory(Long id);
}
