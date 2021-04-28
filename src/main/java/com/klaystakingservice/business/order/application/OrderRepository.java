package com.klaystakingservice.business.order.application;

import com.klaystakingservice.business.account.entity.Account;
import com.klaystakingservice.business.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

    List<Order> findAllByAccount(Account account);
}
