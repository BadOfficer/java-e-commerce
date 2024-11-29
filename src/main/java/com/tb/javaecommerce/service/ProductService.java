package com.tb.javaecommerce.service;

import com.tb.javaecommerce.domain.Product;
import com.tb.javaecommerce.dto.product.ProductRequestDto;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    List<Product> getAllProducts();
    Product getProductById(UUID productId);
    Product createProduct(ProductRequestDto productRequestDto);
    Product updateProduct(ProductRequestDto productRequestDto, UUID id);
    void deleteProduct(UUID id);
}
