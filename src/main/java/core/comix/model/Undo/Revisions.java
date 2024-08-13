package core.comix.model.Undo;
import core.comix.controller.CommandInputException;
import core.comix.controller.UndoableCommands;
import core.comix.model.comic.Comic;

public abstract class Revisions {
    private Comic[] revisedComic;
    private UndoableCommands originator;
    
    public Revisions(Comic[] revisedComic, UndoableCommands originator){
        this.revisedComic = revisedComic;
        this.originator = originator;
    }
    public abstract Comic[] getRevised();
    public abstract void setUndoRevision() throws CommandInputException;
    public abstract void setRedoRevision() throws CommandInputException;
}
