package com.klaystakingservice.business.order.domain.product.application;

import com.klaystakingservice.business.order.domain.product.entity.OrderedProduct;
import com.klaystakingservice.business.order.domain.product.form.OrderedProductForm;
import com.klaystakingservice.business.order.entity.Order;
import com.klaystakingservice.business.order.form.OrderForm;
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
        setUserExpireDay(addDTO);

        orderedProductRepository.save(addDTO.toEntity());
    }

    public OrderedProduct findById(Long orderedProductId){
        return orderedProductRepository.findById(orderedProductId)
                                       .orElseThrow(()-> new BusinessException(ErrorCode.ORDERED_PRODUCT_NOT_FOUND));
    }

    private void setUserExpireDay(OrderedProductForm.Request.AddDTO addDTO) {
        long currentUnixTime = System.currentTimeMillis() / 1000;
        Long userExpireDay = currentUnixTime + (86400 * addDTO.getExpireDay());
        addDTO.setExpireDay(userExpireDay);
    }
}
