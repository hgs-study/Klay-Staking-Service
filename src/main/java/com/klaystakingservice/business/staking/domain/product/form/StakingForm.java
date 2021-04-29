package com.klaystakingservice.business.staking.domain.product.form;

import com.klaystakingservice.business.staking.domain.product.entity.Staking;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

public class StakingForm {
    public static class Request{

        @Getter
        @NoArgsConstructor(access = AccessLevel.PROTECTED)
        public static class AddDTO{
            private String name;
            private BigDecimal rewardAmount;
            private int expireDay;

            @Builder
            private AddDTO(String name,BigDecimal rewardAmount, int expireDay){
                this.name = name;
                this.rewardAmount = rewardAmount;
                this.expireDay = expireDay;
            }
        }
    }
}
