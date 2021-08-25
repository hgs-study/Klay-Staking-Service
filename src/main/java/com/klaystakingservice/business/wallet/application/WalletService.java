package com.klaystakingservice.business.wallet.application;

import com.klaystakingservice.business.account.entity.Account;
import com.klaystakingservice.business.wallet.entity.Wallet;
import com.klaystakingservice.common.error.code.ErrorCode;
import com.klaystakingservice.common.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class WalletService {

    private final WalletRepository walletRepository;

    @Transactional
    public void init(Wallet wallet){
        walletRepository.save(wallet);
    }

    public Wallet create(String walletAddress,Account account){
        return Wallet.builder()
                     .address(walletAddress)
                     .account(account)
                     .build();
    }

    @Cacheable(key = "#account", value = "findWallet")
    public Wallet findByAccount(Account account){
        return walletRepository.findByAccount(account)
                               .orElseThrow(()-> new BusinessException(ErrorCode.WALLET_NOT_FOUND));
    }
}
