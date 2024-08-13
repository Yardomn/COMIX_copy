package core.comix.controller.commands;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import core.comix.controller.Command;
import core.comix.controller.CommandInputException;
import core.comix.controller.UndoableCommands;
import core.comix.view.UI;
import core.comix.model.Undo.EditRevision;
import core.comix.model.Undo.RevisionHistory;
import core.comix.model.Undo.Revisions;
import core.comix.model.comic.Comic;
import core.comix.model.comic.DBComic;
import core.comix.model.user.CurrentUser;

public class EditCommand extends Command implements UndoableCommands{
    public EditCommand(UI ui){
        super(ui);
    }

    public void execute(String fieldType, String newField) throws CommandInputException {
        Comic comic = null;
        Comic[] revisedComics = new Comic[2];
        if(!(ui.getCurrentItem() instanceof DBComic)){
            comic = (Comic) ui.getCurrentItem();
            //Comic newComic = comic;
            revisedComics[0] = comic.copyToUserComic();
            if(fieldType.startsWith("issueNum")){
                comic.setIssueNum(newField);
            } else if(fieldType.startsWith("publicationDate")){
                comic.setPublicationDate(newField);                
            } else if(fieldType.startsWith("principleCharacters")){

                String[] arrPrincpChars = newField.split(" \\| ");
                ArrayList<String> arrListPrincpChars = new ArrayList<>(Arrays.asList(arrPrincpChars));
                comic.setPrincipleCharacters(arrListPrincpChars);

            } else if (fieldType.startsWith("creators")){

                String[] arrCreators = newField.split(" \\| ");
                ArrayList<String> arrListCreators = new ArrayList<>(Arrays.asList(arrCreators));
                comic.setCreators(arrListCreators);

            } else if(fieldType.startsWith("description")){
                comic.setDescription(newField);
            } else if(fieldType.startsWith("value")){
                try {
                    comic.setValue(Float.parseFloat(newField));
                }
                catch (NumberFormatException e) {
                    throw new CommandInputException("Value must be a number");   
                }
            } else {
                throw new CommandInputException("Command <edit> requires you to input a field type and new value.\n Usage: edit <fieldType> <newValue>");
            }
        } else{
            throw new CommandInputException("Cannot edit database comics");
        }
        revisedComics[1] = comic;
        RevisionHistory.addUndoRevision(revisedComics, this);
        ui.updateCurrentItem(comic);
        CurrentUser.getPersonalCollection().save();
    }

    public void undo(Revisions revision) {
        Comic copy = revision.getRevised()[1].copyToUserComic();
        revision.getRevised()[1].setIssueNum(revision.getRevised()[0].getIssueNum());
        revision.getRevised()[1].setDescription(revision.getRevised()[0].getDescription());
        revision.getRevised()[1].setPrincipleCharacters(revision.getRevised()[0].getPrincipleCharacters());
        revision.getRevised()[1].setCreators(revision.getRevised()[0].getCreators());
        revision.getRevised()[1].setPublicationDate(revision.getRevised()[0].getPublicationDate());
        revision.getRevised()[1].setValue(revision.getRevised()[0].getTotalValue());
        revision.getRevised()[0] = copy;
        RevisionHistory.addRedoRevision(revision.getRevised(), this);
        ui.updateCurrentItem(revision.getRevised()[1]);
    }

    @Override
    public Revisions createRevision(Comic[] comics) {
        return new EditRevision(comics, this);
    }

    @Override
    public void redo(Revisions revision) throws CommandInputException {
        Comic copy = revision.getRevised()[1].copyToUserComic();
        revision.getRevised()[1].setIssueNum(revision.getRevised()[0].getIssueNum());
        revision.getRevised()[1].setDescription(revision.getRevised()[0].getDescription());
        revision.getRevised()[1].setPrincipleCharacters(revision.getRevised()[0].getPrincipleCharacters());
        revision.getRevised()[1].setCreators(revision.getRevised()[0].getCreators());
        revision.getRevised()[1].setPublicationDate(revision.getRevised()[0].getPublicationDate());
        revision.getRevised()[1].setValue(revision.getRevised()[0].getTotalValue());
        revision.getRevised()[0] = copy;
        RevisionHistory.addUndoRevision(revision.getRevised(), this);
        ui.updateCurrentItem(revision.getRevised()[1]);
    }
}
