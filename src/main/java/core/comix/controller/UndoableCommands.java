package core.comix.controller;

import core.comix.model.Undo.Revisions;
import core.comix.model.comic.Comic;

public interface UndoableCommands {
    public void undo(Revisions revision) throws CommandInputException;
    public void redo(Revisions revision) throws CommandInputException;
    public Revisions createRevision(Comic[] comics);
}
