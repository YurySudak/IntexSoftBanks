package sudak.command.account;

import lombok.AllArgsConstructor;
import sudak.command.Command;
import sudak.service.AccountService;

@AllArgsConstructor
public class ListAccountsCommand implements Command {

    private final AccountService accountService;

    @Override
    public String getName() {
        return "listAccounts";
    }

    @Override
    public String getDescription() {
        return "List all accounts";
    }

    @Override
    public void execute(String[] params) {
        accountService.getAll().forEach(account -> {
            System.out.printf("Account Id: %d, Number: %s, Client Id: %d, Bank Id: %d, Balance: %f, Currency: %s%n",
                    account.getId(),
                    account.getNumber(),
                    account.getClientId(),
                    account.getBankId(),
                    account.getBalance(),
                    account.getCurrency());
        });
    }
}
