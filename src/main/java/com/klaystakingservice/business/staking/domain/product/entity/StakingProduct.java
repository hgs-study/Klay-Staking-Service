package com.klaystakingservice.business.staking.domain.product.entity;

import com.klaystakingservice.business.token.entity.Token;
import com.klaystakingservice.common.domain.BaseEntity;
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
public class StakingProduct extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="staking_product_id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "reward_amount", nullable = false, precision = 10, scale = 4)
    private BigDecimal rewardAmount;

    @Column(name="expire_day", nullable = false)
    private  Long expire_day;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "token_id")
    private Token token;

}
