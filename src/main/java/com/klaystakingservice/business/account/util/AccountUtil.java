package com.klaystakingservice.business.account.util;

import com.klaystakingservice.business.account.entity.Account;
import com.klaystakingservice.business.account.form.AccountForm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AccountUtil {
    private final PasswordEncoder passwordEncoder;

    public Account getJoinAccount(AccountForm.Request.Join join){
        return Account.builder()
                      .email(join.getEmail())
                      .password(passwordEncoder.encode(join.getPassword()))
                      .address(join.getAddress())
                      .roles(Collections.singletonList("ROLE_USER"))
                      .userKey(UUID.randomUUID().toString())
                      .build();
    }
}
