import java.util.stream.Collectors;
import java.util.HashMap;

// From https://en.wikipedia.org/wiki/Command_pattern#Java

public final class CommandFactory {
    private final HashMap<String, Command> commands;

    private CommandFactory() {
        commands = new HashMap<String, Command>();
    }

    public void addCommand(final String name, final Command command) {
        commands.put(name, command);
    }

    public void executeCommand(String name) {
        if (commands.containsKey(name))
            commands.get(name).apply();
    }

    public void listCommands() {
        System.out.println(
                "Enabled commands: "
                        + commands.keySet().stream().collect(Collectors.joining(", ")));
    }

    public static CommandFactory init() {
        final CommandFactory cf = new CommandFactory();

        return cf;
    }
}