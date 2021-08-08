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
import com.klaystakingservice.common.response.dto.ResponseDTO;
import com.klaystakingservice.common.response.util.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "3.Order")
@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final AccountService accountService;
    private final StakingService stakingService;
    private final OrderedProductService orderedProductService;

    @ApiOperation(value = "스테이킹 상품 주문", notes = "스테이킹 상품을 주문합니다.")
    @PostMapping("/orders")
    public ResponseEntity<ResponseDTO> saveOrder(@Valid @RequestBody Request.Add add){
        final Account account = accountService.findById(add.getAccountId());
        final Staking staking = stakingService.findById(add.getStakingId());
        final OrderedProduct orderedProduct = orderedProductService.create(add.toEntity(account,staking));

        orderedProductService.save(orderedProduct);

        return ApiResponse.set(HttpStatus.CREATED,"/", staking.getName() +" 상품이 주문되었습니다.");
    }

    @ApiOperation(value = "주문 상세 조회", notes = "스테이킹 상품 주문의 상세 내역을 조회합니다.")
    @GetMapping("/orders/{orderId}")
    public Response.Find findOrder(@PathVariable("orderId") Long orderId){
        final Order order = orderService.findById(orderId);

        return Response.Find.of(order);
    }
}
