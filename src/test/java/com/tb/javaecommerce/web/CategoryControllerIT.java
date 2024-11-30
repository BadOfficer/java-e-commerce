package com.tb.javaecommerce.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tb.javaecommerce.AbstractIt;
import com.tb.javaecommerce.dto.category.CategoryDto;
import com.tb.javaecommerce.repository.CategoryRepository;
import com.tb.javaecommerce.repository.entity.CategoryEntity;
import com.tb.javaecommerce.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

@AutoConfigureMockMvc
@DisplayName("Category Controller IT")
@SpringBootTest
public class CategoryControllerIT extends AbstractIt {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoryRepository categoryRepository;

    @SpyBean
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        Mockito.reset(categoryService);
        categoryRepository.deleteAll();
    }

    @Test
    void shouldGetAllCategories() throws Exception {
        saveCategoryEntity();
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/categories")).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void shouldGetCategoryById() throws Exception {
        CategoryEntity category = saveCategoryEntity();
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/categories/" + category.getId())).andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    void shouldGetNotFoundCategory() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/categories/" + 1L)).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void shouldCreateCategory() throws Exception {
        CategoryDto categoryDto = createCategoryDto();

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(categoryDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void shouldCreateCategoryFailed() throws Exception {
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"));

        response.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void shouldUpdateCategory() throws Exception {
        CategoryEntity categoryEntity = saveCategoryEntity();
        CategoryDto categoryDto = createCategoryDto();
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/categories/" + categoryEntity.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(categoryDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void shouldUpdateCategoryFailed() throws Exception {
        CategoryEntity categoryEntity = saveCategoryEntity();
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/categories/" + categoryEntity.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"));

        response.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void shouldNotFoundUpdateCategory() throws Exception {
        CategoryDto categoryDto = createCategoryDto();
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/categories/" + 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryDto)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void shouldDeleteCategory() throws Exception {
        CategoryEntity categoryEntity = saveCategoryEntity();
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/categories/" + categoryEntity.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void shouldDeleteCategoryNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/categories/{id}", 1L))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    private CategoryDto createCategoryDto() {
        return CategoryDto.builder().title("Test Category updated").description("Description for test category updated").build();
    }

    private CategoryEntity saveCategoryEntity() {
        return categoryRepository.save(CategoryEntity.builder()
                .title("Test category")
                .description("Description for test category")
                .build());
    }
}
