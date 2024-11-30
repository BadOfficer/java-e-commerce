package com.tb.javaecommerce.web;

import com.tb.javaecommerce.common.OrderStatus;
import com.tb.javaecommerce.dto.order.OrderRequestDto;
import com.tb.javaecommerce.dto.order.OrderResponseDto;
import com.tb.javaecommerce.repository.projection.OrdersDetailsProjection;
import com.tb.javaecommerce.service.OrderService;
import com.tb.javaecommerce.service.mappers.OrderMapper;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
@Validated
public class OrderController {
    private final OrderService orderService;
    private final OrderMapper orderMapper;

    public OrderController(OrderService orderService, OrderMapper orderMapper) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
    }

    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(@RequestBody @Valid OrderRequestDto orderRequestDto) {
        return ResponseEntity.ok(orderMapper.toOrderResponseDto(orderService.createOrder(orderRequestDto)));
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> getAllOrders() {
        return ResponseEntity.ok(orderMapper.toOrderResponseList(orderService.getAllOrders()));
    }

    @GetMapping("/search")
    public ResponseEntity<List<OrdersDetailsProjection>> getOrderByConsumerName(@RequestParam String consumerName) {
        return ResponseEntity.ok(orderService.getOrdersByConsumerName(consumerName));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable UUID id) {
        return ResponseEntity.ok(orderMapper.toOrderResponseDto(orderService.getOrderById(id)));
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable UUID id) {
        orderService.deleteOrder(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderResponseDto> updateOrderStatus(@PathVariable UUID id, @RequestParam OrderStatus status) {
        return ResponseEntity.ok(orderMapper.toOrderResponseDto(orderService.updateOrderStatus(id, status)));
    }
}
