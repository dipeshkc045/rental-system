package com.bookrental.api.category.controller;

import com.bookrental.api.category.model.request.CategoryRequestDto;
import com.bookrental.api.category.service.CategoryService;
import com.bookrental.response.ResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("category")
public class CategoryController {


    private final CategoryService categoryService;


    @PostMapping
    public ResponseEntity<ResponseDto> createCategory(
            @Valid @RequestBody CategoryRequestDto categoryRequestDto
    ) {
        var createdCategory = categoryService.saveCategory(categoryRequestDto);

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("category", createdCategory.getId());

        return ResponseEntity.ok(ResponseDto.success(responseData));
    }

    @GetMapping
    public ResponseEntity<ResponseDto> getAllCategories() {
        var categories = categoryService.getAllCategories();

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("categories", categories);

        return ResponseEntity.ok(ResponseDto.success(responseData));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> getCategoryById(@PathVariable Long id) {
        var category = categoryService.getCategoryById(id);

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("category", category);

        return ResponseEntity.ok(ResponseDto.success(responseData));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryRequestDto categoryRequestDto
    ) {
        var updatedCategory = categoryService.updateCategory(id, categoryRequestDto);

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("category", updatedCategory);

        return ResponseEntity.ok(ResponseDto.success(responseData));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok(ResponseDto.success());
    }
}
