package com.klaystakingservice.business.staking.domain.product.application;

import com.klaystakingservice.business.staking.domain.product.entity.Staking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StakingRepository extends JpaRepository<Staking,Long>{

    Optional<Staking> findById(Long id);
}