package com.klaystakingservice.business.account.api;

import com.klaystakingservice.business.account.application.AccountService;
import com.klaystakingservice.business.account.entity.Account;
import com.klaystakingservice.business.account.form.AccountForm.*;
import com.klaystakingservice.business.account.validator.AccountValidator;
import com.klaystakingservice.business.klaytnAPI.domain.transaction.util.TransactionUtil;
import com.klaystakingservice.business.token.application.TokenService;
import com.klaystakingservice.business.wallet.application.WalletService;
import com.klaystakingservice.business.wallet.entity.Wallet;
import com.klaystakingservice.business.wallet.util.WalletUtil;
import com.klaystakingservice.common.response.dto.MessageDTO;
import com.klaystakingservice.common.response.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class AccountController {


    private final AccountService accountService;
    private final TokenService tokenService;
    private final WalletService walletService;
    private final AccountValidator accountValidator;
    private final WalletUtil walletUtil;
    private final TransactionUtil transactionUtil;
    private final PasswordEncoder passwordEncoder;


    @PostMapping("/join")
    public ResponseEntity<MessageDTO> join(@Valid @RequestBody Request.Join join){
        accountValidator.validate(join);

//        final Account account = accountService.create(join);
        final Account account = Account.builder()
                                        .email(join.getEmail())
                                        .password(passwordEncoder.encode(join.getPassword()))
                                        .address(join.getAddress())
                                        .roles(Collections.singletonList("ROLE_USER"))
                                        .userKey(UUID.randomUUID().toString())
                                        .build();
        final String walletAddress = walletUtil.create();
        final Wallet wallet = walletService.create(walletAddress, account);

        accountService.save(account);
        walletService.init(wallet);
        transactionUtil.RewardKlayWhenJoin(wallet.getAddress());
        tokenService.initWalletToken(wallet);

        final Response.Find responseAccount = Response.Find.of(account);
        return ApiResponse.set(HttpStatus.CREATED,"/",responseAccount.getEmail()+"회원이 정상적으로 회원가입 되었습니다.");
    }

    @GetMapping("/account/{email}")
    public Response.Find findAccount(@PathVariable String email){
        return Response.Find.of(accountService.findByEmail(email));
    }

    @PatchMapping("/account/{accountId}")
    public ResponseEntity<MessageDTO> modifyAccount(@PathVariable Long accountId, @Valid @RequestBody Request.Modify modify) {
        accountValidator.validate(modify);

        final Account account = accountService.findById(accountId);
        account.toUpdate(modify.getEmail(), modify.getPassword(), modify.getAddress());

        final Response.Find responseAccount = Response.Find.of(account);
        return ApiResponse.set(HttpStatus.OK,"/",responseAccount.getEmail()+" 회원이 정상적으로 수정되었습니다.");
    }


    @DeleteMapping("/account/{accountId}")
    public ResponseEntity<MessageDTO> deleteAccount(@PathVariable(name = "accountId")Long accountId){
        final Account account = accountService.findById(accountId);
        accountService.deleteAccountAndWallet(account);

        final Response.Find responseAccount = Response.Find.of(account);
        return ApiResponse.set(HttpStatus.OK,"/",responseAccount.getEmail()+" 회원이 정상적으로 삭제되었습니다.");
    }

}
