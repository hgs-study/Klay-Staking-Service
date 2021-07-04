package com.klaystakingservice.business.klaytnAPI.domain.node.api;

import com.klaystakingservice.business.account.application.AccountService;
import com.klaystakingservice.business.account.entity.Account;
import com.klaystakingservice.business.klaytnAPI.domain.node.util.NodeUtil;
import com.klaystakingservice.business.wallet.application.WalletRepository;
import com.klaystakingservice.business.wallet.application.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class NodeController {
    private final NodeUtil nodeUtil;

    private final WalletRepository walletRepository;

    private final WalletService walletService;
    private final AccountService accountService;


    @GetMapping("/getBalance")
    public String getBalance(Principal principal){
        final Account account = accountService.findByEmail(principal.getName());
        final String walletAddress = walletService.findByAccount(account).getAddress();

        return nodeUtil.getBalance(walletAddress);
    }
}
