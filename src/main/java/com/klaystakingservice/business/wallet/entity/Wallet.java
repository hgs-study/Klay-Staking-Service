package com.klaystakingservice.business.wallet.entity;

import com.klaystakingservice.business.account.entity.Account;
import com.klaystakingservice.business.token.entity.Token;
import com.klaystakingservice.common.domain.BaseEntity;
import lombok.*;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

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

//    @OneToMany(mappedBy = "wallet")
//    private List<Token> tokens = new ArrayList<>();

    @OneToMany(mappedBy = "wallet")
    private List<Token> tokens = new ArrayList<>();

//    @OneToOne(fetch = LAZY)
//    @JoinColumn(name="account_id")
//    private Account account;
//
//    @Builder
//    private Wallet(String address, List<Token> tokens,Account account){
//        this.address = address;
//        this.tokens = tokens;
//        this.account = account;
//    }
    @Builder
    private Wallet(String address, List<Token> tokens){
        this.address = address;
        this.tokens = tokens;
    }

}
