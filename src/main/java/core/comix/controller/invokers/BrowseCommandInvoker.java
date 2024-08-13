package core.comix.controller.invokers;
import java.io.IOException;

import core.comix.controller.CommandInputException;
import core.comix.controller.Invoker;
import core.comix.controller.commands.BrowseCollectionCommand;
import core.comix.controller.commands.BrowseIntoCommand;


public class BrowseCommandInvoker extends Invoker {

    private BrowseCollectionCommand bcCommand;
    private BrowseIntoCommand biCommand;

    public BrowseCommandInvoker(BrowseCollectionCommand bcCommand, BrowseIntoCommand biCommand) {
		this.bcCommand = bcCommand;
        this.biCommand = biCommand;
	}
    
    
    public void executeCommand(String input) throws CommandInputException, IOException
    {
        try{
            biCommand.execute(Integer.parseInt(input));
        } catch(NumberFormatException nfe){
            if(input.toLowerCase().equals("personal collection") || input.toLowerCase().equals("pc")){
                bcCommand.execute(input.toLowerCase());
            } else if(input.toLowerCase().equals("database") || input.toLowerCase().equals("full collection") || input.toLowerCase().equals("db")){
                bcCommand.execute(input.toLowerCase());
            } else{
                throw new CommandInputException("Command <browse> onlys takes collection indecies, 'personal collection', and 'full collection' as input");
            }
        }
    }

    public static String getHelpString() {
        return "--BROWSE command--\nbrowse <personal collection | full collection>\nbrowse <index>\n";
    }
}
