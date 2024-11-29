package com.tb.javaecommerce.service.mappers;

import com.tb.javaecommerce.domain.Category;
import com.tb.javaecommerce.dto.category.CategoryDto;
import com.tb.javaecommerce.repository.entity.CategoryEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDto categoryToCategoryDto(Category category);
    List<CategoryDto> categoryListToCategoryDtoList(List<Category> categoryList);

    Category toCategory(CategoryEntity categoryEntity);
    List<Category> toCategoryList(Iterable<CategoryEntity> categoryEntityList);
    CategoryEntity toCategoryEntity(CategoryDto categoryDto);
    CategoryEntity toCategoryEntity(Category category);
}
