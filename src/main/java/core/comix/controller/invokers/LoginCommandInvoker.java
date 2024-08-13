package core.comix.controller.invokers;

import core.comix.controller.CommandInputException;
import core.comix.controller.Invoker;
import core.comix.controller.commands.LoginCommand;
import core.comix.model.user.CurrentUser;

public class LoginCommandInvoker extends Invoker {

    private LoginCommand loginCommand;

    public LoginCommandInvoker(LoginCommand command) {
        loginCommand = command;
    }
    
    public void executeCommand(String input) throws CommandInputException
    {
        String[] splits = input.split(" ");
        if (splits.length < 2) {
            throw new CommandInputException("Command <login> requires two arguments.");
        }
        try {
            loginCommand.execute(splits[0], splits[1]);
        }
        catch (CommandInputException e) {
            throw e;
        }
        System.out.println("Logged in as: " + CurrentUser.getUsername());
    }

    public static String getHelpString() {
        return "--LOGIN command--\nlogin <username> <password>\n";
    }
    
}
