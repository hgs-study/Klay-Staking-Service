package com.klaystakingservice.domain.wallet.api;

import com.klaystakingservice.business.account.application.AccountService;
import com.klaystakingservice.business.account.entity.Account;
import com.klaystakingservice.business.staking.domain.product.api.StakingController;
import com.klaystakingservice.business.wallet.api.WalletController;
import com.klaystakingservice.business.wallet.application.WalletService;
import com.klaystakingservice.business.wallet.entity.Wallet;
import com.klaystakingservice.common.security.config.SecurityConfig;
import com.klaystakingservice.common.security.jwt.CustomAuthenticationFilter;
import com.klaystakingservice.common.security.jwt.JwtAuthenticationFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = WalletController.class, excludeFilters = {
        @ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE,
                classes = {SecurityConfig.class,
                        CustomAuthenticationFilter.class,
                        JwtAuthenticationFilter.class})
})
public class WalletControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    WalletService walletService;

    @MockBean
    AccountService accountService;

    @Autowired
    WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup()
    {
        //Init MockMvc Object and build
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @DisplayName("지갑 주소 조회")
    @Test
    void getWallet() throws Exception {
        final String email = "hgstudy_@naver.com";
        final Account account = new Account("hgstudy_@naver.com","password");
        final Wallet wallet = new Wallet("0x5164554464575413122312", account);

        given(accountService.findByEmail(email)).willReturn(account);
        given(walletService.findByAccount(account)).willReturn(wallet);

        mockMvc.perform(get("/wallets/{email}",email))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(wallet.getAddress())))
                .andDo(print());

        verify(accountService).findByEmail(email);
        verify(walletService).findByAccount(account);

    }

}
