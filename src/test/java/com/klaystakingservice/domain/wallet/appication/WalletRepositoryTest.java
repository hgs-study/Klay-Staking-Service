package com.klaystakingservice.domain.wallet.appication;

import com.klaystakingservice.business.token.application.TokenRepository;
import com.klaystakingservice.business.token.entity.Token;
import com.klaystakingservice.business.wallet.application.WalletRepository;
import com.klaystakingservice.business.wallet.entity.Wallet;
import com.klaystakingservice.common.error.code.ErrorCode;
import com.klaystakingservice.common.error.exception.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class WalletRepositoryTest {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Transactional
    @Test
    @DisplayName("Token & Wallet 생성 및 연관관계 매핑")
    public void createWalletAPI(){
        //given
        Token Klay = Token.builder()
                .name("KLAY")
                .symbol("KLAY")
                .type("KIP-7")
                .contractAddress("-")
                .build();

        Token HGC = Token.builder()
                .name("HGSCoin")
                .symbol("HGC")
                .type("ERC-20")
                .contractAddress("0x1234567894564654561561651")
                .build();

        Stream<Token> tokens = Stream.of(Klay,HGC);
        tokenRepository.saveAll(tokens.collect(Collectors.toList()));

        Wallet walletOne = Wallet.builder()
                                 .address("firstWallet")
                                 .tokens(tokenRepository.findAll())
                                 .build();

        Wallet walletTwo = Wallet.builder()
                                 .address("secondeWallet")
                                 .tokens(tokenRepository.findAll())
                                 .build();

        Stream<Wallet> wallets = Stream.of(walletOne,walletTwo);
        walletRepository.saveAll(wallets.collect(Collectors.toList()));

        //when
        Token firstToken = tokenRepository.findById(1L).orElseThrow(()-> new BusinessException(ErrorCode.TOKEN_NOT_FOUND));
        Wallet firstWallet = walletRepository.findById(1L).orElseThrow(() -> new BusinessException(ErrorCode.WALLET_NOT_FOUND));
        firstToken.addWallet(firstWallet);

        Token secondToken = tokenRepository.findById(2L).orElseThrow(()-> new BusinessException(ErrorCode.TOKEN_NOT_FOUND));
        Wallet secondeWallet = walletRepository.findById(2L).orElseThrow(() -> new BusinessException(ErrorCode.WALLET_NOT_FOUND));
        secondToken.addWallet(secondeWallet);

        //then
        assertThat(firstToken.getWallet().getId()).isEqualTo(firstWallet.getId());
        assertThat(secondToken.getWallet().getId()).isEqualTo(secondeWallet.getId());
    }
}
