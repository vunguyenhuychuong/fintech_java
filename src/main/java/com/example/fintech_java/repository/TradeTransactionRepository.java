package com.example.fintech_java.repository;

import com.example.fintech_java.entity.TradeTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TradeTransactionRepository
        extends JpaRepository<TradeTransaction, Long> {

    List<TradeTransaction> findByUserIdOrderByCreatedAtDesc(Long userId);
}

