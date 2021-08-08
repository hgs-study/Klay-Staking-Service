package com.klaystakingservice.domain.staking.application;

import com.klaystakingservice.business.staking.domain.product.application.StakingRepository;
import com.klaystakingservice.business.staking.domain.product.entity.Staking;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class StakingRepositoryTest {

    @Mock
    StakingRepository stakingRepository;

    @DisplayName("스테이킹 조회")
    @Test
    void get(){
        final Long id = 1L;
        final Staking staking = new Staking("staking product#1",new BigDecimal(10),10L);

        given(stakingRepository.findById(1L)).willReturn(Optional.of(staking));

        final Staking findStaking = stakingRepository.findById(1L).get();

        assertEquals(staking.getName(), findStaking.getName());
        verify(stakingRepository).findById(1L);
    }
}
