package com.tb.javaecommerce.repository;

import com.tb.javaecommerce.repository.entity.ProductEntity;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends NaturalIdRepository<ProductEntity, UUID> {
}
