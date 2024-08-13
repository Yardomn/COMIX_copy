package core.comix.controller.invokers;

import core.comix.controller.CommandInputException;
import core.comix.controller.Invoker;
import core.comix.controller.commands.HelpCommand;

public class HelpCommandInvoker extends Invoker {
    private HelpCommand helpCommand;

    public HelpCommandInvoker(HelpCommand command) {
        helpCommand = command;
    }
    
    public void executeCommand(String input) throws CommandInputException
    {
        if (input.length() > 0) {
            throw new CommandInputException("Command <help> takes no arguments.");
        }
        helpCommand.execute();
    }

    public static String getHelpString() {
        return "--HELP command--\nhelp\n";
    }
}
