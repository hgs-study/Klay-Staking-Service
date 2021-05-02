package com.klaystakingservice.business.staking.domain.product.entity;

import com.klaystakingservice.business.account.domain.Address;
import com.klaystakingservice.business.account.entity.Account;
import com.klaystakingservice.business.token.entity.Token;
import com.klaystakingservice.common.domain.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "STAKING_PRODUCT")
public class Staking extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="staking_product_id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "reward_amount", nullable = false, precision = 10, scale = 4)
    private BigDecimal rewardAmount;

    @Column(name="expire_day", nullable = false)
    private  int expireDay;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "token_id")
    private Token token;

    @Builder
    private Staking(Long id,String name, BigDecimal rewardAmount, int expireDay, Token token){
        this.id = id;
        this.name = name;
        this.rewardAmount = rewardAmount;
        this.expireDay = expireDay;
        this.token = token;
    }

    public Staking setUpdate(String name, BigDecimal rewardAmount, int expireDay){
        this.name = name;
        this.rewardAmount = rewardAmount;
        this.expireDay = expireDay;
        return this;
    }
}
