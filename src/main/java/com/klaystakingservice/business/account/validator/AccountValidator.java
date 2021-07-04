package com.klaystakingservice.business.account.validator;

import com.klaystakingservice.business.account.application.AccountRepository;
import com.klaystakingservice.business.account.application.AccountService;
import com.klaystakingservice.business.account.form.AccountForm.*;
import com.klaystakingservice.common.error.code.ErrorCode;
import com.klaystakingservice.common.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountValidator {

    private final AccountService accountService;
    private final AccountRepository accountRepository;

    public void validate(Request.Join join){
        validMatchPasswordAndCheckPassword(join.getPassword(), join.getCheckPassword());
        validEmailDuplicate(join.getEmail());
    }

    public void validate(Request.Modify modify){
        InvalidPassword(modify);
    }

    public void validMatchPasswordAndCheckPassword(String password, String checkPassword) {
        if(isDifferent(password, checkPassword))
            throw new BusinessException(ErrorCode.PASSWORD_NOT_MATCH);
    }

    private boolean isDifferent(String password, String checkPassword) {
        return !password.equals(checkPassword);
    }

    private void validEmailDuplicate(String email) {
        if(isNotExists(email))
            throw new BusinessException(ErrorCode.EMAIL_DUPLICATE);
    }

    private boolean isNotExists(String email) {
        return accountRepository.existsByEmail(email);
    }

    public void InvalidPassword(Request.Modify modify){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        final String encodeInputPassword = passwordEncoder.encode(modify.getPassword());
        final String dbPassword = accountService.findByEmail(modify.getEmail()).getPassword();

        System.out.println("encodeInputPassword = " + encodeInputPassword);
        System.out.println("dbPassword = " + dbPassword);

        if(isDifferent(encodeInputPassword,dbPassword))
            throw new BusinessException(ErrorCode.INVALID_PASSWORD);
    }
}
