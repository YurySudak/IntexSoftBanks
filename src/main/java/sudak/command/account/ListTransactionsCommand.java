package sudak.command.account;

import lombok.AllArgsConstructor;
import sudak.command.Command;
import sudak.service.AccountService;

@AllArgsConstructor
public class ListTransactionsCommand implements Command {

    private final AccountService accountService;

    @Override
    public String getName() {
        return "listTransactions";
    }

    @Override
    public String getDescription() {
        return "List all transactions for an account: listTransactions <accountId>";
    }

    @Override
    public void execute(String[] params) {
        if (params.length != 1) {
            System.out.println("Usage: " + getDescription());
            return;
        }
        try {
            long accountId = Long.parseLong(params[0]);
            accountService.getTransactions(accountId).forEach(transaction -> {
                System.out.printf("Transaction id: %d, amount: %.2f, commission: %.2f, from account: %d, to account: %d, currency: %s, timestamp: %s\n",
                        transaction.getId(),
                        transaction.getAmount(),
                        transaction.getCommission(),
                        transaction.getFromAccountId(),
                        transaction.getToAccountId(),
                        transaction.getCurrency(),
                        transaction.getTimestamp());

            });
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
