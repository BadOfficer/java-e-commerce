package com.tb.javaecommerce.web;

import com.tb.javaecommerce.domain.Product;
import com.tb.javaecommerce.dto.product.ProductRequestDto;
import com.tb.javaecommerce.dto.product.ProductResponseDto;
import com.tb.javaecommerce.service.CategoryService;
import com.tb.javaecommerce.service.ProductService;
import com.tb.javaecommerce.service.mappers.ProductMapper;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
@Validated
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;

    public ProductController(ProductService productService, ProductMapper productMapper, CategoryService categoryService) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable UUID id) {
        return ResponseEntity.ok(productMapper.toProductResponseDto(productService.getProductById(id)));
    }

    @GetMapping
    public List<ProductResponseDto> getAllProducts() {
        return productMapper.toProductResponseDtoList(productService.getAllProducts());
    }

    @PostMapping
    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody @Valid ProductRequestDto productRequestDto) {
        return ResponseEntity.ok(productMapper.toProductResponseDto(productService.createProduct(productRequestDto)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(@RequestBody @Valid ProductRequestDto productRequestDto, @PathVariable UUID id) {
        return ResponseEntity.ok(productMapper.toProductResponseDto(productService.updateProduct(productRequestDto, id)));
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable UUID id) {
        productService.deleteProduct(id);
    }
}
