package core.comix.controller.commands;

import java.util.NoSuchElementException;

import core.comix.controller.Command;
import core.comix.controller.CommandInputException;
import core.comix.model.Undo.RevisionHistory;
import core.comix.view.UI;

public class RedoCommand extends Command{

    public RedoCommand(UI ui) {
        super(ui);
    }
    public void execute() throws CommandInputException{
        try {
            RevisionHistory.setRedoRevision();
        } catch (NoSuchElementException e) {
            throw new CommandInputException("Redo Stack is empty.");
        }
    }
    
}
