package core.comix.controller.invokers;

import java.io.IOException;

import core.comix.controller.CommandInputException;
import core.comix.controller.Invoker;
import core.comix.controller.commands.RemoveCommand;

public class RemoveCommandInvoker extends Invoker{

    private RemoveCommand removeCommand;
    
    public RemoveCommandInvoker(RemoveCommand command) {
        removeCommand = command;
    }

    
    public void executeCommand(String input) throws CommandInputException, IOException
    {
        if (input.length() > 0) {
            throw new CommandInputException("Command <remove> takes no arguments.");
        }
        removeCommand.execute();
    }

    public static String getHelpString() {
        return "--REMOVE command--\nremove\n";
    }
}
