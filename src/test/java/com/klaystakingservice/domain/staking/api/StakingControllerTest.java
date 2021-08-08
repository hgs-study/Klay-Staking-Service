package com.klaystakingservice.domain.staking.api;

import com.klaystakingservice.business.order.api.OrderController;
import com.klaystakingservice.business.staking.domain.product.api.StakingController;
import com.klaystakingservice.business.staking.domain.product.application.StakingService;
import com.klaystakingservice.business.staking.domain.product.entity.Staking;
import com.klaystakingservice.business.staking.domain.product.form.StakingForm;
import com.klaystakingservice.business.staking.domain.product.form.StakingForm.*;
import com.klaystakingservice.business.token.application.TokenService;
import com.klaystakingservice.business.token.entity.Token;
import com.klaystakingservice.common.error.exception.BusinessException;
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

import java.math.BigDecimal;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = StakingController.class, excludeFilters = {
        @ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE,
                classes = {SecurityConfig.class,
                        CustomAuthenticationFilter.class,
                        JwtAuthenticationFilter.class})
})
public class StakingControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    StakingService stakingService;

    @MockBean
    TokenService tokenService;

    @Autowired
    WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup()
    {
        //Init MockMvc Object and build
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @DisplayName("스테이킹 등록")
    @Test
    void save() throws Exception {
        final Staking staking = new Staking("staking product#1",new BigDecimal(10),10L);
        final Token klay = new Token("Klay","KLAY");

        given(tokenService.findBySymbol("KLAY")).willReturn(klay);
        given(stakingService.save(staking)).willReturn(staking);
        given(stakingService.create(any(), any())).willReturn(staking);

        mockMvc.perform(post("/stakings")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"staking product#1\" , \"rewardAmount\" : "+new BigDecimal(5) +", \"expireDay\" : "+100L+" }"))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString(staking.getName())))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        verify(tokenService).findBySymbol("KLAY");
        verify(stakingService).save(staking);
        verify(stakingService).create(any(),any());
    }

    @DisplayName("스테이킹 상세 조회")
    @Test
    void getStaking() throws Exception {
        final Long id = 1L;
        final Staking staking = new Staking("staking product#1",new BigDecimal(10),10L);

        given(stakingService.findById(id)).willReturn(staking);

        mockMvc.perform(get("/stakings/{id}",id))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(staking.getName())))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        verify(stakingService).findById(id);
    }

    @DisplayName("스테이킹 정보 수정")
    @Test
    void modify() throws Exception {
        final Long id = 1L;
        final Staking staking = new Staking("staking product#1",new BigDecimal(10),10L);

        given(stakingService.findById(id)).willReturn(staking);

        mockMvc.perform(patch("/stakings/{id}",id)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"modify product#1\" , \"rewardAmount\" : "+new BigDecimal(20) +", \"expireDay\" : "+30L+" }"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("modify product#1")))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        verify(stakingService).findById(id);
    }


    @DisplayName("스테이킹 삭제")
    @Test
    void deleteStaking() throws Exception {
        final Long id = 1L;
        final Staking staking = new Staking("staking product#1",new BigDecimal(10),10L);

        given(stakingService.findById(id)).willReturn(staking);
        doNothing().when(stakingService).deleteStaking(staking);

        mockMvc.perform(delete("/stakings/{id}",id))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(id.toString())))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        verify(stakingService).findById(id);
        verify(stakingService).deleteStaking(staking);
    }
}
