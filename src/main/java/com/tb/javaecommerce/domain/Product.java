package com.tb.javaecommerce.domain;

import com.tb.javaecommerce.common.ProductStatus;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class Product {
    private String id;
    private String title;
    private String description;
    private double price;
    private ProductStatus status;
    private Category category;
}
