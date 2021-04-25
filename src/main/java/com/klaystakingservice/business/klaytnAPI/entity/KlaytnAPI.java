package com.klaystakingservice.business.klaytnAPI.entity;

import com.klaystakingservice.common.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "klaytn_api")
public class KlaytnAPI extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="api_id")
    private Long id;

    @Column(name = "api_name")
    private String apiName;

    @Column(name = "api_target")
    private String apiTarget;

    @Column(name="api_url")
    private String apiUrl;

}
