package com.klaystakingservice.business.tokenAmount.entity;

import com.klaystakingservice.business.token.entity.Token;
import com.klaystakingservice.business.wallet.entity.Wallet;
import com.klaystakingservice.common.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

import java.math.BigDecimal;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TokenAmount extends BaseEntity {
    @Id @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(name="token_amount_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "token_id")
    private Token token;

    @Column(name = "amount", precision = 20, scale = 4)
    private BigDecimal amount;

    @Builder
    private TokenAmount(Wallet wallet, Token token,BigDecimal amount){
        this.wallet = wallet;
        this.token = token;
        this.amount = amount;
    }
}
