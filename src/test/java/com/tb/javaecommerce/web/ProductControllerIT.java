package com.tb.javaecommerce.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tb.javaecommerce.common.ProductStatus;
import com.tb.javaecommerce.domain.Product;
import com.tb.javaecommerce.dto.product.ProductRequestDto;
import com.tb.javaecommerce.repository.CategoryRepository;
import com.tb.javaecommerce.repository.ProductRepository;
import com.tb.javaecommerce.repository.entity.CategoryEntity;
import com.tb.javaecommerce.repository.entity.ProductEntity;
import com.tb.javaecommerce.service.CategoryService;
import com.tb.javaecommerce.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

@AutoConfigureMockMvc
@DisplayName("Product Controller IT")
@SpringBootTest
public class ProductControllerIT {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @SpyBean
    private ProductService productService;

    @BeforeEach
    void setUp() {
        Mockito.reset(productService);
        productRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    @Test
    void shouldGetAllProduct() throws Exception {
        createProduct();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void shouldGetProductById() throws Exception {
        ProductEntity product = createProduct();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products/" + product.getProduct_reference()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void shouldGetProductByIdNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products/" + UUID.randomUUID()))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void shouldCreateProduct() throws Exception {
        CategoryEntity categoryEntity = createCategory();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/products")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(productRequestDto(categoryEntity.getId()))))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void shouldCreateProductFailed() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/products")
                .contentType("application/json")
                .content("{}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void shouldUpdateProduct() throws Exception {
        ProductEntity product = createProduct();

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/products/" + product.getProduct_reference())
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(productRequestDto(product.getCategory().getId()))))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void shouldUpdateProductFailed() throws Exception {
        ProductEntity product = createProduct();
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/products/" + product.getProduct_reference())
        .contentType("application/json")
                .content("{}")).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void shouldUpdateProductNotFound() throws Exception {
        CategoryEntity category = createCategory();
        ProductRequestDto productRequestDto = productRequestDto(category.getId());

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/products/" + UUID.randomUUID())
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(productRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void shouldGetProductByPriceRange() throws Exception {
        createProduct();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products/filter")
                .param("minPrice", String.valueOf(10.0))
                .param("maxPrice", String.valueOf(20.0))
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void shouldGetProductByPriceRangeFailed() throws Exception {
        createProduct();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products/filter")
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void shouldGetSearchedProducts() throws Exception {
        createProduct();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products/search")
                .param("title", "Test")).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void shouldGetSearchedProductsFailed() throws Exception {
        createProduct();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products/search")).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void shouldDeleteProduct() throws Exception {
        ProductEntity product = productRepository.save(ProductEntity.builder()
                .title("Test Product for deleting galaxy")
                .price(15.0)
                .product_reference(UUID.randomUUID())
                .description("Test product description")
                .status(ProductStatus.IN_STOCK)
                .category(createCategory())
                .build());

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/products/" + product.getProduct_reference()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void shouldDeleteProductNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/products/" + UUID.fromString("37e0d1a8-a692-450e-81c6-29e8f56e8a6e")))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    private CategoryEntity createCategory() {
        return  categoryRepository.save(CategoryEntity.builder()
                .title("Test Category for product")
                .description("Test Category for product")
                .build());
    }

    private ProductEntity createProduct() {
        return productRepository.save(ProductEntity.builder()
                .title("Test Product galaxy")
                .price(15.0)
                .product_reference(UUID.fromString("37e0d1a8-a692-450e-81c6-29e8f56e8a6a"))
                .description("Test product description")
                .status(ProductStatus.IN_STOCK)
                .category(createCategory())
                .build());
    }

    private ProductRequestDto productRequestDto(long categoryId) {
        return ProductRequestDto.builder()
                .title("Product from requestDto star")
                .description("Product Description")
                .categoryId(categoryId)
                .price(14.0)
                .status(ProductStatus.IN_STOCK)
                .build();
    }
}
