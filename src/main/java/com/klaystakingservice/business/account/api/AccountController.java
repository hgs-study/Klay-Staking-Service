package com.klaystakingservice.business.account.api;

import com.klaystakingservice.business.account.application.AccountService;
import com.klaystakingservice.business.account.domain.Address;
import com.klaystakingservice.business.account.entity.Account;
import com.klaystakingservice.business.account.form.AccountForm;
import com.klaystakingservice.common.response.dto.MessageDTO;
import lombok.RequiredArgsConstructor;
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
        accountService.validMatchPassword(addDTO.getPassword(),addDTO.getCheckPassword());

        return accountService.save(Account.builder()
                                            .email(addDTO.getEmail())
                                            .password(addDTO.getPassword())
                                            .address(Address.builder()
                                                    .zipCode(addDTO.getZipCode())
                                                    .city(addDTO.getCity())
                                                    .street(addDTO.getStreet())
                                                    .subStreet(addDTO.getSubStreet())
                                                    .build())
                                            .build());
    }

    @GetMapping("/account/{email}")
    public AccountForm.Response.FindDTO getAccount(@PathVariable(name = "email")String email){
        return AccountForm.Response.FindDTO.of(accountService.findByEmail(email));
    }

    @PatchMapping("/account/{email}")
    public ResponseEntity<MessageDTO> modifyAccount(@Valid @RequestBody AccountForm.Request.ModifyDTO modifyDTO,
                                                    Principal principal){
        accountService.validMatchPassword(modifyDTO.getPassword(),modifyDTO.getCheckPassword());

        return accountService.modifyAccount(Account.builder()
                                                   .email(principal.getName())
                                                   .password(modifyDTO.getPassword())
                                                   .address(Address.builder()
                                                                   .city(modifyDTO.getCity())
                                                                   .zipCode(modifyDTO.getZipCode())
                                                                   .street(modifyDTO.getStreet())
                                                                   .subStreet(modifyDTO.getSubStreet())
                                                                   .build())
                                                   .build());
    }


    @DeleteMapping("/account/{accountId}")
    public ResponseEntity<MessageDTO> deleteAccount(@PathVariable(name = "accountId")Long accountId){
        System.out.println(" delete start= ");
        final Account account = accountService.findById(accountId);
        return accountService.deleteAccountAndWallet(account);
    }

}
