package com.klaystakingservice.business.order.application;

import com.klaystakingservice.business.account.application.AccountRepository;
import com.klaystakingservice.business.account.entity.Account;
import com.klaystakingservice.business.order.domain.product.application.OrderedProductService;
import com.klaystakingservice.business.order.domain.product.form.OrderedProductForm;
import com.klaystakingservice.business.order.entity.Order;
import com.klaystakingservice.business.order.form.OrderForm;
import com.klaystakingservice.business.staking.domain.product.application.StakingRepository;
import com.klaystakingservice.business.staking.domain.product.entity.Staking;
import com.klaystakingservice.common.error.code.ErrorCode;
import com.klaystakingservice.common.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final AccountRepository accountRepository;

    private final StakingRepository stakingRepository;

    private final OrderedProductService orderedProductService;

    public Order save(OrderForm.Request.AddDTO addDTO){
        final Account account = accountRepository.findById(addDTO.getAccountId())
                                                 .orElseThrow(() -> new BusinessException(ErrorCode.ACCOUNT_NOT_FOUND));
        final Staking staking = stakingRepository.findById(addDTO.getStakingId())
                                                 .orElseThrow(() -> new BusinessException(ErrorCode.STAKING_NOT_FOUND));
        final Order order = orderRepository.save(addDTO.toEntity(account,staking));

        orderedProductService.save(OrderedProductForm.Request.AddDTO.builder()
                                                                    .expireDay(staking.getExpireDay())
                                                                    .order(order)
                                                                    .build());

        return order;
    }

    public Order findById(Long orderId){
        return orderRepository.findById(orderId)
                .orElseThrow(()-> new BusinessException(ErrorCode.ORDER_NOT_FOUND));
    }
}
