package com.klaystakingservice.business.account.form;

import com.klaystakingservice.business.account.entity.Account;
import com.klaystakingservice.business.account.enumerated.Address;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embedded;

public class AccountForm {

    public static class Request{
        @Getter
        @Setter
        @NoArgsConstructor
        public static class AccountDTO{
            private String email;
            private String password;
            private String zipCode;
            private String city;
            private String street;
            private String subStreet;

            @Builder
            private AccountDTO(String email, String password,String zipCode,String city,String street,String subStreet){
                this.email = email;
                this.password = password;
                this.zipCode = zipCode;
                this.city = city;
                this.street = street;
                this.subStreet =subStreet;
            }

            public Account toEntity(){
                return Account.builder()
                              .email(email)
                              .password(password)
                              .address(getAddress())
                              .build();
            }

            public Address getAddress() {
                return Address.builder()
                        .city(city)
                        .subStreet(subStreet)
                        .street(street)
                        .zipCode(zipCode)
                        .build();
            }

        }
    }
}
