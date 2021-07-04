package com.klaystakingservice.business.klaytnAPI.entity;

import com.klaystakingservice.business.klaytnAPI.enumerated.Target;
import com.klaystakingservice.common.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "klaytn_api")
public class KlaytnAPI extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="klaytn_api_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "target")
    private Target target;

    @Builder
    private KlaytnAPI(String name, Target target){
        this.name = name;
        this.target = target;
    }

}
