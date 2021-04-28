package com.klaystakingservice.business.klaytnAPI.domain.node.application;

import com.klaystakingservice.business.account.entity.Account;
import com.klaystakingservice.business.klaytnAPI.domain.node.entity.NodeHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NodeHistoryRepository extends JpaRepository<NodeHistory, Long> {

    List<NodeHistory> findAllByAccount(Account account);
}
