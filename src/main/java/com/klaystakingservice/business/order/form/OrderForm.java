package com.klaystakingservice.business.order.form;

import com.klaystakingservice.business.account.entity.Account;
import com.klaystakingservice.business.order.entity.Order;
import com.klaystakingservice.business.staking.domain.product.entity.Staking;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class OrderForm {
    public static class Request {

        @Getter
        @NoArgsConstructor(access = AccessLevel.PROTECTED)
        public static class Add {
            private Long accountId;
            private Long stakingId;


            public Order toEntity(Account account, Staking staking){
                return Order.builder()
                            .account(account)
                            .staking(staking)
                            .build();
            }
        }
    }

    public static class Response{

        @Getter
        @Setter
        public static class Find {
            private Long orderId;
            private Long accountId;
            private Long stakingId;

            private Find(Long orderId, Long accountId, Long stakingId){
                this.orderId = orderId;
                this.accountId = accountId;
                this.stakingId = stakingId;
            }

            public static Find of(Order order){
                return new Find(
                                    order.getId(),
                                    order.getAccount().getId(),
                                    order.getStaking().getId()
                                );
            }
        }
    }
}
