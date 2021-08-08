package com.klaystakingservice.business.wallet.api;

import com.klaystakingservice.business.account.application.AccountService;
import com.klaystakingservice.business.account.entity.Account;
import com.klaystakingservice.business.wallet.application.WalletService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Api(tags = "6.Wallet")
@RestController
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;
    private final AccountService accountService;

    @ApiOperation(value = "지갑 주소 조회", notes = "지갑 주소를 조회합니다.")
    @GetMapping("/wallets/{email}")
    public String findWalletAddress(@PathVariable String email){
        final Account account = accountService.findByEmail(email);

        return walletService.findByAccount(account).getAddress();
    }
}
