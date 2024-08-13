package core.comix.controller.invokers;

import java.io.IOException;

import core.comix.controller.CommandInputException;
import core.comix.controller.Invoker;
import core.comix.controller.commands.SortTypeCommand;
import core.comix.model.sort.DefaultSort;
import core.comix.model.sort.PublicationSort;
import core.comix.model.sort.SortMethod;

public class SortTypeCommandInvoker extends Invoker{
    private SortTypeCommand sortTypeCommand;

    public SortTypeCommandInvoker(SortTypeCommand command) {
        sortTypeCommand = command;
    }

    /*
     * If user has entered a valid sort type, call the SortTypeCommand passing in that type. Else, throw error.
     *
     * input (String): String from the user specifying the sort type
     */
    public void executeCommand(String input) throws CommandInputException, IOException {
        SortMethod sortMethod;
        if (input.equals("default")){
            sortMethod = new DefaultSort();
        } else if (input.equals("publication")){
            sortMethod = new PublicationSort();
        } else {
            throw new CommandInputException("Command <sort-type> only takes 'default' and 'publication' as arguements");
        }
        sortTypeCommand.execute(sortMethod);
    }

    public static String getHelpString() {
        return "--SORT command--\nsort-type <default | publication>\n";
    }
}
