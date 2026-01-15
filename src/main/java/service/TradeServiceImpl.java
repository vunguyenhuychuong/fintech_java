package service;

import dto.TradeRequest;
import entity.AggregatedPrice;
import entity.TradeTransaction;
import entity.UserWallet;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import repository.AggregatedPriceRepository;
import repository.TradeTransactionRepository;
import repository.UserWalletRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TradeServiceImpl implements TradeService {

    private final UserWalletRepository walletRepository;
    private final AggregatedPriceRepository priceRepository;
    private final TradeTransactionRepository tradeRepository;

    @Transactional
    @Override
    public void executeTrade(Long userId, TradeRequest request) {

        UserWallet wallet = walletRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        AggregatedPrice price = priceRepository
                .findFirstBySymbolOrderByUpdatedAtDesc(request.getSymbol())
                .orElseThrow(() -> new RuntimeException("Price not available"));

        if ("BUY".equals(request.getSide())) {
            buy(wallet, price, request);
        } else {
            sell(wallet, price, request);
        }

        walletRepository.save(wallet);
        tradeRepository.save(buildTransaction(userId, price, request));
    }

    private void buy(UserWallet wallet, AggregatedPrice price, TradeRequest req) {
        BigDecimal cost = price.getBestAsk()
                .multiply(req.getQuantity());

        if (wallet.getUsdtBalance().compareTo(cost) < 0) {
            throw new RuntimeException("Insufficient USDT");
        }

        wallet.setUsdtBalance(wallet.getUsdtBalance().subtract(cost));
        wallet.addCrypto(req.getSymbol(), req.getQuantity());
    }

    private void sell(UserWallet wallet, AggregatedPrice price, TradeRequest req) {
        wallet.subtractCrypto(req.getSymbol(), req.getQuantity());

        BigDecimal proceeds = price.getBestBid()
                .multiply(req.getQuantity());

        wallet.setUsdtBalance(wallet.getUsdtBalance().add(proceeds));
    }

    @Override
    public List<TradeTransaction> getTradeHistory(Long userId) {
        return tradeRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    private TradeTransaction buildTransaction(
            Long userId,
            AggregatedPrice price,
            TradeRequest request) {

        TradeTransaction tx = new TradeTransaction();
        tx.setUserId(userId);
        tx.setSymbol(request.getSymbol());
        tx.setSide(request.getSide());
        tx.setQuantity(request.getQuantity());
        tx.setCreatedAt(LocalDateTime.now());

        if ("BUY".equals(request.getSide())) {
            tx.setPrice(price.getBestAsk());
        } else {
            tx.setPrice(price.getBestBid());
        }

        return tx;
    }

}

