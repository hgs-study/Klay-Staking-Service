package com.klaystakingservice.business.account.application;

import com.klaystakingservice.business.account.entity.Account;
import com.klaystakingservice.business.account.form.AccountForm.*;
import com.klaystakingservice.business.wallet.application.WalletRepository;
import com.klaystakingservice.business.wallet.entity.Wallet;
import com.klaystakingservice.common.error.code.ErrorCode;
import com.klaystakingservice.common.error.exception.BusinessException;
import com.klaystakingservice.common.error.exception.EmailNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;



@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class AccountService implements UserDetailsService{

    private final AccountRepository accountRepository;

    private final WalletRepository walletRepository;


//    public Account create(Request.Join join){
//        return Account.builder()
//                      .email(join.getEmail())
//                      .password(passwordEncoder.encode(join.getPassword()))
//                      .address(join.getAddress())
//                      .roles(Collections.singletonList("ROLE_USER"))
//                      .build();
//    }

    @Transactional
    public Account join(Account account) {
        accountRepository.save(account);
        return account;
    }

    public Account findByEmail(String email){
        return accountRepository.findByEmail(email)
                                .orElseThrow(()->new BusinessException(ErrorCode.EMAIL_NOT_FOUND));
    }

    public Account findById(Long accountId){
        return accountRepository.findById(accountId)
                                .orElseThrow(()->new BusinessException(ErrorCode.ACCOUNT_NOT_FOUND));
    }

    @Transactional
    public void deleteAccountAndWallet(Account account){
        final Wallet wallet = walletRepository.findByAccount(account)
                                              .orElseThrow(()-> new BusinessException(ErrorCode.ACCOUNT_NOT_FOUND));
        wallet.update(wallet.getAddress(), null);
        accountRepository.delete(account);
    }


    public Account findByUserKey(String userKey) throws UsernameNotFoundException{
        return accountRepository.findByUserKey(userKey)
                                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return accountRepository.findByEmail(email)
                                .orElseThrow(()-> new EmailNotFoundException("회원가입하지 않은 이메일입니다."));
    }


    public UserDetails findUserDetailsByUserKey(String userKey) throws UsernameNotFoundException{
        return accountRepository.findByUserKey(userKey)
                                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
    }

    public Account update(Account account, Request.Modify modify){
        account.update(modify.getEmail(), modify.getPassword(), modify.getAddress());
        return account;
    }
}
