package com.klaystakingservice.business.account.domain;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor
@Getter
@Setter
public class Address {
    private String zipCode;
    private String city;
    private String street;
    private String subStreet;

    @Builder
    private Address(String zipCode, String city, String street, String subStreet){
        this.zipCode = zipCode;
        this.city = city;
        this.street = street;
        this.subStreet = subStreet;
    }

}
