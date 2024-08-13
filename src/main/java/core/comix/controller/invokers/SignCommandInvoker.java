package core.comix.controller.invokers;

import java.io.IOException;

import core.comix.controller.CommandInputException;
import core.comix.controller.Invoker;
import core.comix.controller.commands.SignCommand;

public class SignCommandInvoker extends Invoker{

    private SignCommand signCommand;

    public SignCommandInvoker(SignCommand command) {
        signCommand = command;
    }

    public void executeCommand(String input) throws CommandInputException, IOException
    {
        signCommand.execute(input);
    }
    
    public static String getHelpString() {
        return "--SIGN command--\nsign <signee name>\n";
    }
}
