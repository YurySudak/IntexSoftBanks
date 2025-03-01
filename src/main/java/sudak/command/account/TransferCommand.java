package sudak.command.account;

import lombok.AllArgsConstructor;
import sudak.command.Command;
import sudak.service.AccountService;

import java.math.BigDecimal;

@AllArgsConstructor
public class TransferCommand implements Command {

    private final AccountService accountService;

    @Override
    public String getName() {
        return "transfer";
    }

    @Override
    public String getDescription() {
        return "Transfer money between accounts: transfer <fromId> <toId> <amount>";
    }

    @Override
    public void execute(String[] params) {
        if (params.length != 3) {
            System.out.println("Usage: " + getDescription());
            return;
        }
        try {
            Long fromId = Long.parseLong(params[0]);
            Long toId = Long.parseLong(params[1]);
            BigDecimal amount = new BigDecimal(params[2]);
            accountService.transfer(fromId, toId, amount);
            System.out.println("Transfer completed successfully.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
