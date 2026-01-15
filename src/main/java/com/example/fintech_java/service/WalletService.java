package com.example.fintech_java.service;

import com.example.fintech_java.entity.UserWallet;

public interface WalletService {

    UserWallet getWallet(Long userId);
}

