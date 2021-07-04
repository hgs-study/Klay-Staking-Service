package com.klaystakingservice.business.order.api;

import com.klaystakingservice.business.account.application.AccountService;
import com.klaystakingservice.business.account.entity.Account;
import com.klaystakingservice.business.order.application.OrderService;
import com.klaystakingservice.business.order.domain.product.application.OrderedProductService;
import com.klaystakingservice.business.order.domain.product.entity.OrderedProduct;
import com.klaystakingservice.business.order.entity.Order;
import com.klaystakingservice.business.order.form.OrderForm.*;
import com.klaystakingservice.business.staking.domain.product.application.StakingService;
import com.klaystakingservice.business.staking.domain.product.entity.Staking;
import com.klaystakingservice.business.staking.domain.product.form.StakingForm;
import com.klaystakingservice.common.response.dto.MessageDTO;
import com.klaystakingservice.common.response.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final AccountService accountService;
    private final StakingService stakingService;
    private final OrderedProductService orderedProductService;

    @PostMapping("/order")
    public ResponseEntity<MessageDTO> saveOrder(@Valid @RequestBody Request.Add add){
        final Account account = accountService.findById(add.getAccountId());
        final Staking staking = stakingService.findById(add.getStakingId());
        final OrderedProduct orderedProduct = orderedProductService.create(add.toEntity(account,staking));

        orderedProductService.save(orderedProduct);

        String stakingName = StakingForm.Response.Find.of(staking).getName();
        return ApiResponse.set(HttpStatus.CREATED,"/", stakingName +" 상품이 주문되었습니다.");
    }

    @GetMapping("/order/{orderId}")
    public Response.Find findOrder(@PathVariable("orderId") Long orderId){
        final Order order = orderService.findById(orderId);

        return Response.Find.of(order);
    }
}
