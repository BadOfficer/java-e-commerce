package com.tb.javaecommerce.dto.order;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class OrderItemRequestDto {
    @NotNull(message = "Product name cannot be null")
    String productId;

    @NotNull(message = "Product quantity cannot be null")
    Integer quantity;
}
