package controller;

import entity.AggregatedPrice;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.PriceAggregationService;

import java.util.List;

@RestController
@RequestMapping("/api/prices")
@RequiredArgsConstructor
public class PriceController {

    private final PriceAggregationService priceService;

    @GetMapping("/latest")
    public List<AggregatedPrice> getLatestPrices() {
        return priceService.getLatestPrices();
    }
}

