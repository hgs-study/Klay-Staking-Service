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
import com.klaystakingservice.common.response.dto.ResponseDTO;
import com.klaystakingservice.common.response.util.ApiResponse;
import com.klaystakingservice.common.security.jwt.JwtProperties;
import io.netty.util.Constant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.UUID;

@Api(tags = "1.Account")
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

    @ApiOperation(value = "회원가입", notes = "유저 등록(회원가입)합니다.")
    @PostMapping("/join")
    public ResponseEntity<ResponseDTO> join(@Valid @RequestBody Request.Join join){
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

        accountService.join(account);
        walletService.init(wallet);
        transactionUtil.RewardKlayWhenJoin(wallet.getAddress());
        tokenService.initWalletToken(wallet);

        final Response.Find responseAccount = Response.Find.of(account);
        return ApiResponse.set(HttpStatus.CREATED,"/",responseAccount.getEmail()+"회원이 정상적으로 회원가입 되었습니다.");
    }

//    @ApiImplicitParams({
//        @ApiImplicitParam(name = JwtProperties.ACCESS_TOKEN_NAME , value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
//    })
    @ApiOperation(value = "유저 상세 조회", notes = "해당 유저를 상세 조회합니다.")
    @GetMapping("/accounts/{email}")
    public Response.Find findAccount(@PathVariable String email){
        return Response.Find.of(accountService.findByEmail(email));
    }

    @ApiOperation(value = "유저 수정", notes = "해당 유저를 수정합니다.")
    @PatchMapping("/accounts/{accountId}")
    public ResponseEntity<ResponseDTO> modifyAccount(@PathVariable Long accountId, @Valid @RequestBody Request.Modify modify) {
        accountValidator.validate(modify);

        final Account account = accountService.findById(accountId);
        account.toUpdate(modify.getEmail(), modify.getPassword(), modify.getAddress());

        final Response.Find responseAccount = Response.Find.of(account);
        return ApiResponse.set(HttpStatus.OK,"/",responseAccount.getEmail()+" 회원이 정상적으로 수정되었습니다.");
    }

    @ApiOperation(value = "유저 삭제", notes = "해당 유저를 삭제합니다.")
    @DeleteMapping("/accounts/{accountId}")
    public ResponseEntity<ResponseDTO> deleteAccount(@PathVariable(name = "accountId")Long accountId){
        final Account account = accountService.findById(accountId);
        accountService.deleteAccountAndWallet(account);

        final Response.Find responseAccount = Response.Find.of(account);
        return ApiResponse.set(HttpStatus.OK,"/",responseAccount.getEmail()+" 회원이 정상적으로 삭제되었습니다.");
    }

}
