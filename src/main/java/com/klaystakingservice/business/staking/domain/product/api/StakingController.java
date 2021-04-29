package com.klaystakingservice.business.staking.domain.product.api;

import com.klaystakingservice.business.staking.domain.product.application.StakingService;
import com.klaystakingservice.business.staking.domain.product.entity.Staking;
import com.klaystakingservice.business.staking.domain.product.form.StakingForm;
import com.klaystakingservice.business.token.application.TokenService;
import com.klaystakingservice.common.response.dto.MessageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class StakingController {

    private final StakingService stakingService;

    private final TokenService tokenService;

    @PostMapping("/staking")
    public ResponseEntity<MessageDTO> saveStaking(@Valid @RequestBody StakingForm.Request.AddDTO addDTO){

        return stakingService.saveStaking(Staking.builder()
                                                 .name(addDTO.getName())
                                                 .rewardAmount(addDTO.getRewardAmount())
                                                 .expireDay(addDTO.getExpireDay())
                                                 .token(tokenService.findBySymbol("KLAY"))
                                                 .build());
    }
}
