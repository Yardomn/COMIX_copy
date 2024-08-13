package core.comix.controller.invokers;

import java.io.IOException;

import core.comix.controller.CommandInputException;
import core.comix.controller.Invoker;
import core.comix.controller.commands.SlabCommand;

public class SlabCommandInvoker extends Invoker{

    private SlabCommand slabCommand;

    public SlabCommandInvoker(SlabCommand command) {
        slabCommand = command;
    }

    
    public void executeCommand(String input) throws CommandInputException, IOException
    {
        if(input == ""){ //input should be empty string, since initial input is "slab"
            slabCommand.execute();
        } else{
            throw new CommandInputException("Command <slab> takes no arguements");
        }
    }

    public static String getHelpString() {
        return "--SLAB command--\nslab\n";
    }
}
