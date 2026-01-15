package com.example.fintech_java.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
public class AggregatedPrice {
    @Id
    @GeneratedValue
    private Long id;

    private String symbol;        // BTCUSDT, ETHUSDT
    private BigDecimal bestBid;   // for SELL
    private BigDecimal bestAsk;   // for BUY
    private LocalDateTime updatedAt;
}
