package com.klaystakingservice.business.klaytnAPI.application;

import com.klaystakingservice.business.klaytnAPI.entity.KlaytnAPI;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KlaytnAPIRepository extends JpaRepository<KlaytnAPI,Long> {

}
