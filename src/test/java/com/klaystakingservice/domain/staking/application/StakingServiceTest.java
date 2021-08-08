package com.klaystakingservice.domain.staking.application;

import com.klaystakingservice.business.staking.domain.product.application.StakingRepository;
import com.klaystakingservice.business.staking.domain.product.application.StakingService;
import com.klaystakingservice.business.staking.domain.product.entity.Staking;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class StakingServiceTest {

    @InjectMocks
    StakingService stakingService;

    @Mock
    StakingRepository stakingRepository;

    @DisplayName("스테이킹 등록")
    @Test
    void save(){
        final Staking staking = new Staking("staking product#1",new BigDecimal(10),10L);

        given(stakingRepository.save(staking)).willReturn(staking);

        final Staking saveStaking = stakingService.save(staking);

        assertEquals(staking.getName(), saveStaking.getName());
        verify(stakingRepository).save(staking);
    }


    @DisplayName("스테이킹 상세 조회")
    @Test
    void get(){
        final Long id = 1L;
        final Staking staking = new Staking("staking product#1",new BigDecimal(10),10L);

        given(stakingRepository.findById(id)).willReturn(Optional.of(staking));

        final Staking findStaking = stakingService.findById(id);

        assertEquals(staking.getName(), findStaking.getName());
        verify(stakingRepository).findById(id);
    }

    @DisplayName("스테이킹 삭제")
    @Test
    void delete(){
        final Staking staking = new Staking("staking product#1",new BigDecimal(10),10L);

        doNothing().when(stakingRepository).delete(staking);

        stakingService.deleteStaking(staking);

        verify(stakingRepository).delete(staking);
    }
}
