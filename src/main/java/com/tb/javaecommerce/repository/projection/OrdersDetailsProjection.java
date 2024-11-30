package com.tb.javaecommerce.repository.projection;

public interface OrdersDetailsProjection {
    String getConsumerName();
    Double getTotalPrice();
    String getOrderStatus();
}
