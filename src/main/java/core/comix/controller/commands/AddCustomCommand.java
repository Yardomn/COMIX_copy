package core.comix.controller.commands;
import java.io.IOException;
import java.util.ArrayList;

import core.comix.controller.Command;
import core.comix.controller.UndoableCommands;
import core.comix.model.Undo.AddRevision;
import core.comix.model.Undo.RevisionHistory;
import core.comix.model.Undo.Revisions;
import core.comix.model.comic.Comic;
import core.comix.controller.CommandInputException;
import core.comix.model.collections.personal.PersonalCollection;
import core.comix.model.comic.UserComic;
import core.comix.model.user.CurrentUser;
import core.comix.view.UI;

public class AddCustomCommand extends Command implements UndoableCommands{
    public AddCustomCommand(UI ui){
        super(ui);
    }

    // Creates a new custom comic in the user's Personal Collection
        
    public void execute(String storyTitle, String publisher, String series, int volumeNum, String issueNum, String publicationDate, ArrayList<String> creators, ArrayList<String> characters, String description, float value) throws CommandInputException {
        if (! (ui.getCurrentItem() instanceof PersonalCollection)) {
            throw new CommandInputException("Command <add-custom> can only be run from the Personal Collection page.");
        }
        if (! CurrentUser.hasAddRemovePermissions()) {
            throw new CommandInputException("You must be signed in to add a comic to your collection.");
        }
        UserComic newComic = new UserComic(storyTitle, issueNum, publicationDate, characters, creators, description, value, publisher, series, volumeNum);
        CurrentUser.getPersonalCollection().tryAddComic(newComic);
        ui.updateCurrentItem(newComic);
        CurrentUser.getPersonalCollection().save();
        Comic[] revComics = {newComic};
        RevisionHistory.addUndoRevision(revComics, this);
    }

    public void undo(Revisions revision) {
        CurrentUser.getPersonalCollection().tryRemoveComic(revision.getRevised()[0]);
        RevisionHistory.addRedoRevision(revision.getRevised(), this);
        ui.updateCurrentItem(revision.getRevised()[0].getParent());
    }
    @Override
    public Revisions createRevision(Comic[] comics) {
        return new AddRevision(comics, this);

    }

    @Override
    public void redo(Revisions revision) throws CommandInputException {
        CurrentUser.getPersonalCollection().tryAddComic(revision.getRevised()[0]);
        RevisionHistory.addUndoRevision(revision.getRevised(), this);
        ui.updateCurrentItem(revision.getRevised()[0]);
    }
}
