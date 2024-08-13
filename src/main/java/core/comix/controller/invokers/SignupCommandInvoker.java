package core.comix.controller.invokers;

import core.comix.controller.CommandInputException;
import core.comix.controller.Invoker;
import core.comix.controller.commands.SignupCommand;
import core.comix.model.user.CurrentUser;

public class SignupCommandInvoker extends Invoker {
    private SignupCommand signupCommand;

    public SignupCommandInvoker(SignupCommand command) {
        signupCommand = command;
    }
    
    public void executeCommand(String input) throws CommandInputException
    {
        String[] splits = input.split(" ");
        if (splits.length < 2) {
            throw new CommandInputException("Command <signup> requires two arguments.");
        }
        try {
            signupCommand.execute(splits[0], splits[1]);
        }
        catch (CommandInputException e) {
            throw e;
        }
        System.out.println("Signed up as a new user: " + CurrentUser.getUsername());
    }

    public static String getHelpString() {
        return "--SIGNUP command--\nsignup <username> <password>\n";
    }
}
