package com.klaystakingservice.business.account.api;

import com.klaystakingservice.business.account.application.AccountService;
import com.klaystakingservice.business.account.form.AccountForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/account")
    public ResponseEntity<?> signUp(AccountForm.Request.AccountDTO accountDTO){
        return accountService.save(accountDTO);
    }
}
