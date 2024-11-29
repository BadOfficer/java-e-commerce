package com.tb.javaecommerce.web;

import com.tb.javaecommerce.domain.Category;
import com.tb.javaecommerce.dto.category.CategoryDto;
import com.tb.javaecommerce.service.CategoryService;
import com.tb.javaecommerce.service.mappers.CategoryMapper;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@Validated
public class CategoryController {
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    public CategoryController(CategoryService categoryService, CategoryMapper categoryMapper) {
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryMapper.categoryToCategoryDto(categoryService.getCategoryById(id)));
    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        return ResponseEntity.ok(categoryMapper.categoryListToCategoryDtoList(categoryService.getAllCategories()));
    }

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@RequestBody @Valid CategoryDto categoryDto) {
        return ResponseEntity.ok(categoryMapper.categoryToCategoryDto(categoryService.createCategory(categoryDto)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody @Valid CategoryDto categoryDto, @PathVariable Long id) {
        return ResponseEntity.ok(categoryMapper.categoryToCategoryDto(categoryService.updateCategory(categoryDto, id)));
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }
}
