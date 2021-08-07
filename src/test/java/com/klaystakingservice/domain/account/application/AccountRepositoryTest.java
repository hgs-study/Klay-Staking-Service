package com.klaystakingservice.domain.account.application;

import com.klaystakingservice.business.account.application.AccountRepository;
import com.klaystakingservice.business.account.entity.Account;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AccountRepositoryTest {

    @Mock
    AccountRepository accountRepository;

    @DisplayName("유저 저장")
    @Test
    void save(){
        final Account account = new Account("hgstudy_@naver.com","password");
        given(accountRepository.save(account)).willReturn(account);

        final Account saveAccount = accountRepository.save(account);

        assertEquals(account.getEmail(), saveAccount.getEmail());
        verify(accountRepository).save(account);
    }

    @DisplayName("유저 상세 조회")
    @Test
    void get(){
        final Account account = new Account("hgstudy_@naver.com","password");
        given(accountRepository.findByEmail(account.getEmail())).willReturn(Optional.of(account));

        final Account findAccount = accountRepository.findByEmail(account.getEmail()).get();

        assertEquals(account.getEmail(), findAccount.getEmail());
        verify(accountRepository).findByEmail(account.getEmail());
    }


}
