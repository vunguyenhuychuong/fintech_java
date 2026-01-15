package com.example.fintech_java.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class HuobiTickerDto {
    private String symbol;
    private BigDecimal bid;
    private BigDecimal ask;
}
