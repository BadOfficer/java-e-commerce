package com.tb.javaecommerce.domain;

import com.tb.javaecommerce.common.OrderStatus;
import com.tb.javaecommerce.dto.order.OrderItemDto;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.util.List;
import java.util.UUID;

@Value
@Data
@Builder
public class Order {
    long id;
    String consumerName;
    String address;
    String email;
    OrderStatus orderStatus;
    List<OrderItem> orderItems;
    double totalPrice;
}
