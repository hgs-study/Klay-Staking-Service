package com.klaystakingservice.business.staking.domain.rewardHistory.entity;

import com.klaystakingservice.business.order.domain.product.entity.OrderedProduct;
import com.klaystakingservice.common.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StakingRewardHistory extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="reward_history_id")
    private Long id;

    @Column(name="reward_amount", precision = 10, scale = 4)
    private BigDecimal rewardAmount;


    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "ordered_product_id")
    private OrderedProduct orderedProduct;
}
