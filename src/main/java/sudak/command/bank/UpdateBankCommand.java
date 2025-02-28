package sudak.command.bank;

import lombok.AllArgsConstructor;
import sudak.command.Command;
import sudak.model.Bank;
import sudak.service.BankService;

import java.math.BigDecimal;

@AllArgsConstructor
public class UpdateBankCommand implements Command {

    private final BankService bankService;

    @Override
    public String getName() {
        return "updateBank";
    }

    @Override
    public String getDescription() {
        return "Update an existing bank: updateBank <id> <name> <commission_individual> <commission_legal>";
    }

    @Override
    public void execute(String[] params) {
        if (params.length != 4) {
            System.out.println("Usage: " + getDescription());
            return;
        }
        try {
            Long id = Long.parseLong(params[0]);
            String name = params[1];
            BigDecimal commInd = new BigDecimal(params[2]);
            BigDecimal commLeg = new BigDecimal(params[3]);
            Bank bank = new Bank(id, name, commInd, commLeg);
            bankService.update(bank);
            System.out.println("Bank updated successfully.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
