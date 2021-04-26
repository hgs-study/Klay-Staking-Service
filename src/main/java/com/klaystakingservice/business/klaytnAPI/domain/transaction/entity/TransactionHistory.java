package com.klaystakingservice.business.klaytnAPI.domain.transaction.entity;

import com.klaystakingservice.business.account.entity.Account;
import com.klaystakingservice.business.klaytnAPI.entity.KlaytnAPI;
import com.klaystakingservice.common.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.math.BigDecimal;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "transaction_history")
public class TransactionHistory extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="transaction_history_id")
    private Long id;

    @Column(name="transaction")
    private String transaction;

    @Column(name = "from_address", length = 42)
    private String fromAddress;

    @Column(name = "to_address", length = 42)
    private String toAddress;

    @Column(name = "amount", precision = 30, scale = 4)
    private BigDecimal amount;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "klaytn_api_id")
    private KlaytnAPI klaytnAPI;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "accout_id")
    private Account account;

    @Builder
    private TransactionHistory(String transaction, String fromAddress, String toAddress,
                               BigDecimal amount, KlaytnAPI klaytnAPI, Account account){
        this.transaction = transaction;
        this.fromAddress = fromAddress;
        this.toAddress = toAddress;
        this.amount = amount;
        this.klaytnAPI = klaytnAPI;
        this.account = account;
    }
}
