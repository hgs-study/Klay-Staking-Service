package com.klaystakingservice.business.wallet.api;

import com.klaystakingservice.business.account.form.AccountForm;
import com.klaystakingservice.business.wallet.application.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @GetMapping("/wallet")
    public String signUp(Principal principal){
        return walletService.findAddressByPrincipal(principal);
    }
}
