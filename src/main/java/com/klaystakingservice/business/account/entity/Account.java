package com.klaystakingservice.business.account.entity;

import com.klaystakingservice.business.account.domain.Address;
import com.klaystakingservice.business.account.enumerated.Role;
import com.klaystakingservice.common.domain.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Account extends BaseEntity {
    @Id @GeneratedValue
    private Long Id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "zipCode", column = @Column(name="zip_code")),
        @AttributeOverride(name = "city", column = @Column(name="city")),
        @AttributeOverride(name = "street", column = @Column(name="street")),
        @AttributeOverride(name = "subStreet", column = @Column(name="sub_street"))
    })
    private Address address;

    private Role role;

    @Builder
    private Account(String email,String password,Address address, Role role){
        this.email = email;
        this.password = password;
        this.address = address;
        this.role = role;
    }

}
