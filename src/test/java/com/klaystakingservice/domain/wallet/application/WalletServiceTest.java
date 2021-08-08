package com.klaystakingservice.domain.wallet.application;

import com.klaystakingservice.business.account.entity.Account;
import com.klaystakingservice.business.wallet.application.WalletRepository;
import com.klaystakingservice.business.wallet.application.WalletService;
import com.klaystakingservice.business.wallet.entity.Wallet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class WalletServiceTest {

    @InjectMocks
    WalletService walletService;

    @Mock
    WalletRepository walletRepository;

    @DisplayName("Account로 Wallet 가져오기")
    @Test
    void findByAccount() {
        final Account account = new Account("hgstudy_@naver.com", "password");
        final Wallet wallet = new Wallet("0x5164554464575413122312", account);

        given(walletRepository.findByAccount(account)).willReturn(Optional.of(wallet));

        final Wallet findWallet = walletService.findByAccount(account);

        assertEquals(wallet.getAddress(), findWallet.getAddress());
        verify(walletRepository).findByAccount(account);

    }
}