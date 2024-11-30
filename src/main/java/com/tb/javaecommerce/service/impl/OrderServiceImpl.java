package com.tb.javaecommerce.service.impl;

import com.tb.javaecommerce.common.OrderStatus;
import com.tb.javaecommerce.common.ProductStatus;
import com.tb.javaecommerce.domain.Order;
import com.tb.javaecommerce.dto.order.OrderItemRequestDto;
import com.tb.javaecommerce.dto.order.OrderRequestDto;
import com.tb.javaecommerce.repository.OrderRepository;
import com.tb.javaecommerce.repository.ProductRepository;
import com.tb.javaecommerce.repository.entity.OrderEntity;
import com.tb.javaecommerce.repository.entity.OrderItemEntity;
import com.tb.javaecommerce.repository.entity.ProductEntity;
import com.tb.javaecommerce.repository.projection.OrdersDetailsProjection;
import com.tb.javaecommerce.service.OrderService;
import com.tb.javaecommerce.service.exception.OrderNotFoundException;
import com.tb.javaecommerce.service.exception.ProductNotFoundException;
import com.tb.javaecommerce.service.exception.ProductStatusIsInCorrectException;
import com.tb.javaecommerce.service.mappers.OrderMapper;
import jakarta.persistence.PersistenceException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public Order createOrder(OrderRequestDto orderRequestDto) {
       try {
           List<OrderItemEntity> orderItems = new ArrayList<>();
           Double totalPrice = 0.0;

           for (OrderItemRequestDto orderItem: orderRequestDto.getOrderItems()) {
               ProductEntity product = productRepository.findByNaturalId(UUID.fromString(orderItem.getProductId()))
                       .orElseThrow(() -> new ProductNotFoundException(orderItem.getProductId()));

               if (product.getStatus().equals(ProductStatus.OUT_OF_STOCK) || product.getStatus().equals(ProductStatus.DISCONTINUED)) {
                    throw new ProductStatusIsInCorrectException(product.getStatus().toString());
               }

               OrderItemEntity orderItemEntity = OrderItemEntity.builder()
                       .product(product)
                       .price(product.getPrice() * orderItem.getQuantity())
                       .quantity(orderItem.getQuantity())
                       .build();
               orderItems.add(orderItemEntity);
               totalPrice += orderItemEntity.getPrice();
           }

           OrderEntity order = OrderEntity.builder()
                   .order_items(orderItems)
                   .order_status(OrderStatus.PENDING)
                   .order_reference(UUID.randomUUID())
                   .email(orderRequestDto.getEmail())
                   .consumer_name(orderRequestDto.getConsumerName())
                   .address(orderRequestDto.getAddress())
                   .total_price(totalPrice)
                   .build();

           orderItems.forEach(orderItem -> orderItem.setOrder(order));

           return orderMapper.toOrder(orderRepository.save(order));
       } catch (Exception e) {
           throw new PersistenceException(e);
       }
    }

    @Override
    @Transactional(readOnly = true)
    public Order getOrderById(UUID orderId) {
        OrderEntity order = orderRepository.findByNaturalId(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId.toString()));

        return orderMapper.toOrder(order);
    }

    @Override
    @Transactional
    public void deleteOrder(UUID orderId) {
        try {
            orderRepository.deleteByNaturalId(orderId);
        } catch (Exception e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> getAllOrders() {
        return orderMapper.toOrders(orderRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrdersDetailsProjection> getOrdersByConsumerName(String consumerName) {
        return orderRepository.getOrdersByConsumerName(consumerName);
    }

    @Override
    @Transactional
    public Order updateOrderStatus(UUID orderId, OrderStatus orderStatus) {
        OrderEntity order = orderRepository.findByNaturalId(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId.toString()));
        order.setOrder_status(orderStatus);

        return orderMapper.toOrder(orderRepository.save(order));
    }
}
