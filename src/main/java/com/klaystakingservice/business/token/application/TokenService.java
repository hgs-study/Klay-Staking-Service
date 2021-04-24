package com.klaystakingservice.business.token.application;

import com.klaystakingservice.business.account.entity.Account;
import com.klaystakingservice.business.token.entity.Token;
import com.klaystakingservice.business.wallet.application.WalletRepository;
import com.klaystakingservice.business.tokenAmount.application.TokenAmountRepository;
import com.klaystakingservice.business.tokenAmount.entity.TokenAmount;
import com.klaystakingservice.business.wallet.entity.Wallet;
import com.klaystakingservice.common.error.code.ErrorCode;
import com.klaystakingservice.common.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final TokenRepository tokenRepository;

    private final WalletRepository walletRepository;

    private final TokenAmountRepository tokenAmountRepository;

    @Transactional
    public void setWalletToken(Account account){
        List<Token> tokens = tokenRepository.findAll();
        Wallet wallet = walletRepository.findByAccount(account).orElseThrow(() -> new BusinessException(ErrorCode.WALLET_NOT_FOUND));

        List<TokenAmount> tokenAmounts = new ArrayList<>();
        tokens.stream()
                .map(t -> tokenAmounts.add(
                            TokenAmount.builder()
                                       .token(t)
                                       .wallet(wallet)
                                       .build()
                    )
                )
                .collect(Collectors.toList());

        tokenAmountRepository.saveAll(tokenAmounts);
    }

}
