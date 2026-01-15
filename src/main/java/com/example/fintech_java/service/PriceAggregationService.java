package com.example.fintech_java.service;

import com.example.fintech_java.entity.AggregatedPrice;

import java.util.List;

public interface PriceAggregationService {

    void aggregatePrices();

    List<AggregatedPrice> getLatestPrices();
}

