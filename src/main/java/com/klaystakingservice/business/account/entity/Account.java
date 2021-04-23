package com.klaystakingservice.business.account.entity;

import com.klaystakingservice.business.account.domain.Address;
import com.klaystakingservice.business.account.enumerated.Role;
import com.klaystakingservice.common.domain.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Account extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long Id;

    @Column(name="email", nullable = false)
    private String email;

    @Column(name="password", nullable = false)
    private String password;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "zipCode", column = @Column(name="zip_code")),
        @AttributeOverride(name = "city", column = @Column(name="city")),
        @AttributeOverride(name = "street", column = @Column(name="street")),
        @AttributeOverride(name = "subStreet", column = @Column(name="sub_street"))
    })
    private Address address;

    @Enumerated(EnumType.STRING)
    @Column(name="role")
    private Role role;

    @Builder
    private Account(String email,String password,Address address, Role role){
        this.email = email;
        this.password = password;
        this.address = address;
        this.role = role;
    }

}
