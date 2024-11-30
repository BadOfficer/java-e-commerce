package com.tb.javaecommerce.service;

import com.tb.javaecommerce.domain.Product;
import com.tb.javaecommerce.dto.product.ProductRequestDto;
import com.tb.javaecommerce.repository.projection.ProductDetailsProjection;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    List<Product> getAllProducts();
    Product getProductById(UUID productId);
    Product createProduct(ProductRequestDto productRequestDto);
    Product updateProduct(ProductRequestDto productRequestDto, UUID id);
    List<ProductDetailsProjection> getProductsByPriceRange(double minPrice, double maxPrice);
    List<Product> findByTitleContainingIgnoreCase(String title);
    void deleteProduct(UUID id);
}
