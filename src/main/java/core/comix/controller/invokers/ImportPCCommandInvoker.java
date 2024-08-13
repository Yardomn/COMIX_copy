package core.comix.controller.invokers;

import core.comix.controller.CommandInputException;
import core.comix.controller.Invoker;
import core.comix.controller.commands.ExportPCCommand;
import core.comix.controller.commands.ImportPCCommand;

public class ImportPCCommandInvoker extends Invoker {
    private ImportPCCommand importPCCommand;

    public ImportPCCommandInvoker(ImportPCCommand command) {
        importPCCommand = command;
    }

    public void executeCommand(String input) throws CommandInputException {
        if (input.startsWith("csv") | input.startsWith("json") | input.startsWith("xml")){
            importPCCommand.execute(input.split(" ")[0], input.split(" ")[1]);
            System.out.println("Personal Collection imported.");
        } else {
            throw new CommandInputException("Command <importpc> only takes inputs 'csv' 'json' or 'xml'");
        }
    }

    public static String getHelpString() {
        return "--IMPORTPC command--\nimportpc [file type]\n";
    }
}