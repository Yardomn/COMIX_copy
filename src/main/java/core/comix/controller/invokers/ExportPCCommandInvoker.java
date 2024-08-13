package core.comix.controller.invokers;

import core.comix.controller.CommandInputException;
import core.comix.controller.Invoker;
import core.comix.controller.commands.ExportPCCommand;

public class ExportPCCommandInvoker extends Invoker {
    private ExportPCCommand exportCommand;

    public ExportPCCommandInvoker(ExportPCCommand command) {
        exportCommand = command;
    }

    public void executeCommand(String input) throws CommandInputException {
        if (input.startsWith("csv") | input.startsWith("json") | input.startsWith("xml")){
            exportCommand.execute(input.split(" ")[0], input.split(" ")[1]);
            System.out.println("Personal Collection exported.");
        } else {
            throw new CommandInputException("Command <exportpc> only takes inputs 'csv' 'json' or 'xml'");
        }
    }

    public static String getHelpString() {
        return "--EXPORTPC command--\nexportpc [file type]\n";
    }
}