package sudak.command.account;

import lombok.AllArgsConstructor;
import sudak.command.Command;
import sudak.model.Account;
import sudak.service.AccountService;

@AllArgsConstructor
public class AddAccountCommand implements Command {

    private final AccountService accountService;

    @Override
    public String getName() {
        return "addAccount";
    }

    @Override
    public String getDescription() {
        return "Add a new account: addAccount <clientId> <bankId> <currency>";
    }

    @Override
    public void execute(String[] params) {
        if (params.length != 3) {
            System.out.println("Usage: " + getDescription());
            return;
        }
        try {
            long clientId = Long.parseLong(params[0]);
            long bankId = Long.parseLong(params[1]);
            String currency = params[2];
            Account account = new Account(clientId, bankId, currency);
            accountService.create(account);
            System.out.println("Account added successfully.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
