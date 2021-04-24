package com.klaystakingservice.business.wallet.application;

import com.klaystakingservice.business.account.application.AccountRepository;
import com.klaystakingservice.business.account.entity.Account;
import com.klaystakingservice.business.token.application.TokenRepository;
import com.klaystakingservice.business.token.entity.Token;
import com.klaystakingservice.business.wallet.entity.Wallet;
import com.klaystakingservice.business.wallet.util.WalletUtil;
import com.klaystakingservice.common.error.code.ErrorCode;
import com.klaystakingservice.common.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class WalletService {

    private final AccountRepository accountRepository;
    private final WalletRepository walletRepository;
    private final TokenRepository tokenRepository;
    private final WalletUtil walletUtil;

    @Transactional
    public void create(String accountEmail){

        String address = walletUtil.create();
        List<Token> tokens = tokenRepository.findAll();
        Account account = accountRepository.findByEmail(accountEmail)
                                           .orElseThrow(()-> new BusinessException(ErrorCode.EMAIL_NOT_FOUND));

        walletRepository.save(Wallet.builder()
                                    .address(address)
                                    .account(account)
                                    .build());
    }
}
