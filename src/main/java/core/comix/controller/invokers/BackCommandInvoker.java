package core.comix.controller.invokers;
import java.io.IOException;

import core.comix.controller.CommandInputException;
import core.comix.controller.commands.BackCommand;

import core.comix.controller.Invoker;

public class BackCommandInvoker extends Invoker{
    private BackCommand backCommand;
    public BackCommandInvoker(BackCommand command){
        backCommand = command;
    }
    public void executeCommand(String input) throws CommandInputException, IOException{
        if(input.length()>0){
            throw new CommandInputException("Command <back> does not take any arguements");
        } else{
            backCommand.execute();
        }
    }

    public static String getHelpString() {
        return "--BACK command--\nback\n";
    }
}
