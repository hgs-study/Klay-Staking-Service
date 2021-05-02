package com.klaystakingservice.business.order.api;

import com.klaystakingservice.business.order.application.OrderService;
import com.klaystakingservice.business.order.form.OrderForm.*;
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

    @PostMapping("/order")
    public ResponseEntity<MessageDTO> saveOrder(@Valid @RequestBody Request.AddDTO addDTO){
        String stakingName = orderService.save(addDTO).getStaking().getName();
        return ApiResponse.set(HttpStatus.CREATED,"/",stakingName+" 상품이 주문되었습니다.");
    }

    @GetMapping("/order/{orderId}")
    public Response.FindDTO findOrder(@PathVariable("orderId") Long orderId){
        return Response.FindDTO.of(orderService.findById(orderId));
    }
}
