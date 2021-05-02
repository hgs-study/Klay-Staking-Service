package com.klaystakingservice.business.staking.domain.product.application;

import com.klaystakingservice.business.staking.domain.product.entity.Staking;
import com.klaystakingservice.business.staking.domain.product.form.StakingForm;
import com.klaystakingservice.business.token.application.TokenService;
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

    private final TokenService tokenService;

    @Transactional
    public Staking saveStaking(StakingForm.Request.AddDTO addDTO){
        final Staking staking = Staking.builder()
                                       .name(addDTO.getName())
                                       .rewardAmount(addDTO.getRewardAmount())
                                       .expireDay(addDTO.getExpireDay())
                                       .token(tokenService.findBySymbol("KLAY"))
                                       .build();

        return stakingRepository.save(staking);
    }

    @Transactional
    public void deleteStaking(Long stakingId){
        final Staking staking = stakingRepository.findById(stakingId)
                                                 .orElseThrow(()-> new BusinessException(ErrorCode.STAKING_NOT_FOUND));
        stakingRepository.delete(staking);
    }

    public Staking findById(Long StakingId){
        return stakingRepository.findById(StakingId)
                                .orElseThrow(()-> new BusinessException(ErrorCode.STAKING_NOT_FOUND));
    }

    @Transactional
    public Staking modifyStaking(Long stakingId, StakingForm.Request.ModifyDTO modifyDTO){
       Staking staking = stakingRepository.findById(stakingId)
                                          .orElseThrow(()-> new BusinessException(ErrorCode.STAKING_NOT_FOUND));

       staking.setUpdate(modifyDTO.getName(),modifyDTO.getRewardAmount(),modifyDTO.getExpireDay());

       return stakingRepository.save(staking);
    }
}
