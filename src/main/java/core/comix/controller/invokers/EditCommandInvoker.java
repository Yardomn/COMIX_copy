package core.comix.controller.invokers;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import core.comix.controller.CommandInputException;
import core.comix.controller.Invoker;
import core.comix.controller.commands.EditCommand;


public class EditCommandInvoker extends Invoker{
    
    private EditCommand editCommand;

    public EditCommandInvoker(EditCommand command) {
        editCommand = command;
    }

    public void executeCommand(String input) throws CommandInputException, IOException
    {
        String[] fieldTypeArr = new String[]{"issueNum", "publicationDate", "principleCharacters", "creators", "description", "value"};
        List<String> fieldTypeList = Arrays.asList(fieldTypeArr);

        String[] inputList = input.split(" ");
        String fieldType = inputList[0];
        String newField = input.substring(fieldType.length()+1);
        
        if(fieldTypeList.contains(fieldType)){ //input expected as "{fieldType} {newField}". So "value 15".
            editCommand.execute(fieldType, newField);
        } else{
            throw new CommandInputException("Command <edit> requires you to input a field type and new value.\n Usage: edit <fieldType> <newValue>");
        }
    }

    public static String getHelpString() {
        return "--EDIT command--\nedit <fieldType> <newValue>\n";
    }
}
