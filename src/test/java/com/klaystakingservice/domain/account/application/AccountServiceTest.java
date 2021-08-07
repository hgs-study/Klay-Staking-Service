package com.klaystakingservice.domain.account.application;

import com.klaystakingservice.business.account.application.AccountRepository;
import com.klaystakingservice.business.account.application.AccountService;
import com.klaystakingservice.business.account.domain.Address;
import com.klaystakingservice.business.account.entity.Account;
import com.klaystakingservice.business.account.form.AccountForm;
import com.klaystakingservice.business.account.form.AccountForm.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @InjectMocks
    AccountService accountService;

    @Mock
    AccountRepository accountRepository;

    @DisplayName("회원 가입")
    @Test
    void join(){
        final Account account = new Account("hgstudy_@naver.com","password");

        given(accountService.join(account)).willReturn(account);
        given(accountRepository.save(account)).willReturn(account);

        final Account saveAccount = accountService.join(account);

        assertEquals(account.getPassword(), saveAccount.getPassword());
        verify(accountRepository).save(account);
    }

    @DisplayName("유저 상세 조회")
    @Test
    void get(){
        final Account account = new Account("hgstudy_@naver.com","password");

        given(accountRepository.findByEmail(account.getEmail())).willReturn(Optional.of(account));

        final Account findAccount = accountService.findByEmail(account.getEmail());

        assertEquals(account.getEmail(), findAccount.getEmail());
        verify(accountRepository).findByEmail(account.getEmail());
    }

    @DisplayName("유저 정보 수정")
    @Test
    void modify(){
        final Account account = new Account("hgstudy_@naver.com","password");
        final Account modifyAccount = new Account("modify_@naver.com","password123");
        final Request.Modify modifyDto = new Request.Modify("modify_@naver.com","password123");

        given(accountService.update(account, modifyDto)).willReturn(modifyAccount);

        final Account modifiedAccount = accountService.update(account, modifyDto);

        assertNotEquals(account.getEmail(),modifiedAccount.getEmail());
        assertEquals(modifyAccount.getEmail(), modifiedAccount.getEmail());
    }



}
