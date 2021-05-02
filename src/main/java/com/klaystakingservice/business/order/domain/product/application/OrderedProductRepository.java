package com.klaystakingservice.business.order.domain.product.application;

import com.klaystakingservice.business.order.domain.product.entity.OrderedProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderedProductRepository extends JpaRepository<OrderedProduct,Long> {
}
