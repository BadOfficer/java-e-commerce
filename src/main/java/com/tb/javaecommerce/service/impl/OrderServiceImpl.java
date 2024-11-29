package com.tb.javaecommerce.service.impl;

import com.tb.javaecommerce.domain.Order;
import com.tb.javaecommerce.dto.order.OrderRequestDto;
import com.tb.javaecommerce.repository.OrderRepository;
import com.tb.javaecommerce.repository.entity.OrderEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    @Transactional(readOnly = true)
    public Order getOrderById(UUID orderId) {
        OrderEntity orderEntity = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException(orderId.toString()));

        return orderMapper.toOrder(orderEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> getAllOrders() {
        return orderMapper.toOrders(orderRepository.findAll());
    }

    @Override
    public Order createOrder(OrderRequestDto orderRequestDto) {


        return null;
    }

    @Override
    public Order updateOrder(OrderRequestDto orderRequestDto, UUID orderId) {
        return null;
    }

    @Override
    public void deleteOrder(UUID orderId) {

    }
}
