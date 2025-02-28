package sudak.command.client;

import lombok.AllArgsConstructor;
import sudak.command.Command;
import sudak.model.Account;
import sudak.model.Client;
import sudak.model.ClientType;
import sudak.service.AccountService;
import sudak.service.ClientService;

@AllArgsConstructor
public class AddClientCommand implements Command {

    private final ClientService clientService;
    private final AccountService accountService;

    @Override
    public String getName() {
        return "addClient";
    }

    @Override
    public String getDescription() {
        return "Add a new client: addClient <type(individual|legal)> <name> <bankId> <newAccountCurrency>";
    }

    @Override
    public void execute(String[] params) {
        if (params.length != 4) {
            System.out.println("Usage: " + getDescription());
            return;
        }
        try {
            ClientType type = ClientType.valueOf(params[0].toUpperCase());
            String name = params[1];
            Long bankId = Long.parseLong(params[2]);
            String newAccountCurrency = params[3];
            Client client = new Client(type, name);
            Account account = new Account(bankId, newAccountCurrency);
            clientService.createNewClient(client, account);
            System.out.println("Client added successfully.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
