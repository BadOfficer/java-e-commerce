package com.tb.javaecommerce.service.impl;

import com.tb.javaecommerce.domain.Category;
import com.tb.javaecommerce.dto.category.CategoryDto;
import com.tb.javaecommerce.repository.CategoryRepository;
import com.tb.javaecommerce.repository.entity.CategoryEntity;
import com.tb.javaecommerce.service.CategoryService;
import com.tb.javaecommerce.service.exception.CategoryNotFoundException;
import com.tb.javaecommerce.service.mappers.CategoryMapper;
import jakarta.persistence.PersistenceException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    @Transactional(readOnly = true)
    public List<Category> getAllCategories() {
        return categoryMapper.toCategoryList(categoryRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public Category getCategoryById(Long categoryId) {
        CategoryEntity category = categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException(categoryId));

        return categoryMapper.toCategory(category);
    }

    @Override
    @Transactional
    public Category createCategory(CategoryDto categoryDto) {
        try {
            return categoryMapper.toCategory(categoryRepository.save(categoryMapper.toCategoryEntity(categoryDto)));
        } catch (Exception e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    @Transactional
    public Category updateCategory(CategoryDto categoryDto, Long categoryId) {
        CategoryEntity category = categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException(categoryId));

        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());

        try {
            return categoryMapper.toCategory(categoryRepository.save(category));
        } catch (Exception e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    @Transactional
    public void deleteCategory(Long categoryId) {
        getCategoryById(categoryId);
        try {
            categoryRepository.deleteById(categoryId);
        } catch (Exception e) {
            throw new PersistenceException(e);
        }
    }
}
