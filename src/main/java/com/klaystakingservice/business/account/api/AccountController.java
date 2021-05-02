package com.klaystakingservice.business.account.api;

import com.klaystakingservice.business.account.application.AccountService;
import com.klaystakingservice.business.account.entity.Account;
import com.klaystakingservice.business.account.form.AccountForm.*;
import com.klaystakingservice.common.response.dto.MessageDTO;
import com.klaystakingservice.common.response.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/account")
    public ResponseEntity<MessageDTO> addAccount(@Valid @RequestBody Request.AddDTO addDTO){
        Account account =  accountService.save(addDTO);

        return ApiResponse.set(HttpStatus.CREATED,"/",account.getEmail()+"회원이 정상적으로 회원가입 되었습니다.");
    }

    @GetMapping("/account/{email}")
    public Response.FindDTO findAccount(@PathVariable(name = "email")String email){
        return Response.FindDTO.of(accountService.findByEmail(email));
    }

    @PatchMapping("/account/{accountId}")
    public ResponseEntity<MessageDTO> modifyAccount(@PathVariable(name = "accountId")Long accountId,
                                                    @Valid @RequestBody Request.ModifyDTO modifyDTO) {
        Long modifyAccountId = accountService.modifyAccount(accountId,modifyDTO).getId();

        return ApiResponse.set(HttpStatus.OK,"/",modifyAccountId+"번 회원이 정상적으로 수정되었습니다.");
    }


    @DeleteMapping("/account/{accountId}")
    public ResponseEntity<MessageDTO> deleteAccount(@PathVariable(name = "accountId")Long accountId){
        final Account account = accountService.findById(accountId);
        Long deletedAccountId = accountService.deleteAccountAndWallet(account);

        return ApiResponse.set(HttpStatus.OK,"/",deletedAccountId+"번 회원이 정상적으로 삭제되었습니다.");
    }

}
