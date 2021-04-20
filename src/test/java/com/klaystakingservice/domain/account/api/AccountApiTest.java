package com.klaystakingservice.domain.account.api;

import com.klaystakingservice.business.account.application.AccountRepository;
import com.klaystakingservice.business.account.application.AccountService;
import com.klaystakingservice.business.account.entity.Account;
import com.klaystakingservice.business.account.enumerated.Role;
import com.klaystakingservice.common.error.code.ErrorCode;
import com.klaystakingservice.common.error.exception.BusinessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class AccountApiTest {


    @Autowired
    private AccountRepository accountRepository;



    @Test
    @DisplayName("BaseEntity 확인")
    public void BaseEntity() {
        //given
        LocalDateTime now = LocalDateTime.now();

        Account account = Account.builder()
                                 .email("hgstudy_@naver.com")
                                 .password("password")
                                 .role(Role.USER)
                                 .build();

        accountRepository.save(account);

        //when
        Account savedAccount  = accountRepository.findByEmail(account.getEmail())
                                                 .orElseThrow(()-> new BusinessException(ErrorCode.EMAIL_NOT_FOUND));
        //then
        then(savedAccount.getCreatedDate()).isAfter(now);
    }
}
