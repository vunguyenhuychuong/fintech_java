package com.example.fintech_java.config;

import com.example.fintech_java.entity.UserWallet;
import com.example.fintech_java.repository.UserWalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final UserWalletRepository walletRepository;

    @Bean
    CommandLineRunner initWallet() {
        return args -> {
            Long userId = 1L;

            if (!walletRepository.existsById(userId)) {
                UserWallet wallet = new UserWallet();
                wallet.setUserId(userId);
                wallet.setUsdtBalance(new BigDecimal("50000"));
                wallet.setBtcBalance(BigDecimal.ZERO);
                wallet.setEthBalance(BigDecimal.ZERO);

                walletRepository.save(wallet);
            }
        };
    }
}
