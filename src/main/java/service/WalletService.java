package service;

import entity.UserWallet;

public interface WalletService {

    UserWallet getWallet(Long userId);
}

