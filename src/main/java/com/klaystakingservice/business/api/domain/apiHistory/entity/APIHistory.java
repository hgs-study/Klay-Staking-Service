package com.klaystakingservice.business.api.domain.apiHistory.entity;

import com.klaystakingservice.business.account.entity.Account;
import com.klaystakingservice.business.api.entity.API;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.lang.reflect.Member;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class APIHistory {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "api_history_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "api_id")
    private API api;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "account_id")
    private Account account;
}
