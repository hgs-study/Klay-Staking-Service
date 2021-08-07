package com.klaystakingservice.domain.account.application;

import com.klaystakingservice.business.account.application.AccountRepository;
import com.klaystakingservice.business.account.application.AccountService;
import com.klaystakingservice.business.account.domain.Address;
import com.klaystakingservice.business.account.entity.Account;
import com.klaystakingservice.business.account.form.AccountForm;
import com.klaystakingservice.business.account.form.AccountForm.*;
import com.klaystakingservice.business.wallet.application.WalletRepository;
import com.klaystakingservice.business.wallet.entity.Wallet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.text.html.Option;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @InjectMocks
    AccountService accountService;

    @Mock
    AccountRepository accountRepository;

    @Mock
    WalletRepository walletRepository;

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


    @DisplayName("유저,지갑 정보 삭제")
    @Test
    void deleteAccountAndWallet(){
        final Account account = new Account("hgstudy_@naver.com","password");
        final Wallet wallet = new Wallet("address",account);

        doNothing().when(accountRepository).delete(account);
        given(walletRepository.findByAccount(account)).willReturn(Optional.of(wallet));

        accountService.deleteAccountAndWallet(account);

        assertThrows(IllegalArgumentException.class, () ->
                    accountRepository.findByEmail(account.getEmail())
                                     .orElseThrow(()-> new IllegalArgumentException("사용자를 찾을 수 없습니다."))
                    );
        verify(accountRepository).delete(account);
        verify(walletRepository).findByAccount(account);
    }

}
