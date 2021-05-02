package com.klaystakingservice.business.account.api;

import com.klaystakingservice.business.account.application.AccountService;
import com.klaystakingservice.business.account.domain.Address;
import com.klaystakingservice.business.account.entity.Account;
import com.klaystakingservice.business.account.form.AccountForm;
import com.klaystakingservice.common.response.dto.MessageDTO;
import com.klaystakingservice.common.response.util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/account")
    public ResponseEntity<MessageDTO> addAccount(@Valid @RequestBody AccountForm.Request.AddDTO addDTO){
        Account account =  accountService.save(addDTO);

        return Response.ApiResponse(HttpStatus.CREATED,"/",account.getEmail()+"회원이 정상적으로 회원가입 되었습니다.");
    }

    @GetMapping("/account/{email}")
    public AccountForm.Response.FindDTO getAccount(@PathVariable(name = "email")String email){
        return AccountForm.Response.FindDTO.of(accountService.findByEmail(email));
    }

    @PatchMapping("/account/{accountId}")
    public ResponseEntity<MessageDTO> modifyAccount(@PathVariable(name = "accountId")Long accountId,
                                                    @Valid @RequestBody AccountForm.Request.ModifyDTO modifyDTO) {
        Long modifyAccountId = accountService.modifyAccount(accountId,modifyDTO).getId();

        return Response.ApiResponse(HttpStatus.OK,"/",modifyAccountId+"번 회원이 정상적으로 수정되었습니다.");
    }


    @DeleteMapping("/account/{accountId}")
    public ResponseEntity<MessageDTO> deleteAccount(@PathVariable(name = "accountId")Long accountId){
        final Account account = accountService.findById(accountId);
        Long deletedAccountId = accountService.deleteAccountAndWallet(account);

        return Response.ApiResponse(HttpStatus.OK,"/",deletedAccountId+"번 회원이 정상적으로 삭제되었습니다.");
    }

}
