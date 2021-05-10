package com.klaystakingservice.business.order.domain.product.entity;

import com.klaystakingservice.business.order.entity.Order;
import com.klaystakingservice.common.domain.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderedProduct {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ordered_product_id")
    private Long id;

    @Column(name = "expire_day")
    private Long expireDay;

    @Column(name = "expire_status")
    private boolean expireStatus;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Builder
    private OrderedProduct(Long expireDay, boolean expireStatus, Order order){
        this.expireDay = expireDay;
        this.expireStatus = expireStatus;
        this.order = order;
    }

}
