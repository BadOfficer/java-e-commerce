package com.tb.javaecommerce.service;

import com.tb.javaecommerce.domain.Category;
import com.tb.javaecommerce.dto.category.CategoryDto;
import com.tb.javaecommerce.repository.CategoryRepository;
import com.tb.javaecommerce.repository.entity.CategoryEntity;
import com.tb.javaecommerce.service.exception.CategoryNotFoundException;
import com.tb.javaecommerce.service.impl.CategoryServiceImpl;
import com.tb.javaecommerce.service.mappers.CategoryMapper;
import jakarta.persistence.PersistenceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Category Service Test")
@SpringBootTest(classes = CategoryServiceImpl.class)
@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {
    @MockBean
    private CategoryRepository categoryRepository;

    @MockBean
    private CategoryMapper categoryMapper;

    @Autowired
    private CategoryService categoryService;

    private CategoryDto categoryDto;
    private CategoryEntity categoryEntity;
    private Category category;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        categoryDto = CategoryDto.builder().title("Category 1").description("Description for category 1").build();
        categoryEntity = CategoryEntity.builder().title("Category 1").description("Description form category 1").build();
        category = Category.builder().id(1).title("Category 1").description("Description for category 1").build();
    }

    @Test
    void shouldGetAllCategories() {
        List<CategoryEntity> categoryEntities = new ArrayList<>(List.of(categoryEntity));
        List<Category> categories = new ArrayList<>(List.of(category));

        Mockito.when(categoryRepository.findAll()).thenReturn(categoryEntities);
        Mockito.when(categoryMapper.toCategoryList(categoryEntities)).thenReturn(categories);

        List<Category> result = categoryService.getAllCategories();

        assertNotNull(result);
    }

    @Test
    void shouldGetCategoryById() {
        Mockito.when(categoryRepository.findById(1L)).thenReturn(Optional.of(categoryEntity));
        Mockito.when(categoryMapper.toCategory(categoryEntity)).thenReturn(category);

        Category result = categoryService.getCategoryById(1L);

        assertNotNull(result);
    }

    @Test
    void shouldGetCategoryByIdNotFound() {
        Mockito.when(categoryRepository.findById(1L)).thenThrow(CategoryNotFoundException.class);

        assertThrows(CategoryNotFoundException.class, () -> categoryService.getCategoryById(1L));
    }

    @Test
    void shouldCreateCategory() {
        Mockito.when(categoryMapper.toCategoryEntity(categoryDto)).thenReturn(categoryEntity);
        Mockito.when(categoryRepository.save(categoryEntity)).thenReturn(categoryEntity);
        Mockito.when(categoryMapper.toCategory(categoryEntity)).thenReturn(category);

        Category result = categoryService.createCategory(categoryDto);

        assertNotNull(result);
    }

    @Test
    void shouldUpdateCategory() {
        Mockito.when(categoryRepository.findById(1L)).thenReturn(Optional.of(categoryEntity));
        Mockito.when(categoryMapper.toCategoryEntity(categoryDto)).thenReturn(categoryEntity);
        Mockito.when(categoryRepository.save(categoryEntity)).thenReturn(categoryEntity);
        Mockito.when(categoryMapper.toCategory(categoryEntity)).thenReturn(category);

        Category result = categoryService.updateCategory(categoryDto, 1L);

        assertNotNull(result);
    }

    @Test
    void shouldUpdateCategoryNotFound() {
        Mockito.when(categoryRepository.findById(1L)).thenThrow(CategoryNotFoundException.class);

        assertThrows(CategoryNotFoundException.class, () -> categoryService.updateCategory(categoryDto, 1L));
    }

    @Test
    void shouldDeleteCategory() {
        Mockito.when(categoryRepository.findById(1L)).thenReturn(Optional.of(categoryEntity));
        categoryService.deleteCategory(1L);
        Mockito.verify(categoryRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    void shouldDeleteCategoryNotFound() {
        Mockito.when(categoryRepository.findById(1L)).thenThrow(CategoryNotFoundException.class);
        assertThrows(CategoryNotFoundException.class, () -> categoryService.deleteCategory(1L));
    }

    @Test
    void shouldThrowPersistenceExceptionOnCreateCategory() {
        Mockito.when(categoryMapper.toCategoryEntity(categoryDto)).thenReturn(categoryEntity);
        Mockito.when(categoryRepository.save(categoryEntity)).thenThrow(new PersistenceException("Database error"));

        assertThrows(PersistenceException.class, () -> categoryService.createCategory(categoryDto));
    }

    @Test
    void shouldThrowPersistenceExceptionOnUpdateCategory() {
        Mockito.when(categoryRepository.findById(1L)).thenReturn(Optional.of(categoryEntity));
        Mockito.when(categoryRepository.save(categoryEntity)).thenThrow(new PersistenceException("Database error"));

        assertThrows(PersistenceException.class, () -> categoryService.updateCategory(categoryDto, 1L));
    }

    @Test
    void shouldThrowPersistenceExceptionOnDeleteCategory() {
        Mockito.when(categoryRepository.findById(1L)).thenReturn(Optional.of(categoryEntity));
        Mockito.doThrow(new PersistenceException("Database error")).when(categoryRepository).deleteById(1L);

        assertThrows(PersistenceException.class, () -> categoryService.deleteCategory(1L));
    }

}
