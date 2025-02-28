package sudak.command.client;

import lombok.AllArgsConstructor;
import sudak.command.Command;
import sudak.service.ClientService;

@AllArgsConstructor
public class DeleteClientCommand implements Command {

    private final ClientService clientService;

    @Override
    public String getName() {
        return "deleteClient";
    }

    @Override
    public String getDescription() {
        return "Delete a client: deleteClient <id>";
    }

    @Override
    public void execute(String[] params) {
        if (params.length != 1) {
            System.out.println("Usage: " + getDescription());
            return;
        }
        try {
            long id = Long.parseLong(params[0]);
            clientService.delete(id);
            System.out.println("Client deleted successfully.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
