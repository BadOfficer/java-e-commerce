package com.tb.javaecommerce.repository;

import com.tb.javaecommerce.repository.entity.OrderEntity;
import com.tb.javaecommerce.repository.projection.OrdersDetailsProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends NaturalIdRepository<OrderEntity, UUID> {
    @Query("SELECT o.consumer_name AS consumerName, o.total_price AS totalPrice, o.order_status AS orderStatus " +
            "FROM OrderEntity o " +
            "WHERE o.consumer_name = :consumerName " +
            "ORDER BY o.total_price DESC")
    List<OrdersDetailsProjection> getOrdersByConsumerName(@Param("consumerName") String consumerName);
}
