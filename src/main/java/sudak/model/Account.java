package sudak.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Random;

@Data
@AllArgsConstructor
public class Account {
    private Long id;
    private String number;
    private Long clientId;
    private Long bankId;
    private String currency;
    private BigDecimal balance;

    public Account(Long clientId, Long bankId, String currency) {
        this.clientId = clientId;
        this.bankId = bankId;
        this.currency = currency;
        this.balance = BigDecimal.ZERO;
        this.number = generateNumber();
    }

    public Account(Long bankId, String currency) {
        this.bankId = bankId;
        this.currency = currency;
        this.balance = BigDecimal.ZERO;
        this.number = generateNumber();
    }

    private String generateNumber() {
        return String.valueOf(new Random().nextInt(1000000000));
    }
}
