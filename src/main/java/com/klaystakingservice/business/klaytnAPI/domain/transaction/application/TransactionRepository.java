package com.klaystakingservice.business.klaytnAPI.domain.transaction.application;

import com.klaystakingservice.business.klaytnAPI.domain.transaction.entity.TransactionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionHistory,Long> {

}
