package com.klaystakingservice.business.wallet.entity;

import com.klaystakingservice.business.account.entity.Account;
import com.klaystakingservice.business.tokenAmount.entity.TokenAmount;
import com.klaystakingservice.common.domain.BaseEntity;
import lombok.*;

import javax.persistence.*;

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

    @OneToMany(mappedBy = "wallet")
    private List<TokenAmount> tokenAmounts;

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL ,orphanRemoval = true)
    @JoinColumn(name = "account_id")
    private Account account;

    public void setAccount(Account account) {
        this.account = account;
    }

    @Builder
    public Wallet(String address, Account account){
        this.address = address;
        this.account = account;
    }

    public void update(String address,  Account account) {
        this.address = address;
        this.account = account;
    }
}
