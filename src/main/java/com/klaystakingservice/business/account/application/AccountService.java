package com.klaystakingservice.business.account.application;

import com.klaystakingservice.business.account.entity.Account;
import com.klaystakingservice.business.account.enumerated.Role;
import com.klaystakingservice.business.account.form.AccountForm;
import com.klaystakingservice.business.klaytnAPI.domain.transaction.util.TransactionUtil;
import com.klaystakingservice.business.token.application.TokenService;
import com.klaystakingservice.business.wallet.application.WalletRepository;
import com.klaystakingservice.business.wallet.application.WalletService;
import com.klaystakingservice.business.wallet.entity.Wallet;
import com.klaystakingservice.common.error.code.ErrorCode;
import com.klaystakingservice.common.error.exception.BusinessException;
import com.klaystakingservice.common.response.util.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService implements UserDetailsService {

    private final AccountRepository accountRepository;

    private final WalletService walletService;

    private final WalletRepository walletRepository;

    private final TokenService tokenService;

    private final TransactionUtil transactionUtil;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Account> accountWrapper = accountRepository.findByEmail(email);
        Account account = accountWrapper.orElseThrow(() -> new UsernameNotFoundException("아이디가 존재하지 않습니다."));

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("USER"));
        return User.builder()
                   .username(account.getEmail())
                   .password(account.getPassword())
                   .authorities(authorities)
                   .build();
    }

    @Transactional
    public ResponseEntity<?> save(AccountForm.Request.AccountDTO accountDTO) {
        validEmailDuplicate(accountDTO);
        validMatchPassword(accountDTO);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Account account = Account.builder()
                                 .email(accountDTO.getEmail())
                                 .password(passwordEncoder.encode(accountDTO.getPassword()))
                                 .address(accountDTO.getAddress())
                                 .role(Role.ROLE_USER)
                                 .build();

        accountRepository.save(account);
        walletService.create(accountDTO.getEmail());
        tokenService.setWalletToken(account);
        signUpRewardZeroPointKlay(account);

        return ResponseEntity.status(HttpStatus.CREATED).header("Location", "/").body(Response.message("정상적으로 회원가입 되셨습니다."));
    }
    
    //0.1 클레이 보상
    private void signUpRewardZeroPointKlay(Account account) {
        Wallet wallet = walletRepository.findByAccount(account).orElseThrow(()-> new BusinessException(ErrorCode.EMAIL_NOT_FOUND));
        transactionUtil.signUpRewardKlay(wallet.getAddress());
    }

    private void validEmailDuplicate(AccountForm.Request.AccountDTO accountDTO) {
        if(accountRepository.existsByEmail(accountDTO.getEmail()))
            throw new BusinessException(ErrorCode.EMAIL_DUPLICATE);
    }

    private void validMatchPassword(AccountForm.Request.AccountDTO accountDTO) {
        if(!accountDTO.getPassword().equals(accountDTO.getCheckPassword()))
            throw new BusinessException(ErrorCode.PASSWORD_NOT_MATCH);
    }
}
