package com.klaystakingservice.domain.order.application;

import com.klaystakingservice.business.account.entity.Account;
import com.klaystakingservice.business.order.application.OrderRepository;
import com.klaystakingservice.business.order.entity.Order;
import com.klaystakingservice.business.staking.domain.product.entity.Staking;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@ExtendWith({MockitoExtension.class})
public class OrderRepositoryTest {

    @Mock
    OrderRepository orderRepository;

    @DisplayName("주문 등록")
    @Test
    void save(){
        final Account account = new Account("hgstudy_@naver.com","password");
        final Staking staking = new Staking("스테이킹 상품#1",new BigDecimal(10),10L);
        final Order order = new Order(account,staking);

        given(orderRepository.save(any(Order.class))).willReturn(any());

        orderRepository.save(order);

        verify(orderRepository).save(any(Order.class));

    }

    @DisplayName("주문 상세 조회")
    @Test
    void get(){
        final Long id = 1L;

        given(orderRepository.findById(id)).willReturn(any());

        orderRepository.findById(id);

        verify(orderRepository).findById(id);
    }

}
