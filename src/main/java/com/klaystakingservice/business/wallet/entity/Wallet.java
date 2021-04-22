package com.klaystakingservice.business.wallet.entity;

import com.klaystakingservice.business.account.entity.Account;
import com.klaystakingservice.business.token.entity.Token;
import com.klaystakingservice.common.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Wallet extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wallet_id")
    private Long id;

    @Column(name = "address", length = 42, nullable = false)
    private String address;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="token_id")
    private Token token;

    @OneToOne
    @JoinColumn(name="account_id")
    private Account account;
}