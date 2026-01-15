package service;

import dto.BinanceTickerDto;
import dto.HuobiResponse;
import dto.HuobiTickerDto;
import dto.PriceDto;
import entity.AggregatedPrice;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import repository.AggregatedPriceRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PriceAggregationServiceImpl
        implements PriceAggregationService {

    private final AggregatedPriceRepository priceRepository;
    private final RestTemplate restTemplate;

    @Scheduled(fixedRate = 10000)
    @Override
    public void aggregatePrices() {
        // fetch prices from Binance & Huobi
        Map<String, PriceDto> binance = fetchBinance();
        Map<String, PriceDto> huobi = fetchHuobi();

        for (String symbol : List.of("BTCUSDT", "ETHUSDT")) {
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
        }
    }

    @Override
    public List<AggregatedPrice> getLatestPrices() {
        return priceRepository.findBySymbolIn(
                List.of("BTCUSDT", "ETHUSDT"));
    }

    private Map<String, PriceDto> fetchBinance() {

        String url = "https://api.binance.com/api/v3/ticker/bookTicker";

        BinanceTickerDto[] response =
                restTemplate.getForObject(url, BinanceTickerDto[].class);

        Map<String, PriceDto> result = new HashMap<>();

        assert response != null;
        for (BinanceTickerDto dto : response) {
            if ("BTCUSDT".equals(dto.getSymbol()) ||
                    "ETHUSDT".equals(dto.getSymbol())) {

                result.put(dto.getSymbol(),
                        new PriceDto(
                                new BigDecimal(dto.getBidPrice()),
                                new BigDecimal(dto.getAskPrice())
                        )
                );
            }
        }
        return result;
    }

    private Map<String, PriceDto> fetchHuobi() {

        String url = "https://api.huobi.pro/market/tickers";

        HuobiResponse response =
                restTemplate.getForObject(url, HuobiResponse.class);

        Map<String, PriceDto> result = new HashMap<>();

        assert response != null;
        for (HuobiTickerDto dto : response.getData()) {

            String symbol = dto.getSymbol().toUpperCase();

            if ("BTCUSDT".equals(symbol) ||
                    "ETHUSDT".equals(symbol)) {

                result.put(symbol,
                        new PriceDto(dto.getBid(), dto.getAsk())
                );
            }
        }
        return result;
    }
}

