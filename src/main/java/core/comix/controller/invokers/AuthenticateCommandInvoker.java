package core.comix.controller.invokers;

import java.io.IOException;

import core.comix.controller.CommandInputException;
import core.comix.controller.Invoker;
import core.comix.controller.commands.AuthenticateCommand;

public class AuthenticateCommandInvoker extends Invoker{

    private AuthenticateCommand authenticateCommand;

    public AuthenticateCommandInvoker(AuthenticateCommand command) {
        authenticateCommand = command;
    }

    public void executeCommand(String input) throws CommandInputException, IOException
    {
        authenticateCommand.execute(input);
    }
    
    public static String getHelpString() {
        return "--AUTHENTICATE command--\nauthenticate <signee name>\n";
    }
}
