package com.klaystakingservice.business.order.domain.product.application;

import com.klaystakingservice.business.order.domain.product.entity.OrderedProduct;
import com.klaystakingservice.business.order.domain.product.form.OrderedProductForm;
import com.klaystakingservice.common.error.code.ErrorCode;
import com.klaystakingservice.common.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderedProductService {
    private final OrderedProductRepository orderedProductRepository;

    @Transactional
    public void save(OrderedProductForm.Request.AddDTO addDTO){
        Long expireDay = UserExpireDay(addDTO);

        orderedProductRepository.save(OrderedProductForm.Request.AddDTO.builder()
                                                                       .expireDay(expireDay)
                                                                       .expireStatus(false)
                                                                       .order(addDTO.getOrder())
                                                                       .build()
                                                                   .toEntity());
    }

    public OrderedProduct findById(Long orderedProductId){
        return orderedProductRepository.findById(orderedProductId)
                                       .orElseThrow(()-> new BusinessException(ErrorCode.ORDERED_PRODUCT_NOT_FOUND));
    }

    private Long UserExpireDay(OrderedProductForm.Request.AddDTO addDTO) {
        long currentUnixTime = System.currentTimeMillis() / 1000;
        return currentUnixTime + (86400 * addDTO.getExpireDay());
    }
}
