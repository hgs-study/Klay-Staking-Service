package com.klaystakingservice.domain.account.api;

import com.klaystakingservice.business.account.api.AccountController;
import com.klaystakingservice.business.account.application.AccountService;
import com.klaystakingservice.business.account.entity.Account;
import com.klaystakingservice.business.account.util.AccountUtil;
import com.klaystakingservice.business.account.validator.AccountValidator;
import com.klaystakingservice.business.klaytnAPI.domain.transaction.util.TransactionUtil;
import com.klaystakingservice.business.token.application.TokenService;
import com.klaystakingservice.business.wallet.application.WalletService;
import com.klaystakingservice.business.wallet.util.WalletUtil;
import com.klaystakingservice.common.security.config.SecurityConfig;
import com.klaystakingservice.common.security.jwt.CustomAuthenticationFilter;
import com.klaystakingservice.common.security.jwt.JwtAuthenticationFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.filter;
import static org.assertj.core.api.BDDAssertions.then;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = AccountController.class, excludeFilters = {
            @ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE,
                                  classes = {SecurityConfig.class,
                                             CustomAuthenticationFilter.class,
                                             JwtAuthenticationFilter.class})
})
public class AccountControllerTest {

    @MockBean
    JpaMetamodelMappingContext jpaMetamodelMappingContext;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @MockBean
    private TokenService tokenService;

    @MockBean
    private WalletService walletService;

    @MockBean
    private AccountValidator accountValidator;

    @MockBean
    private WalletUtil walletUtil;

    @MockBean
    private TransactionUtil transactionUtil;

    @MockBean
    private AccountUtil accountUtil;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup()
    {
        //Init MockMvc Object and build
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @DisplayName("회원가입")
    public void join() throws Exception {
        final Account account = new Account("hgstudy_@naver.com","password");

        given(accountService.join(any())).willReturn(account);

        mockMvc.perform(post("/join")
               .contentType(MediaType.APPLICATION_JSON)
               .content("{\"email\":\"hgstudy_@naver.com\", \"password\" : \"password123\", \"checkPassword\" : \"password123\" }"))
               .andExpect(status().isCreated())
               .andExpect(content().string(containsString(account.getEmail())))
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andDo(print());

        verify(accountService).join(any());
    }
}
