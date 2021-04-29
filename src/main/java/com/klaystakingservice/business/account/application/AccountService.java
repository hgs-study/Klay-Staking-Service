package com.klaystakingservice.business.account.application;

import com.klaystakingservice.business.account.entity.Account;
import com.klaystakingservice.business.account.enumerated.Role;
import com.klaystakingservice.business.account.form.AccountForm;
import com.klaystakingservice.business.klaytnAPI.domain.node.application.NodeHistoryRepository;
import com.klaystakingservice.business.klaytnAPI.domain.node.entity.NodeHistory;
import com.klaystakingservice.business.klaytnAPI.domain.transaction.application.TransactionHistoryRepository;
import com.klaystakingservice.business.klaytnAPI.domain.transaction.entity.TransactionHistory;
import com.klaystakingservice.business.klaytnAPI.domain.transaction.util.TransactionUtil;
import com.klaystakingservice.business.order.application.OrderRepository;
import com.klaystakingservice.business.order.entity.Order;
import com.klaystakingservice.business.token.application.TokenService;
import com.klaystakingservice.business.wallet.application.WalletRepository;
import com.klaystakingservice.business.wallet.application.WalletService;
import com.klaystakingservice.business.wallet.entity.Wallet;
import com.klaystakingservice.common.error.code.ErrorCode;
import com.klaystakingservice.common.error.exception.BusinessException;
import com.klaystakingservice.common.response.dto.MessageDTO;
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
@Transactional(readOnly = true)
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
    public ResponseEntity<MessageDTO> save(Account account) {
        validEmailDuplicate(account);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Account createAccount = Account.builder()
                                       .email(account.getEmail())
                                       .password(passwordEncoder.encode(account.getPassword()))
                                       .address(account.getAddress())
                                       .role(Role.ROLE_USER)
                                       .build();

        accountRepository.save(createAccount);
        walletService.create(createAccount.getEmail());
        tokenService.setWalletToken(createAccount);
        signUpRewardZeroPointKlay(createAccount);

        return Response.ApiResponse(HttpStatus.CREATED,"/","정상적으로 회원가입 되었습니다.");
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
    public ResponseEntity<MessageDTO> modifyAccount(Account account){
        accountRepository.save(account);
        return Response.ApiResponse(HttpStatus.OK,"/","정상적으로 수정되었습니다.");
    }

    @Transactional
    public ResponseEntity<MessageDTO> deleteAccountAndWallet(Account account){
        Wallet wallet = walletRepository.findByAccount(account).orElseThrow(()-> new BusinessException(ErrorCode.ACCOUNT_NOT_FOUND));
        wallet.setAccount(null);
        accountRepository.delete(account);

        return Response.ApiResponse(HttpStatus.OK,"/","정상적으로 삭제되었습니다.");
    }

    
    //0.1 클레이 보상
    private void signUpRewardZeroPointKlay(Account account) {
        Wallet wallet = walletRepository.findByAccount(account).orElseThrow(()-> new BusinessException(ErrorCode.EMAIL_NOT_FOUND));
        transactionUtil.signUpRewardKlay(wallet.getAddress());
    }

    private void validEmailDuplicate(Account account) {
        if(accountRepository.existsByEmail(account.getEmail()))
            throw new BusinessException(ErrorCode.EMAIL_DUPLICATE);
    }

    public void validMatchPassword(String password, String checkPassword) {
        if(!password.equals(checkPassword))
            throw new BusinessException(ErrorCode.PASSWORD_NOT_MATCH);
    }
}
