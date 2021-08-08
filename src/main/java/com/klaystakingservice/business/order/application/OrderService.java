package com.klaystakingservice.business.order.application;

import com.klaystakingservice.business.order.domain.product.application.OrderedProductService;
import com.klaystakingservice.business.order.domain.product.entity.OrderedProduct;
import com.klaystakingservice.business.order.domain.product.form.OrderedProductForm;
import com.klaystakingservice.business.order.entity.Order;
import com.klaystakingservice.common.error.code.ErrorCode;
import com.klaystakingservice.common.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final OrderedProductService orderedProductService;

    public void save(Order order){
        final OrderedProduct orderedProduct = orderedProductService.create(order);

        orderedProductService.save(orderedProduct);
        orderRepository.save(order);
    }

    public Order findById(Long orderId){
        return orderRepository.findById(orderId)
                .orElseThrow(()-> new BusinessException(ErrorCode.ORDER_NOT_FOUND));
    }
}
