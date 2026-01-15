package com.example.fintech_java.service;

import com.example.fintech_java.entity.UserWallet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.fintech_java.repository.UserWalletRepository;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final UserWalletRepository walletRepository;

    @Override
    public UserWallet getWallet(Long userId) {
        return walletRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));
    }
}

