package com.tb.javaecommerce.dto.order;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class OrderItemDto {
    @NotNull(message = "Product name cannot be null")
    Long productId;

    @NotNull(message = "Product quantity cannot be null")
    Integer quantity;

    @NotNull(message = "Product price cannot be null")
    Double price;
}
