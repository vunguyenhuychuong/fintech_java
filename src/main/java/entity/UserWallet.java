package entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
public class UserWallet {
    @Id
    private Long userId;

    private BigDecimal usdtBalance;
    private BigDecimal btcBalance;
    private BigDecimal ethBalance;

    public void addCrypto(String symbol, BigDecimal quantity) {
        if ("BTCUSDT".equals(symbol)) {
            btcBalance = btcBalance.add(quantity);
        } else if ("ETHUSDT".equals(symbol)) {
            ethBalance = ethBalance.add(quantity);
        } else {
            throw new IllegalArgumentException("Unsupported symbol");
        }
    }

    public void subtractCrypto(String symbol, BigDecimal quantity) {
        if ("BTCUSDT".equals(symbol)) {
            if (btcBalance.compareTo(quantity) < 0) {
                throw new RuntimeException("Insufficient BTC");
            }
            btcBalance = btcBalance.subtract(quantity);
        } else if ("ETHUSDT".equals(symbol)) {
            if (ethBalance.compareTo(quantity) < 0) {
                throw new RuntimeException("Insufficient ETH");
            }
            ethBalance = ethBalance.subtract(quantity);
        } else {
            throw new IllegalArgumentException("Unsupported symbol");
        }
    }
}
