package com.bookrental.api.category.service.impl;

import com.bookrental.api.category.model.entity.Category;
import com.bookrental.api.category.model.request.CategoryRequestDto;
import com.bookrental.api.category.repository.CategoryRepository;
import com.bookrental.api.category.service.CategoryService;
import com.bookrental.resourceconverter.GenericMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final GenericMapper genericMapper;


    @Override
    @Transactional
    public Category saveCategory(CategoryRequestDto categoryRequestDto) {
        Category category = genericMapper.convert(categoryRequestDto, Category.class);
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }


    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Category updateCategory(Long id, CategoryRequestDto categoryRequestDto) {
        Category book = categoryRepository.findById(id).orElse(null);
        assert book != null;
        Category updatedBook = Category.builder()
                .id(book.getId())
                .name(categoryRequestDto.getName() != null ? categoryRequestDto.getName() : book.getName())
                .build();
        return categoryRepository.save(updatedBook);
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {
        Category existedCategory= categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Found"));
        categoryRepository.deleteById(existedCategory.getId());

    }
}
