package com.klaystakingservice.business.order.domain.product.api;

import com.klaystakingservice.business.order.domain.product.application.OrderedProductService;
import com.klaystakingservice.business.order.domain.product.form.OrderedProductForm.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderedProductController {

    private final OrderedProductService orderedProductService;

    @GetMapping("/orderedProduct/{orderedProductId}")
    private Response.Find findOrderedProduct(@PathVariable("orderedProductId") Long orderedProductId){
        return Response.Find.of(orderedProductService.findById(orderedProductId));
    }
}
