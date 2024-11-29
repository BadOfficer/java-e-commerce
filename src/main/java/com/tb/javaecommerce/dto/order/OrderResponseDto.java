package com.tb.javaecommerce.dto.order;

import lombok.Builder;
import lombok.Value;

import java.util.List;
import java.util.UUID;

@Value
@Builder
public class OrderResponseDto {
    UUID id;
    String consumerName;
    String address;
    String email;
    Double totalPrice;
    String orderStatus;
    List<OrderItemDto> orderItems;
}
