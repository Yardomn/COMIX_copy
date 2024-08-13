package core.comix.controller.invokers;

import core.comix.controller.CommandInputException;
import core.comix.controller.Invoker;
import core.comix.controller.commands.ExportDBCommand;
import core.comix.controller.commands.ImportDBCommand;

public class ImportDBCommandInvoker extends Invoker {
    private ImportDBCommand importDBCommand;

    public ImportDBCommandInvoker(ImportDBCommand command) {
        importDBCommand = command;
    }

    public void executeCommand(String input) throws CommandInputException {
        String[] splits = input.split(" ");
        if (splits.length < 2) {
            throw new CommandInputException("Command <importdb> requires two arguments.");
        }
        importDBCommand.execute(splits[0], splits[1]);

        System.out.println("Database imported from: " + splits[1]);
    }

    public static String getHelpString() {
        return "--IMPORTDB command--\nimportdb [file type] [filepath]\n";
    }
}