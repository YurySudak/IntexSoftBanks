package sudak.command.bank;

import sudak.command.Command;
import sudak.model.Bank;
import sudak.service.BankService;

import java.math.BigDecimal;

public class AddBankCommand implements Command {
    private final BankService bankService;

    public AddBankCommand(BankService bankService) {
        this.bankService = bankService;
    }

    @Override
    public String getName() {
        return "addBank";
    }

    @Override
    public String getDescription() {
        return "Add a new bank: addBank <name> <commission_individual> <commission_legal>";
    }

    @Override
    public void execute(String[] params) {
        if (params.length != 3) {
            System.out.println("Usage: " + getDescription());
            return;
        }
        try {
            String name = params[0];
            BigDecimal commInd = new BigDecimal(params[1]);
            BigDecimal commLeg = new BigDecimal(params[2]);
            Bank bank = new Bank(name, commInd, commLeg);
            bankService.create(bank);
            System.out.println("Bank added successfully.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
