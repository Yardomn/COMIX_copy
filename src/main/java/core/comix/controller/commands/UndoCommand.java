package core.comix.controller.commands;

import java.util.NoSuchElementException;

import core.comix.controller.Command;
import core.comix.controller.CommandInputException;
import core.comix.model.Undo.RevisionHistory;
import core.comix.view.UI;

public class UndoCommand extends Command{

    public UndoCommand(UI ui) {
        super(ui);
    }
    public void execute() throws CommandInputException{
        try {
            RevisionHistory.setUndoRevision();
        } catch (NoSuchElementException e) {
            throw new CommandInputException("Undo Stack is empty.");
        }
    }
    
}
