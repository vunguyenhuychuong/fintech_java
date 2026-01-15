package com.example.fintech_java.repository;

import com.example.fintech_java.entity.AggregatedPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AggregatedPriceRepository  extends JpaRepository<AggregatedPrice, Long> {

    Optional<AggregatedPrice> findFirstBySymbolOrderByUpdatedAtDesc(String symbol);

    List<AggregatedPrice> findBySymbolIn(List<String> symbols);
}
