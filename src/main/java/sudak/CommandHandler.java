package sudak;

import sudak.command.Command;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CommandHandler {
    private final Map<String, Command> commandMap;

    public CommandHandler() {
        this.commandMap = new HashMap<>();
        registerCommand(new HelpCommand(commandMap));
    }

    public void registerCommand(Command command) {
        commandMap.put(command.getName(), command);
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("========= Bank Management Application =========");
        System.out.println(" Type 'help' for commands help, 'exit' to quit.");
        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();
            if ("exit".equalsIgnoreCase(input)) {
                break;
            }
            if (input.isEmpty()) {
                continue;
            }
            String[] parts = input.split("\\s+");
            String commandName = parts[0];
            String[] params = new String[parts.length - 1];
            System.arraycopy(parts, 1, params, 0, params.length);

            Command command = commandMap.get(commandName);
            if (command != null) {
                command.execute(params);
            } else {
                System.out.println("Unknown command. Type 'help' for a list of commands.");
            }
        }
        scanner.close();
    }
}

class HelpCommand implements Command {
    private final Map<String, Command> commandMap;

    public HelpCommand(Map<String, Command> commandMap) {
        this.commandMap = commandMap;
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "Display all available commands";
    }

    @Override
    public void execute(String[] params) {
        System.out.println("Available commands:");
        commandMap.values().stream()
                .sorted(Comparator.comparing(Command::getName))
                .forEach(c -> System.out.printf("%s - %s%n", c.getName(), c.getDescription()));
    }
}
