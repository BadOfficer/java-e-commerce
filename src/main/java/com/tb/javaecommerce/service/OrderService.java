package com.tb.javaecommerce.service;

import com.tb.javaecommerce.common.OrderStatus;
import com.tb.javaecommerce.domain.Order;
import com.tb.javaecommerce.dto.order.OrderRequestDto;
import com.tb.javaecommerce.repository.projection.OrdersDetailsProjection;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    Order createOrder(OrderRequestDto orderRequestDto);
    Order getOrderById(UUID orderId);
    void deleteOrder(UUID orderId);
    List<Order> getAllOrders();
    List<OrdersDetailsProjection> getOrdersByConsumerName(String consumerName);
    Order updateOrderStatus(UUID orderId, OrderStatus orderStatus);
}
