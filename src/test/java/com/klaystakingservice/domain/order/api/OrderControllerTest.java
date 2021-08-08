package com.klaystakingservice.domain.order.api;


import com.klaystakingservice.business.account.api.AccountController;
import com.klaystakingservice.business.account.application.AccountService;
import com.klaystakingservice.business.account.entity.Account;
import com.klaystakingservice.business.order.api.OrderController;
import com.klaystakingservice.business.order.application.OrderService;
import com.klaystakingservice.business.order.domain.product.application.OrderedProductService;
import com.klaystakingservice.business.order.domain.product.entity.OrderedProduct;
import com.klaystakingservice.business.order.entity.Order;
import com.klaystakingservice.business.staking.domain.product.application.StakingService;
import com.klaystakingservice.business.staking.domain.product.entity.Staking;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static reactor.core.publisher.Mono.when;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = OrderController.class, excludeFilters = {
            @ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE,
                    classes = {SecurityConfig.class,
                            CustomAuthenticationFilter.class,
                            JwtAuthenticationFilter.class})
})
public class OrderControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    OrderService orderService;

    @MockBean
    AccountService accountService;

    @MockBean
    StakingService stakingService;

    @MockBean
    OrderedProductService orderedProductService;

    @Autowired
    WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup()
    {
        //Init MockMvc Object and build
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    @DisplayName("스테이킹 주문 저장")
    @Test
    void save() throws Exception {
        final Long accountId = 1L;
        final Long stakingId = 1L;
        final Staking staking = new Staking("staking product#1",new BigDecimal(10),10L);

        given(stakingService.findById(stakingId)).willReturn(staking);
        doNothing().when(orderedProductService).save(any(OrderedProduct.class));

        mockMvc.perform(post("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"accountId\": "+accountId+", \"stakingId\" : "+stakingId+" }"))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString(staking.getName())))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        verify(orderedProductService).save(any());
        verify(stakingService).findById(stakingId);
    }

    @DisplayName("주문 상세 조회")
    @Test
    void getOrder() throws Exception {
        final Long id = 1L;
        final Account account = new Account("hgstudy_@naver.com","password");
        final Staking staking = new Staking("staking product#1",new BigDecimal(10),10L);
        final Order order = new Order(account,staking);

        given(orderService.findById(id)).willReturn(order);

        mockMvc.perform(get("/orders/{id}", id))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andDo(print());

        verify(orderService).findById(id);
    }

}
