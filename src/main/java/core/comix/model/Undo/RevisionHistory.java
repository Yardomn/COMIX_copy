package core.comix.model.Undo;

import java.util.LinkedList;

import core.comix.controller.CommandInputException;
import core.comix.controller.UndoableCommands;
import core.comix.model.comic.Comic;

public class RevisionHistory {
    private static RevisionHistory INSTANCE;
    private static LinkedList<Revisions> undoHistory = new LinkedList<Revisions>();
    private static LinkedList<Revisions> redoHistory = new LinkedList<Revisions>();

    public static RevisionHistory getRevisionHistory(){
        if(INSTANCE == null){
            INSTANCE = new RevisionHistory();
        }
        return INSTANCE;
    }

    public static void addUndoRevision(Comic[] comic, UndoableCommands originator){
        undoHistory.add(0, originator.createRevision(comic));
    }
    public static void addRedoRevision(Comic[] comic, UndoableCommands originator){
        redoHistory.add(0, originator.createRevision(comic));
    }
    public static void removeUndoRevision(int index){
        if(undoHistory.size() != 0){
            undoHistory.remove(index);
        }
    }
    public static void removeRedoRevision(int index){
        if(redoHistory.size() != 0){
            redoHistory.remove(index);
        }
    }
    public static LinkedList<Revisions> getUndoHistory(){
        return undoHistory;
    }
    public static LinkedList<Revisions> getRedoHistory(){
        return redoHistory;
    }
    public static void setUndoRevision() throws CommandInputException{
        Revisions revision = undoHistory.removeFirst();
        revision.setUndoRevision();
    }
    public static void setRedoRevision() throws CommandInputException{
        Revisions revision = redoHistory.removeFirst();
        revision.setRedoRevision();
    }
}
