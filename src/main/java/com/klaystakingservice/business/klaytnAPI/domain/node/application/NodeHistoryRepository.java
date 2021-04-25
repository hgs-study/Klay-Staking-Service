package com.klaystakingservice.business.klaytnAPI.domain.node.application;

import com.klaystakingservice.business.klaytnAPI.domain.node.entity.NodeHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NodeHistoryRepository extends JpaRepository<NodeHistory, Long> {
}
