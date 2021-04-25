package com.klaystakingservice.business.token.application;

import com.klaystakingservice.business.token.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token,Long> {

    Optional<Token> findBySymbol(String symbol);
}
