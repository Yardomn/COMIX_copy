package core.comix.controller.invokers;

import java.io.IOException;

import core.comix.controller.CommandInputException;
import core.comix.controller.Invoker;
import core.comix.controller.commands.AddCommand;

public class AddCommandInvoker extends Invoker
{
    private AddCommand addCommand;

    public AddCommandInvoker(AddCommand command) {
        addCommand = command;
    }
    
    public void executeCommand(String input) throws CommandInputException, IOException
    {
        if (input.length() > 0) {
            throw new CommandInputException("Command <add> takes no arguments.");
        }
        addCommand.execute();
    }

    public static String getHelpString() {
        return "--ADD command--\nadd\n";
    }
}
