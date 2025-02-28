package sudak.command.bank;

import lombok.AllArgsConstructor;
import sudak.command.Command;
import sudak.model.Bank;
import sudak.service.BankService;

import java.util.List;

@AllArgsConstructor
public class ListBanksCommand implements Command {

    private BankService bankService;

    @Override
    public String getName() {
        return "listBanks";
    }

    @Override
    public String getDescription() {
        return "List all banks";
    }

 @Override
 public void execute(String[] params) {
     List<Bank> banks = bankService.getAll();
     if (banks.isEmpty()) {
         System.out.println("No banks available.");
     } else {
         for (Bank bank : banks) {
             System.out.printf("Bank Id: %d, Name: %s, Individual Commission: %s, Legal Commission: %s%n",
                     bank.getId(),
                     bank.getName(),
                     bank.getCommissionIndividual(),
                     bank.getCommissionLegal());
         }
     }
 }
}
