package com.klaystakingservice.business.token.entity;

import com.klaystakingservice.business.account.entity.Account;
import com.klaystakingservice.business.wallet.entity.Wallet;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class Token {

    @Id @GeneratedValue
    @Column(name="token_id")
    private Long id;

    @Column(name="name",nullable = false)
    private String name;

    @Column(name = "symbol",nullable = false)
    private String symbol;

    @Column(name = "type",nullable = false)
    private String type;

    @Column(name = "contractAddress", length = 42)
    private String contractAddress;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;

    @Builder
    private Token(String name, String symbol, String type, String contractAddress){
        this.name = name;
        this.symbol = symbol;
        this.type = type;
        this.contractAddress = contractAddress;
    }

//    @Builder
//    private Token(String name, String symbol, String type, String contractAddress,Wallet wallet){
//        this.name = name;
//        this.symbol = symbol;
//        this.type = type;
//        this.contractAddress = contractAddress;
//        this.wallet = wallet;
//    }

//    private void changeWallet(Wallet wallet){
//        this.wallet = wallet;
//        wallet.getTokens().add(this);
//    }
    public void addWallet(Wallet wallet){
        this.wallet = wallet;
        wallet.getTokens().add(this);
    }


}
