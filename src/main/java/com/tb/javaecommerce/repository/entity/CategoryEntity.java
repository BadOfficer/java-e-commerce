package com.tb.javaecommerce.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "category")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder(toBuilder = true)
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_category_id")
    @SequenceGenerator(name = "seq_category_id", sequenceName = "seq_category_id")
    Long id;

    @Column(name = "title", unique = true, nullable = false)
    String title;

    @Column(name = "description")
    String description;
}
