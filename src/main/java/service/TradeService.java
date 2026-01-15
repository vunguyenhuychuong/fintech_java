package service;

import dto.TradeRequest;
import entity.TradeTransaction;

import java.util.List;

public interface TradeService {

    void executeTrade(Long userId, TradeRequest request);

    List<TradeTransaction> getTradeHistory(Long userId);
}

