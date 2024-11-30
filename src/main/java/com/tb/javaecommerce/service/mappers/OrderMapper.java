package com.tb.javaecommerce.service.mappers;

import com.tb.javaecommerce.domain.Order;
import com.tb.javaecommerce.domain.OrderItem;
import com.tb.javaecommerce.dto.order.OrderResponseDto;
import com.tb.javaecommerce.repository.entity.OrderEntity;
import com.tb.javaecommerce.repository.entity.OrderItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(target = "id", source = "order_reference")
    @Mapping(target = "consumerName", source = "consumer_name")
    @Mapping(target = "totalPrice", source = "total_price")
    @Mapping(target = "orderStatus", source = "order_status")
    @Mapping(target = "orderItems", source = "order_items", qualifiedByName = "toOrderItem")
    Order toOrder(OrderEntity orderEntity);

    List<Order> toOrders(List<OrderEntity> orderEntities);

    List<OrderResponseDto> toOrderResponseList(List<Order> orders);

    @Mapping(target = "consumerName", source = "consumerName")
    @Mapping(target = "totalPrice", source = "totalPrice")
    @Mapping(target = "orderStatus", source = "orderStatus")
    @Mapping(target = "orderItems", source = "orderItems")
    OrderResponseDto toOrderResponseDto(Order order);

    @Named("toOrderItem")
    default OrderItem toOrderItem(OrderItemEntity orderItem) {
        return new OrderItem(orderItem.getProduct().getTitle(), orderItem.getQuantity(), orderItem.getPrice());
    }
}
