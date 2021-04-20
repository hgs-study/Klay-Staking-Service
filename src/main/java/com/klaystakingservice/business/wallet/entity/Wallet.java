package com.klaystakingservice.business.wallet.entity;

import com.klaystakingservice.common.domain.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Wallet extends BaseEntity {

    @Id @GeneratedValue
    private Long id;
    private String address;
}
