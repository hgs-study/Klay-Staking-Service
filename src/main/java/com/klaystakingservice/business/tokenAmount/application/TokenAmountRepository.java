package com.klaystakingservice.business.tokenAmount.application;

import com.klaystakingservice.business.tokenAmount.entity.TokenAmount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenAmountRepository extends JpaRepository<TokenAmount,Long> {

}
