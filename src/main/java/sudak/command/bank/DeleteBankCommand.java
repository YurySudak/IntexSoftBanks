package sudak.command.bank;

import lombok.AllArgsConstructor;
import sudak.command.Command;
import sudak.service.BankService;

@AllArgsConstructor
public class DeleteBankCommand implements Command {

    private final BankService bankService;

    @Override
    public String getName() {
        return "deleteBank";
    }

    @Override
    public String getDescription() {
        return "Delete an existing bank: deleteBank <id>";
    }

    @Override
    public void execute(String[] params) {
        if (params.length != 1) {
            System.out.println("Usage: " + getDescription());
            return;
        }
        try {
            Long id = Long.parseLong(params[0]);
            bankService.delete(id);
            System.out.println("Bank deleted successfully.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
