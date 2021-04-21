package com.klaystakingservice.business.order.domain.product;

import com.klaystakingservice.business.order.entity.Order;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Column(name = "rewardAmount", nullable = false, precision = 10, scale = 4)
    private BigDecimal rewardAmount;

    @Column(name = "expire_day")
    private int expireDay;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

}
