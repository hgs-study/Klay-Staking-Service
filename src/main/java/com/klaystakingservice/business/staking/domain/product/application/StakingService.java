package com.klaystakingservice.business.staking.domain.product.application;

import com.klaystakingservice.business.staking.domain.product.entity.Staking;
import com.klaystakingservice.common.response.dto.MessageDTO;
import com.klaystakingservice.common.response.util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class StakingService {
    private final StakingRepository stakingRepository;

    @Transactional
    public ResponseEntity<MessageDTO> saveStaking(Staking staking){

        stakingRepository.save(staking);
        return Response.ApiResponse(HttpStatus.OK,"/","정상적으로 등록되었습니다.");
    }
}
