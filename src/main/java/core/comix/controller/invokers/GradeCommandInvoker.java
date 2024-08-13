package core.comix.controller.invokers;

import java.io.IOException;

import core.comix.controller.CommandInputException;
import core.comix.controller.Invoker;
import core.comix.controller.commands.GradeCommand;

public class GradeCommandInvoker extends Invoker{

    private GradeCommand gradeCommand;

    public GradeCommandInvoker(GradeCommand command) {
        gradeCommand = command;
    }

    
    public void executeCommand(String input) throws CommandInputException, NumberFormatException, IOException
    {
        String[] inputList = input.split(" ");
        if(inputList.length == 1){ //input expected as "{grade}". So "9".
            if (inputList[0].matches("\\d+")) {
                int grade = Integer.parseInt(input);
                if(grade >= 1 && grade <= 10){
                    gradeCommand.execute(Integer.parseInt(input));
                } else {
                    throw new CommandInputException("The grade score should be a number between 1-10");
                }
            } else {
                throw new CommandInputException("Expecting \"grade <rating>\" syntax for grade command where grade score is a number 1-10");
            }
        } else{
            throw new CommandInputException("Expecting \"grade <rating>\" syntax for grade command where grade score is a number 1-10");
        }
    }
    
    public static String getHelpString() {
        return "--GRADE command--\ngrade <rating>\n";
    }
}
