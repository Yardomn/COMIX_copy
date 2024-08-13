package core.comix.model.Undo;

import core.comix.controller.CommandInputException;
import core.comix.controller.UndoableCommands;
import core.comix.model.comic.Comic;

public class MarkupRevision extends Revisions{
    private Comic[] revisedComic;
    private UndoableCommands originator;
    
    public MarkupRevision(Comic[] comic, UndoableCommands originator){
        super(comic, originator);
        this.revisedComic = comic;
        this.originator = originator;
    }
    @Override
    public Comic[] getRevised() {
        return revisedComic;
    }
    @Override
    public void setUndoRevision() throws CommandInputException {
        originator.undo(this);
    }
    @Override
    public void setRedoRevision() throws CommandInputException {
        originator.redo(this);
    }
    
}
