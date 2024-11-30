package com.tb.javaecommerce.repository.entity;

import com.tb.javaecommerce.common.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "\"order\"")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder(toBuilder = true)
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_id_seq")
    @SequenceGenerator(name = "order_id_seq", sequenceName = "order_id_seq")
    long id;

    @Column(name = "consumer_name")
    String consumer_name;

    @Column(name = "address")
    String address;

    @Column(name = "email")
    String email;

    @NaturalId
    @Column(name = "order_reference", unique = true, nullable = false)
    UUID order_reference;

    @Column(name = "total_price")
    Double total_price;

    @Column(name = "order_status")
    @Enumerated(EnumType.ORDINAL)
    OrderStatus order_status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.PERSIST, orphanRemoval = true)
    List<OrderItemEntity> order_items;
}
