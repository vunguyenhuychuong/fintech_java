package com.example.fintech_java.controller;

import com.example.fintech_java.dto.TradeRequest;
import com.example.fintech_java.entity.TradeTransaction;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.fintech_java.service.TradeService;

import java.util.List;

@RestController
@RequestMapping("/api/trades")
@RequiredArgsConstructor
public class TradeController {

    private final TradeService tradeService;

    @PostMapping
    public ResponseEntity<Void> trade(@RequestBody TradeRequest request) {
        tradeService.executeTrade(1L, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/history")
    public List<TradeTransaction> history() {
        return tradeService.getTradeHistory(1L);
    }
}

