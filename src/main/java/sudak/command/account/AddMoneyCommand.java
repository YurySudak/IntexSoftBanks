package sudak.command.account;

import lombok.AllArgsConstructor;
import sudak.command.Command;
import sudak.model.Account;
import sudak.service.AccountService;

import java.math.BigDecimal;

@AllArgsConstructor
public class AddMoneyCommand implements Command {

    private final AccountService accountService;

    @Override
    public String getName() {
        return "addMoney";
    }

    @Override
    public String getDescription() {
        return "Add money to an account: addMoney <id> <amount>";
    }

    @Override
    public void execute(String[] params) {
        if (params.length != 2) {
            System.out.println("Usage: " + getDescription());
            return;
        }
        try {
            Long id = Long.parseLong(params[0]);
            BigDecimal amount = new BigDecimal(params[1]);
            accountService.addMoney(id, amount);
            System.out.println("Money added successfully.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
