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
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class WalletService {

    private final AccountRepository accountRepository;

    private final WalletRepository walletRepository;

    private final TokenRepository tokenRepository;

    private final WalletUtil walletUtil;

    @Transactional
    public void create(String accountEmail){
        System.out.println("create start= " );
        String address = walletUtil.create();
        List<Token> tokens = tokenRepository.findAll();
        Account account = accountRepository.findByEmail(accountEmail)
                                           .orElseThrow(()-> new BusinessException(ErrorCode.EMAIL_NOT_FOUND));

        walletRepository.save(Wallet.builder()
                                    .address(address)
                                    .account(account)
                                    .build());
    }

    public String findAddressByPrincipal(Principal principal){
        Account account = accountRepository.findByEmail(principal.getName())
                                            .orElseThrow(()-> new BusinessException(ErrorCode.EMAIL_NOT_FOUND));
        Wallet wallet = walletRepository.findByAccount(account).orElseThrow(()-> new BusinessException(ErrorCode.WALLET_NOT_FOUND));

        return wallet.getAddress();
    }

    public Wallet findByAccount(Account account){
        return walletRepository.findByAccount(account).orElseThrow(()-> new BusinessException(ErrorCode.WALLET_NOT_FOUND));
    }
}
