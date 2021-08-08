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

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="token_id")
    private Long id;

    @Column(name="name",nullable = false)
    private String name;

    @Column(name = "symbol",nullable = false)
    private String symbol;

    @Column(name="decimal", nullable = false)
    private int decimal;

    @Column(name = "type",nullable = false)
    private String type;

    @Column(name = "contractAddress", length = 42)
    private String contractAddress;

    @OneToMany(mappedBy = "token")
    private List<TokenAmount> tokenAmounts;

    @Builder
    private Token(String name, String symbol, int decimal,String type, String contractAddress){
        this.name = name;
        this.symbol = symbol;
        this.decimal = decimal;
        this.type = type;
        this.contractAddress = contractAddress;
    }

    public Token(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
    }
}
