package com.tb.javaecommerce.service.impl;

import com.tb.javaecommerce.common.ProductStatus;
import com.tb.javaecommerce.domain.Category;
import com.tb.javaecommerce.domain.Product;
import com.tb.javaecommerce.dto.product.ProductRequestDto;
import com.tb.javaecommerce.repository.ProductRepository;
import com.tb.javaecommerce.repository.entity.CategoryEntity;
import com.tb.javaecommerce.repository.entity.ProductEntity;
import com.tb.javaecommerce.repository.projection.ProductDetailsProjection;
import com.tb.javaecommerce.service.CategoryService;
import com.tb.javaecommerce.service.ProductService;
import com.tb.javaecommerce.service.exception.ProductNotFoundException;
import com.tb.javaecommerce.service.mappers.CategoryMapper;
import com.tb.javaecommerce.service.mappers.ProductMapper;
import jakarta.persistence.PersistenceException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final ProductMapper productMapper;
    private final CategoryMapper categoryMapper;

    @Override
    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {
        return productMapper.toProductList(productRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public Product getProductById(UUID productId) {
        ProductEntity product = productRepository.findByNaturalId(productId).orElseThrow(() -> new ProductNotFoundException(productId.toString()));
        return productMapper.toProduct(product);
    }

    @Override
    @Transactional
    public Product createProduct(ProductRequestDto productRequestDto) {
        CategoryEntity category = categoryMapper.toCategoryEntity(categoryService.getCategoryById(productRequestDto.getCategoryId()));

        Product newProduct = Product.builder()
                .title(productRequestDto.getTitle())
                .description(productRequestDto.getDescription())
                .price(productRequestDto.getPrice())
                .category(categoryMapper.toCategory(category))
                .status(productRequestDto.getStatus())
                .build();
        try {
            return productMapper.toProduct(productRepository.save(productMapper.toProductEntity(newProduct)));
        } catch (Exception e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    @Transactional
    public Product updateProduct(ProductRequestDto productRequestDto, UUID id) {
        ProductEntity product = productRepository.findByNaturalId(id).orElseThrow(() -> new ProductNotFoundException(id.toString()));
        CategoryEntity category = categoryMapper.toCategoryEntity(categoryService.getCategoryById(productRequestDto.getCategoryId()));

        product.setTitle(productRequestDto.getTitle());
        product.setDescription(productRequestDto.getDescription());
        product.setPrice(productRequestDto.getPrice());
        product.setStatus(productRequestDto.getStatus());
        product.setCategory(category);

        try {
            return productMapper.toProduct(productRepository.save(product));
        } catch (Exception e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDetailsProjection> getProductsByPriceRange(double minPrice, double maxPrice) {
        return productRepository.findProductByPriceRange(minPrice, maxPrice);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> findByTitleContainingIgnoreCase(String title) {
        return productMapper.toProductList(productRepository.findByTitleContainingIgnoreCase(title));
    }

    @Override
    @Transactional
    public void deleteProduct(UUID id) {
        getProductById(id);

        try {
            productRepository.deleteByNaturalId(id);
        } catch (Exception e) {
            throw new PersistenceException(e);
        }
    }
}
