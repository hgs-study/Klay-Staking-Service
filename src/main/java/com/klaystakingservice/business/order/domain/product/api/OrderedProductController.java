package com.klaystakingservice.business.order.domain.product.api;

import com.klaystakingservice.business.order.domain.product.application.OrderedProductService;
import com.klaystakingservice.business.order.domain.product.entity.OrderedProduct;
import com.klaystakingservice.business.order.domain.product.form.OrderedProductForm.*;
import com.klaystakingservice.common.response.dto.MessageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderedProductController {

    private final OrderedProductService orderedProductService;

    @GetMapping("/orderedProduct/{orderedProductId}")
    private Response.FindDTO findOrderedProduct(@PathVariable("orderedProductId") Long orderedProductId){
        return Response.FindDTO.of(orderedProductService.findById(orderedProductId));
    }
}
