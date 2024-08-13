package core.comix.controller.invokers;

import java.io.IOException;

import core.comix.controller.CommandInputException;
import core.comix.controller.Invoker;
import core.comix.controller.commands.SearchCommand;

public class SearchCommandInvoker extends Invoker {

    private SearchCommand searchCommand;

    public SearchCommandInvoker(SearchCommand command) 
    {
        searchCommand = command;
    }

    public void executeCommand(String input) throws CommandInputException, IOException 
    {
        searchCommand.execute(input);
    }

    public static String getHelpString() {
        return "--SEARCH command--\nsearch\n";
    }
}
