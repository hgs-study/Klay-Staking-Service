package com.klaystakingservice.business.wallet.api;

import com.klaystakingservice.business.account.application.AccountService;
import com.klaystakingservice.business.account.entity.Account;
import com.klaystakingservice.business.wallet.application.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;
    private final AccountService accountService;

    @GetMapping("/wallet")
    public String findWalletAddress(Principal principal){
        final Account account = accountService.findByEmail(principal.getName());

        return walletService.findByAccount(account).getAddress();
    }
}
