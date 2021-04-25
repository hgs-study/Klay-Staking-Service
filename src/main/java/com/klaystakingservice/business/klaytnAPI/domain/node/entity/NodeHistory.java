package com.klaystakingservice.business.klaytnAPI.domain.node.entity;

import com.klaystakingservice.business.account.entity.Account;
import com.klaystakingservice.business.klaytnAPI.entity.KlaytnAPI;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "node_history")
public class NodeHistory {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "node_history_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "klaytn_api_id")
    private KlaytnAPI klaytnApi;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @Builder
    private NodeHistory(KlaytnAPI klaytnAPI, Account account){
        this.klaytnApi = klaytnAPI;
        this.account = account;
    }
}
