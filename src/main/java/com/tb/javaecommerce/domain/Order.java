package com.tb.javaecommerce.domain;

import com.tb.javaecommerce.common.OrderStatus;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.util.List;

@Value
@Data
@Builder
public class Order {
    String id;
    String consumerName;
    String address;
    String email;
    OrderStatus orderStatus;
    List<OrderItem> orderItems;
    double totalPrice;
}
