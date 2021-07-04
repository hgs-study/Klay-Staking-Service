package com.klaystakingservice.business.order.domain.product.form;

import com.klaystakingservice.business.order.domain.product.entity.OrderedProduct;
import com.klaystakingservice.business.order.entity.Order;
import lombok.*;

public class OrderedProductForm {
    public static class Request{

        @Getter
        @NoArgsConstructor(access = AccessLevel.PROTECTED)
        public static class Add {
            private Long expireDay;
            private boolean expireStatus;
            private Order order;

            public void setExpireDay(Long expireDay) {
                this.expireDay = expireDay;
            }

            @Builder
            private Add(Long expireDay, boolean expireStatus, Order order){
                this.expireDay = expireDay;
                this.expireStatus = expireStatus;
                this.order = order;
            }

            public OrderedProduct toEntity(){
                return OrderedProduct.builder()
                                     .expireDay(expireDay)
                                     .expireStatus(expireStatus)
                                     .order(order)
                                     .build();
            }
        }
    }

    public static class Response{

        @Getter
        @Setter
        public static class Find {
            private Long orderedProductId;
            private Long expireDay;
            private boolean expireStatus;
            private Long orderId;

            private Find(Long orderedProductId, Long expireDay, boolean expireStatus, Long orderId){
                this.orderedProductId = orderedProductId;
                this.expireDay = expireDay;
                this.expireStatus = expireStatus;
                this.orderId = orderId;
            }

            public static Find of(OrderedProduct orderedProduct){
                return new Find(
                                      orderedProduct.getId(),
                                      orderedProduct.getExpireDay(),
                                      orderedProduct.isExpireStatus(),
                                      orderedProduct.getId()
                                  );
            }
        }
    }
}
