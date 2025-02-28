package sudak.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Bank {
    private Long id;
    private String name;
    private BigDecimal commissionIndividual;
    private BigDecimal commissionLegal;

    public Bank(String name, BigDecimal commissionIndividual, BigDecimal commissionLegal) {
        this.name = name;
        this.commissionIndividual = commissionIndividual;
        this.commissionLegal = commissionLegal;
    }
}
