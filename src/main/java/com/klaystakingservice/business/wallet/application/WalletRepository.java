package com.klaystakingservice.business.wallet.application;

import com.klaystakingservice.business.account.entity.Account;
import com.klaystakingservice.business.wallet.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet,Long> {

    Optional<Wallet> findByAccount(Account account);
}
