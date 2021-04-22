package com.klaystakingservice.domain.wallet.util;

import com.klaystakingservice.business.wallet.util.WalletUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class WalletUtilTest {

    @Autowired
    private WalletUtil walletUtil;

    private static final int WALLET_ADDRESS_LENGTH = 42;

    @Test
    @DisplayName("Wallet 생성 API 확인")
    public void createWalletAPI(){
        assertThat(walletUtil.create().length()).isEqualTo(WALLET_ADDRESS_LENGTH);
    }
}
