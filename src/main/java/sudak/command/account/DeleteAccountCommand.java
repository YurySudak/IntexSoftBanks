package sudak.command.account;

import lombok.AllArgsConstructor;
import sudak.command.Command;
import sudak.service.AccountService;

@AllArgsConstructor
public class DeleteAccountCommand implements Command {

    private final AccountService accountService;

    @Override
    public String getName() {
        return "deleteAccount";
    }

    @Override
    public String getDescription() {
        return "Delete an account: deleteAccount <id>";
    }

    @Override
    public void execute(String[] params) {
        if (params.length != 1) {
            System.out.println("Usage: " + getDescription());
            return;
        }
        try {
            Long id = Long.parseLong(params[0]);
            accountService.delete(id);
            System.out.println("Account deleted successfully.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
