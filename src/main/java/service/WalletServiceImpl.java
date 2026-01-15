package service;

import entity.UserWallet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import repository.UserWalletRepository;

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

