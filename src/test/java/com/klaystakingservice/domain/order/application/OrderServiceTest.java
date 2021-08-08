package com.klaystakingservice.domain.order.application;

import com.klaystakingservice.business.account.entity.Account;
import com.klaystakingservice.business.order.application.OrderRepository;
import com.klaystakingservice.business.order.application.OrderService;
import com.klaystakingservice.business.order.domain.product.application.OrderedProductService;
import com.klaystakingservice.business.order.domain.product.entity.OrderedProduct;
import com.klaystakingservice.business.order.entity.Order;
import com.klaystakingservice.business.staking.domain.product.entity.Staking;
import org.aspectj.weaver.ast.Or;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @InjectMocks
    OrderService orderService;

    @Mock
    OrderRepository orderRepository;

    @Mock
    OrderedProductService orderedProductService;

    @DisplayName("주문 등록")
    @Test
    void save(){
        final Account account = new Account("hgstudy_@naver.com","password");
        final Staking staking = new Staking("스테이킹 상품#1",new BigDecimal(10),10L);
        final Order order = new Order(account,staking);
        final OrderedProduct orderedProduct = new OrderedProduct(10L,true,order);

        given(orderedProductService.create(order)).willReturn(orderedProduct);
        given(orderRepository.save(order)).willReturn(order);

        orderService.save(order);

        verify(orderRepository).save(order);
        verify(orderedProductService).create(order);
    }

    @DisplayName("주문 상세 조회")
    @Test
    void get(){
        final Long id = 1L;
        final Account account = new Account("hgstudy_@naver.com","password");
        final Staking staking = new Staking("스테이킹 상품#1",new BigDecimal(10),10L);
        final Order order = new Order(account,staking);

        given(orderRepository.findById(id)).willReturn(Optional.of(order));

        orderService.findById(id);

        verify(orderRepository).findById(id);
    }
}
