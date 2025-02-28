package sudak.command;

public interface Command {
    String getName();
    String getDescription();
    void execute(String[] params);
}
