package core.comix.controller.invokers;

import java.io.IOException;

import core.comix.controller.CommandInputException;
import core.comix.controller.Invoker;
import core.comix.controller.commands.ScrollCommand;

public class ScrollCommandInvoker extends Invoker{

    private ScrollCommand scrollCommand;

    public ScrollCommandInvoker(ScrollCommand command) {
        scrollCommand = command;
    }

    
    public void executeCommand(String input) throws CommandInputException, IOException
    {
        if(input.equals("up")){
            scrollCommand.execute(true);
        } else if(input.equals("down")){
            scrollCommand.execute(false);
        } else{
            throw new CommandInputException("Command <scroll> only takes inputs 'up' and 'down'");
        }

    }

    public static String getHelpString() {
        return "--SCROLL command--\nscroll <up | down>\n";
    }
}
