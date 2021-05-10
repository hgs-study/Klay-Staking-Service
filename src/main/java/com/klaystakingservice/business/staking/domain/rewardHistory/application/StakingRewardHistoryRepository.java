package com.klaystakingservice.business.staking.domain.rewardHistory.application;

import com.klaystakingservice.business.staking.domain.rewardHistory.entity.StakingRewardHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StakingRewardHistoryRepository extends JpaRepository<StakingRewardHistory,Long> {
}
