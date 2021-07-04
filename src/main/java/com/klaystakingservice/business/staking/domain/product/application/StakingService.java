package com.klaystakingservice.business.staking.domain.product.application;

import com.klaystakingservice.business.staking.domain.product.entity.Staking;
import com.klaystakingservice.business.staking.domain.product.form.StakingForm.*;
import com.klaystakingservice.business.token.application.TokenService;
import com.klaystakingservice.business.token.entity.Token;
import com.klaystakingservice.common.error.code.ErrorCode;
import com.klaystakingservice.common.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StakingService {

    private final StakingRepository stakingRepository;

    @Transactional
    public Staking save(Staking staking){

        return stakingRepository.save(staking);
    }

    @Transactional
    public void deleteStaking(Staking staking){

        stakingRepository.delete(staking);
    }

    public Staking findById(Long StakingId){
        return stakingRepository.findById(StakingId)
                                .orElseThrow(()-> new BusinessException(ErrorCode.STAKING_NOT_FOUND));
    }

    public Staking create(Request.Add add, Token token){
        return Staking.builder()
                      .name(add.getName())
                      .rewardAmount(add.getRewardAmount())
                      .expireDay(add.getExpireDay())
                      .token(token)
                      .build();
    }
}
