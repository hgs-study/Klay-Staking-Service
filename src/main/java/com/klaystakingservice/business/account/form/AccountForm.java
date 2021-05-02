package com.klaystakingservice.business.account.form;

import com.klaystakingservice.business.account.entity.Account;
import com.klaystakingservice.business.account.domain.Address;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

public class AccountForm {

    public static class Request{
        @Getter
        @Setter
        @NoArgsConstructor
        public static class AddDTO {
            @NotBlank(message = "이메일을 입력해주세요.")
//            @Email(message = "이메일 형식대로 입력해주세요.")
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
        @NoArgsConstructor
        public static class ModifyDTO{
            @NotBlank(message = "이메일을 입력해주세요.")
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
    }

    public static class Response{
        @Getter
        @Setter
        public static class FindDTO {
            private String email;
            private Address address;

            private FindDTO(String email, Address address){
                this.email = email;
                this.address = address;
            }

            public static FindDTO of(Account account){
                return new FindDTO(account.getEmail(),account.getAddress());
            }
        }
    }
}
