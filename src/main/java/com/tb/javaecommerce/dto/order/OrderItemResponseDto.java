package com.tb.javaecommerce.dto.order;

import lombok.Value;

@Value
public class OrderItemResponseDto {
    String productTitle;
    double price;
    int quantity;
}
