package com.klaystakingservice.business.account.form;

import com.klaystakingservice.business.account.entity.Account;
import com.klaystakingservice.business.account.domain.Address;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class AccountForm {

    public static class Request{
        @Getter
        @Setter
        @NoArgsConstructor
        public static class Join {
            @NotBlank(message = "이메일을 입력해주세요.")
            @Email(message = "이메일 형식대로 입력해주세요.")
            private String email;

            @NotBlank(message = "비밀번호를 입력해주세요.")
            private String password;

            @NotBlank(message = "비밀번호 확인을 입력해주세요.")
            private String checkPassword;

            private String zipCode;

            private String city;

            private String street;

            private String subStreet;

            public Address getAddress() {
                return Address.builder()
                              .city(city)
                              .subStreet(subStreet)
                              .street(street)
                              .zipCode(zipCode)
                              .build();
            }

        }

        @Getter
        @Setter
        public static class Login {

            private String email;
            private String password;
        }


        @Getter
        @Setter
        @NoArgsConstructor
        public static class Modify {
            @NotBlank(message = "이메일을 입력해주세요.")
            private String email;

            @NotBlank(message = "비밀번호를 입력해주세요.")
            private String password;

            private String zipCode;

            private String city;

            private String street;

            private String subStreet;

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

    public static class Response{
        @Getter
        @Setter
        public static class Find {
            @ApiModelProperty(example = "유저 이메일")
            private String email;

            @ApiModelProperty(example = "유저 주소")
            private Address address;

            private Find(String email, Address address){
                this.email = email;
                this.address = address;
            }

            public static Find of(Account account){
                return new Find(account.getEmail(),account.getAddress());
            }
        }
    }
}
