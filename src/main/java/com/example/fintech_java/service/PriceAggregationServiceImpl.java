package com.example.fintech_java.service;

import com.example.fintech_java.dto.BinanceTickerDto;
import com.example.fintech_java.dto.HuobiResponse;
import com.example.fintech_java.dto.HuobiTickerDto;
import com.example.fintech_java.dto.PriceDto;
import com.example.fintech_java.entity.AggregatedPrice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.example.fintech_java.repository.AggregatedPriceRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class PriceAggregationServiceImpl
        implements PriceAggregationService {

    private final AggregatedPriceRepository priceRepository;
    private final RestTemplate restTemplate;

    @Scheduled(fixedRate = 10000)
    @Override
    public void aggregatePrices() {
        log.info("Start price aggregation");
        // fetch prices from Binance & Huobi
        Map<String, PriceDto> binance = fetchBinance();
        Map<String, PriceDto> huobi = fetchHuobi();

        for (String symbol : List.of("BTCUSDT", "ETHUSDT")) {
            if (!binance.containsKey(symbol) || !huobi.containsKey(symbol)) {
                log.warn("Missing price for symbol {}", symbol);
                continue;
            }
            BigDecimal bestBid = binance.get(symbol).getBid()
                    .max(huobi.get(symbol).getBid());

            BigDecimal bestAsk = binance.get(symbol).getAsk()
                    .min(huobi.get(symbol).getAsk());

            AggregatedPrice price = new AggregatedPrice();
            price.setSymbol(symbol);
            price.setBestBid(bestBid);
            price.setBestAsk(bestAsk);
            price.setUpdatedAt(LocalDateTime.now());

            priceRepository.save(price);
            log.info("Aggregated {} | bid={} ask={}", symbol, bestBid, bestAsk);
        }
    }

    @Override
    public List<AggregatedPrice> getLatestPrices() {
        return priceRepository.findBySymbolIn(
                List.of("BTCUSDT", "ETHUSDT"));
    }

    private Map<String, PriceDto> fetchBinance() {

        Map<String, PriceDto> result = new HashMap<>();
        try {
            BinanceTickerDto[] response =
                    restTemplate.getForObject(
                            "https://api.binance.com/api/v3/ticker/bookTicker",
                            BinanceTickerDto[].class
                    );
            if (response == null) {
                return result;
            }

            for (BinanceTickerDto dto : response) {
                if ("BTCUSDT".equals(dto.getSymbol())
                        || "ETHUSDT".equals(dto.getSymbol())) {

                    result.put(dto.getSymbol(),
                            new PriceDto(
                                    new BigDecimal(dto.getBidPrice()),
                                    new BigDecimal(dto.getAskPrice())
                            ));
                }
            }
        } catch (Exception e) {
            log.error("Failed to fetch Binance prices", e);
        }

        return result;
    }

    private Map<String, PriceDto> fetchHuobi() {
        Map<String, PriceDto> result = new HashMap<>();
        try {
            HuobiResponse response =
                    restTemplate.getForObject(
                            "https://api.huobi.pro/market/tickers",
                            HuobiResponse.class
                    );
            if (response == null || response.getData() == null) {
                return result;
            }

            for (HuobiTickerDto dto : response.getData()) {

                String symbol = dto.getSymbol().toUpperCase();

                if ("BTCUSDT".equals(symbol)
                        || "ETHUSDT".equals(symbol)) {

                    result.put(symbol,
                            new PriceDto(dto.getBid(), dto.getAsk()));
                }
            }
        } catch (Exception e) {
            log.error("Failed to fetch Huobi prices", e);
        }

        return result;
    }
}

