package entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
public class TradeTransaction {

    @Id
    @GeneratedValue
    private Long id;

    private Long userId;
    private String symbol;
    private String side;          // BUY / SELL
    private BigDecimal price;
    private BigDecimal quantity;
    private LocalDateTime createdAt;
}
