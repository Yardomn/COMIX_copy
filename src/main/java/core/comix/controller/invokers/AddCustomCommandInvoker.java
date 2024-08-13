package core.comix.controller.invokers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import core.comix.controller.CommandInputException;
import core.comix.controller.Invoker;
import core.comix.controller.commands.AddCustomCommand;

public class AddCustomCommandInvoker extends Invoker{

    private AddCustomCommand customCommand;
    
    public AddCustomCommandInvoker(AddCustomCommand command) {
        customCommand = command;
    }

    public void executeCommand(String input) throws CommandInputException, IOException
    {
        HashMap<String, String> argValues = new HashMap<String, String>();
        String currArg = null;
        String currString = null;
        for (String s : input.split(" ")) {
            if (s.startsWith("--")) {
                argValues.put(currArg, currString);
                currArg = s.substring(2);
                currString = null;
            }
            else {
                if (currArg == null) {
                    throw new CommandInputException("Must provide a field for each part of a new comic.\n Usage: newcomic --publisher <publisherName> --series <seriesName> ...");
                }
                else {
                    if (currString == null) {
                        currString = s;
                    }
                    else {
                        currString += " " + s;
                    }
                }
            }
        }
        argValues.put(currArg, currString);

        HashSet<String> requiredFields = new HashSet<String>();
        requiredFields.add("publisher");
        requiredFields.add("series");
        requiredFields.add("volume");
        requiredFields.add("issue");
        requiredFields.add("publicationDate");
        requiredFields.add("title");

        for (String field : argValues.keySet()) {
            requiredFields.remove(field);
        }
        
        if (requiredFields.size() > 0) {
            throw new CommandInputException("Required to supply values for the fields: " + requiredFields.toString() + ".\n Example: --publisher <publisherName>");
        }

        if (!argValues.containsKey("description")) {
            argValues.put("description", "");
        }
        if (!argValues.containsKey("value")) {
            argValues.put("value", "0");
        }
        try {
            customCommand.execute(argValues.get("title"), argValues.get("publisher"), argValues.get("series"), Integer.parseInt(argValues.get("volume")), argValues.get("issue"), argValues.get("publicationDate"), new ArrayList<String>(), new ArrayList<String>(), argValues.get("description"), Float.parseFloat(argValues.get("value")));
        }
        catch (NumberFormatException nfe) {
            throw new CommandInputException("Parameters for volume number and value must all be numbers.");
        }
    }

    public static String getHelpString() {
        return "--ADD CUSTOM command--\nadd-custom --publisher <publisherName> --series <seriesName> --volume <volumeNum> --issue <issueNum> --publicationDate <pubNate> --title <title> \nOPTIONAL VALUES: --description <description> --value <value>\n";
    }
}
