package com.bookrental.api.category.controller;

import com.bookrental.api.category.model.request.CategoryRequestDto;
import com.bookrental.api.category.service.CategoryService;
import com.bookrental.endpoints.EndPointConstants;
import com.bookrental.response.ResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(EndPointConstants.CATEGORY)
public class CategoryController {


    private final CategoryService categoryService;


    @PostMapping(EndPointConstants.SAVE)
    public ResponseEntity<ResponseDto> createCategory(
            @Valid @RequestBody CategoryRequestDto categoryRequestDto
    ) {
        var createdCategory = categoryService.saveCategory(categoryRequestDto);

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("category", createdCategory.getId());

        return ResponseEntity.ok(ResponseDto.success(responseData));
    }

    @GetMapping(EndPointConstants.GET_ALL)
    public ResponseEntity<ResponseDto> getAllCategories() {
        var categories = categoryService.getAllCategories();

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("categories", categories);

        return ResponseEntity.ok(ResponseDto.success(responseData));
    }

    @GetMapping(EndPointConstants.GET_BY_ID)
    public ResponseEntity<ResponseDto> getCategoryById(@PathVariable Long id) {
        var category = categoryService.getCategoryById(id);

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("category", category);

        return ResponseEntity.ok(ResponseDto.success(responseData));
    }

    @PutMapping(EndPointConstants.UPDATE)
    public ResponseEntity<ResponseDto> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryRequestDto categoryRequestDto
    ) {
        var updatedCategory = categoryService.updateCategory(id, categoryRequestDto);

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("category", updatedCategory);

        return ResponseEntity.ok(ResponseDto.success(responseData));
    }

    @DeleteMapping(EndPointConstants.DELETE)
    public ResponseEntity<ResponseDto> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("CategoryId", id);
        responseData.put("message", "Category deleted successfully");
        return ResponseEntity.ok(ResponseDto.success(responseData));
    }
}
