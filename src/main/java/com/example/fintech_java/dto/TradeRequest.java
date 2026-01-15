package com.example.fintech_java.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TradeRequest {
    private String symbol;   // BTCUSDT / ETHUSDT
    private String side;     // BUY / SELL
    private BigDecimal quantity;
}

