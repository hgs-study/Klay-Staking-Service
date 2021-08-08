package com.klaystakingservice.business.staking.domain.product.form;

import com.klaystakingservice.business.staking.domain.product.entity.Staking;
import lombok.*;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

public class StakingForm {
    public static class Request{

        @Getter
        @NoArgsConstructor(access = AccessLevel.PROTECTED)
        public static class Add {
            @NotBlank(message = "스테이킹 이름을 입력해주세요.")
            private String name;

            @DecimalMin(value = "0.0000", message = "보상 리워드는 0보다 커야합니다.")
            private BigDecimal rewardAmount;

            @Min(value = 1, message = "만료 날짜는 1일 이상이여야합니다.")
            private Long expireDay;

            public Add( String name,  BigDecimal rewardAmount, Long expireDay) {
                this.name = name;
                this.rewardAmount = rewardAmount;
                this.expireDay = expireDay;
            }
        }



        @Getter
        @NoArgsConstructor(access = AccessLevel.PROTECTED)
        public static class Modify {

            @NotBlank(message = "스테이킹 이름을 입력해주세요.")
            private String name;

            @DecimalMin(value = "0.0000", message = "보상 리워드는 0보다 커야합니다.")
            private BigDecimal rewardAmount;

            @Min(value = 1, message = "만료 날짜는 1일 이상이여야합니다.")
            private Long expireDay;
        }
    }

    public static class Response{
        @Getter
        @Setter
        public static class Find {
            private Long id;
            private String name;
            private BigDecimal rewardAmount;
            private Long expireDay;
            private Long tokenId;

            public Find(String name, BigDecimal rewardAmount, Long expireDay) {
                this.name = name;
                this.rewardAmount = rewardAmount;
                this.expireDay = expireDay;
            }

            private Find(Long id, String name, BigDecimal rewardAmount, Long expireDay , Long tokenId){
                this.id = id;
                this.name = name;
                this.rewardAmount = rewardAmount;
                this.expireDay = expireDay;
                this.tokenId = tokenId;
            }


            public static Find of(Staking staking){
                return new Find(staking.getId(),
                                   staking.getName(),
                                   staking.getRewardAmount(),
                                   staking.getExpireDay(),
                                   staking.getToken().getId());
            }
        }
    }
}
