package com.klaystakingservice.business.order.domain.product.api;

import com.klaystakingservice.business.order.domain.product.application.OrderedProductService;
import com.klaystakingservice.business.order.domain.product.form.OrderedProductForm.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "4.Ordered Product")
@RestController
@RequiredArgsConstructor
public class OrderedProductController {

    private final OrderedProductService orderedProductService;

    @ApiOperation(value = "주문 상품 조회", notes = "스테이킹 주문된 상품을 조회합니다.")
    @GetMapping("/orderedProduct/{orderedProductId}")
    private Response.Find findOrderedProduct(@PathVariable("orderedProductId") Long orderedProductId){
        return Response.Find.of(orderedProductService.findById(orderedProductId));
    }
}
