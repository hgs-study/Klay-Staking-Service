package com.klaystakingservice.business.order.entity;

import com.klaystakingservice.business.account.entity.Account;
import com.klaystakingservice.business.staking.domain.product.entity.StakingProduct;
import com.klaystakingservice.common.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.lang.reflect.Member;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
public class Order extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "accont_id")
    private Account account;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "staking_product_id")
    private StakingProduct stakingProduct;

}
