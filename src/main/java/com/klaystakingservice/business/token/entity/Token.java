package com.klaystakingservice.business.token.entity;

import com.klaystakingservice.business.tokenAmount.entity.TokenAmount;
import lombok.*;

import javax.persistence.*;
import java.util.List;

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

    @OneToMany(mappedBy = "token")
    private List<TokenAmount> tokenAmounts;

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
//
//    public void addWallet(Wallet wallet){
//        this.wallet = wallet;
//        wallet.getTokens().add(this);
//    }


}
