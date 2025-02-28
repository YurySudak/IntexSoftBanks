package sudak.command.client;

import lombok.AllArgsConstructor;
import sudak.command.Command;
import sudak.model.Client;
import sudak.service.ClientService;

import java.util.List;

@AllArgsConstructor
public class ListClientsCommand implements Command {

    private final ClientService clientService;

    @Override
    public String getName() {
        return "listClients";
    }

    @Override
    public String getDescription() {
        return "List all clients";
    }

    @Override
    public void execute(String[] params) {
        List<Client> clients = clientService.getAll();
        for (Client client : clients) {
            System.out.printf("Client Id: %d, Name: %s, Type: %s%n",
                    client.getId(),
                    client.getName(),
                    client.getType());
        }
    }
}
