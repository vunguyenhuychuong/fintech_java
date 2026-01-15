package service;

import entity.AggregatedPrice;

import java.util.List;

public interface PriceAggregationService {

    void aggregatePrices();

    List<AggregatedPrice> getLatestPrices();
}

