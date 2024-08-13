package core.comix.controller.commands;

import java.io.IOException;

import core.comix.controller.Command;
import core.comix.controller.UndoableCommands;
import core.comix.model.Undo.AddRevision;
import core.comix.model.Undo.RevisionHistory;
import core.comix.model.Undo.Revisions;
import core.comix.controller.CommandInputException;
import core.comix.model.comic.Comic;
import core.comix.model.comic.UserComic;

import core.comix.model.user.CurrentUser;
import core.comix.view.UI;



public class AddCommand extends Command implements UndoableCommands{
    public AddCommand(UI ui){
        super(ui);
    }

    // Adds the currently viewed comic to the User's personal collection.
    public void execute() throws CommandInputException {
        if (! (ui.getCurrentItem() instanceof Comic)) {
            throw new CommandInputException("Command <add> can only be run from a Comic page.");
        }
        if (! CurrentUser.hasAddRemovePermissions()) {
            throw new CommandInputException("You must be signed in to add a comic to your collection.");
        }
        Comic currentComic = (Comic)ui.getCurrentItem();
        UserComic userComic = currentComic.copyToUserComic();
        CurrentUser.getPersonalCollection().tryAddComic(userComic);
        ui.updateCurrentItem(userComic);
        Comic[] revComics = {userComic};
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
