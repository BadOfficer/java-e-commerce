package com.tb.javaecommerce.domain;

import lombok.Data;
import lombok.Value;

@Value
@Data
public class OrderItem {
    long productId;
    int quantity;
    double price;
}
