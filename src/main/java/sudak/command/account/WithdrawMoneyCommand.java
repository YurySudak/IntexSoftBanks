package sudak.command.account;

import lombok.AllArgsConstructor;
import sudak.command.Command;
import sudak.service.AccountService;

import java.math.BigDecimal;

@AllArgsConstructor
public class WithdrawMoneyCommand implements Command {

    private final AccountService accountService;

    @Override
    public String getName() {
        return "withdrawMoney";
    }

    @Override
    public String getDescription() {
        return "Withdraw money from an account: withdrawMoney <id> <amount>";
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
            accountService.withdrawMoney(id, amount);
            System.out.println("Money withdrawn successfully.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
