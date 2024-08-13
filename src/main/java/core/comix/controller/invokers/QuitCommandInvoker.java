package core.comix.controller.invokers;

import core.comix.controller.CommandInputException;
import core.comix.controller.Invoker;
import core.comix.controller.commands.QuitCommand;

public class QuitCommandInvoker extends Invoker{

    private QuitCommand quitCommand;

    public QuitCommandInvoker(QuitCommand command) {
        quitCommand = command;
    }

    
    public void executeCommand(String input) throws CommandInputException
    {
        if (input.length() > 0) {
            throw new CommandInputException("Command <quit> takes no arguments.");
        }
        quitCommand.execute();
    }

    public static String getHelpString() {
        return "--QUIT command--\nquit\n";
    }
}
