package sudak.command.client;

import lombok.AllArgsConstructor;
import sudak.command.Command;
import sudak.model.Client;
import sudak.model.ClientType;
import sudak.service.ClientService;

@AllArgsConstructor
public class UpdateClientCommand implements Command {

    private final ClientService clientService;

    @Override
    public String getName() {
        return "updateClient";
    }

    @Override
    public String getDescription() {
        return "Update client information: updateClient <id> <type(individual|legal)> <name>";
    }

    @Override
    public void execute(String[] params) {
        if (params.length != 3) {
            System.out.println("Usage: " + getDescription());
            return;
        }
        try {
            Long id = Long.parseLong(params[0]);
            ClientType type = ClientType.valueOf(params[1].toUpperCase());
            String name = params[2];
            Client client = new Client(id, type, name);
            clientService.update(client);
            System.out.println("Client updated successfully.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
