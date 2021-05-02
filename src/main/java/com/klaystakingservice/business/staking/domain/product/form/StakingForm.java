package com.klaystakingservice.business.staking.domain.product.form;

import com.klaystakingservice.business.account.domain.Address;
import com.klaystakingservice.business.account.entity.Account;
import com.klaystakingservice.business.account.form.AccountForm;
import com.klaystakingservice.business.staking.domain.product.entity.Staking;
import com.klaystakingservice.business.token.entity.Token;
import lombok.*;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

public class StakingForm {
    public static class Request{

        @Getter
        @NoArgsConstructor(access = AccessLevel.PROTECTED)
        public static class AddDTO{
            @NotBlank(message = "스테이킹 이름을 입력해주세요.")
            private String name;

            @DecimalMin(value = "0.0000", message = "보상 리워드는 0보다 커야합니다.")
            private BigDecimal rewardAmount;

            @Min(value = 1, message = "만료 날짜는 1일 이상이여야합니다.")
            private Long expireDay;
        }



        @Getter
        @NoArgsConstructor(access = AccessLevel.PROTECTED)
        public static class ModifyDTO{

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
        public static class FindDTO {
            private Long id;
            private String name;
            private BigDecimal rewardAmount;
            private Long expireDay;
            private Long tokenId;

            private FindDTO(Long id, String name,BigDecimal rewardAmount,Long expireDay ,Long tokenId){
                this.id = id;
                this.name = name;
                this.rewardAmount = rewardAmount;
                this.expireDay = expireDay;
                this.tokenId = tokenId;
            }

            public static FindDTO of(Staking staking){
                return new FindDTO(staking.getId(),
                                   staking.getName(),
                                   staking.getRewardAmount(),
                                   staking.getExpireDay(),
                                   staking.getToken().getId());
            }
        }
    }
}
