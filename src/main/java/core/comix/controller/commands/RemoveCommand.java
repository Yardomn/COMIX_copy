package core.comix.controller.commands;
import java.io.IOException;

import core.comix.controller.Command;
import core.comix.controller.CommandInputException;
import core.comix.controller.UndoableCommands;
import core.comix.model.Undo.RemoveRevision;
import core.comix.model.Undo.RevisionHistory;
import core.comix.model.Undo.Revisions;
import core.comix.model.collections.CollectionCategory;
import core.comix.model.comic.Comic;
import core.comix.model.comic.UserComic;
import core.comix.model.user.CurrentUser;
import core.comix.view.UI;

public class RemoveCommand extends Command implements UndoableCommands{
    public RemoveCommand(UI ui){
        super(ui);
    }

    public void execute() throws CommandInputException {
        if (! (ui.getCurrentItem() instanceof Comic)) {
            throw new CommandInputException("Command <remove> can only be run from a Comic page.");
        }
        UserComic currentComic = (UserComic)ui.getCurrentItem();
        CollectionCategory currentParent = currentComic.getParent();
        CurrentUser.getPersonalCollection().tryRemoveComic(currentComic);
        ui.updateCurrentItem(currentParent);
        Comic[] revComics = {currentComic};
        RevisionHistory.addUndoRevision(revComics, this);
    }

    public void undo(Revisions revision) {
        CurrentUser.getPersonalCollection().tryAddComic(revision.getRevised()[0]);
        RevisionHistory.addRedoRevision(revision.getRevised(), this);
        ui.updateCurrentItem(revision.getRevised()[0]);
    }

    @Override
    public Revisions createRevision(Comic[] comics) {
        return new RemoveRevision(comics, this);
    }

    @Override
    public void redo(Revisions revision) throws CommandInputException {
        CurrentUser.getPersonalCollection().tryRemoveComic(revision.getRevised()[0]);
        RevisionHistory.addUndoRevision(revision.getRevised(), this);
        ui.updateCurrentItem(revision.getRevised()[0].getParent());
    }
}
