package com.klaystakingservice.business.token.entity;

import com.klaystakingservice.business.wallet.entity.Wallet;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Token {

    @Id @GeneratedValue
    @Column(name="token_id")
    private Long id;

    @Column(name="name",nullable = false)
    private String name;

    @Column(name = "symbol",nullable = false)
    private String symbol;

    @Column(name = "contractAddress", length = 42)
    private String contractAddress;

    @OneToMany(mappedBy = "token")
    private List<Wallet> wallets = new ArrayList<>();
}
