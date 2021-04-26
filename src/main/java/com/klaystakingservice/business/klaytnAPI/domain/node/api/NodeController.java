package com.klaystakingservice.business.klaytnAPI.domain.node.api;

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


    @GetMapping("/getBalance")
    public String getBalance(Principal principal){
        return nodeUtil.getBalance(walletService.findAddressByPrincipal(principal));
    }
}
