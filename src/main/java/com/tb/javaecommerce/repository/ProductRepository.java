package com.tb.javaecommerce.repository;

import com.tb.javaecommerce.common.ProductStatus;
import com.tb.javaecommerce.repository.entity.ProductEntity;
import com.tb.javaecommerce.repository.projection.ProductDetailsProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends NaturalIdRepository<ProductEntity, UUID> {
    @Query("SELECT p.title AS title, p.description AS description, p.price AS price " +
            "FROM ProductEntity p " +
            "WHERE p.price >= :minPrice AND p.price <= :maxPrice " +
            "ORDER BY p.title ASC")
    List<ProductDetailsProjection> findProductByPriceRange(
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice);

    List<ProductEntity> findByTitleContainingIgnoreCase(String title);
}
