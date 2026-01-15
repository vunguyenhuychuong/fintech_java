package com.example.fintech_java.service;

import com.example.fintech_java.dto.TradeRequest;
import com.example.fintech_java.entity.TradeTransaction;

import java.util.List;

public interface TradeService {

    void executeTrade(Long userId, TradeRequest request);

    List<TradeTransaction> getTradeHistory(Long userId);
}

