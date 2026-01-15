package com.example.fintech_java.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceDto {

    private BigDecimal bid; // used for SELL
    private BigDecimal ask; // used for BUY
}

