package core.comix.controller.invokers;

import core.comix.controller.CommandInputException;
import core.comix.controller.Invoker;
import core.comix.controller.commands.ExportDBCommand;
import core.comix.model.user.CurrentUser;

public class ExportDBCommandInvoker extends Invoker {
    private ExportDBCommand exportCommand;

    public ExportDBCommandInvoker(ExportDBCommand command) {
        exportCommand = command;
    }

    public void executeCommand(String input) throws CommandInputException {
        String[] splits = input.split(" ");
        if (splits.length < 2) {
            throw new CommandInputException("Command <exportdb> requires two arguments.");
        }
        exportCommand.execute(splits[0], splits[1]);

        System.out.println("Database saved to: " + splits[1]);
    }

    public static String getHelpString() {
        return "--EXPORTDB command--\nexportdb [file type] [file path]\n";
    }
}