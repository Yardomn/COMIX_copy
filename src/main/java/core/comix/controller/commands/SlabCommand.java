package core.comix.controller.commands;
import java.util.List;

import core.comix.controller.Command;
import core.comix.controller.UndoableCommands;
import core.comix.model.Undo.MarkupRevision;
import core.comix.model.Undo.RevisionHistory;
import core.comix.model.Undo.Revisions;
import core.comix.controller.CommandInputException;
import core.comix.model.collections.CollectionCategory;
import core.comix.model.collections.CollectionComponent;
import core.comix.model.comic.Comic;
import core.comix.model.comic.DBComic;
import core.comix.model.comic.MarkupOption;
import core.comix.model.comic.Slabbed;
import core.comix.model.user.CurrentUser;
import core.comix.view.UI;

public class SlabCommand extends Command implements UndoableCommands{
    public SlabCommand(UI ui){
        super(ui);
    }

    public void execute() throws CommandInputException {
        if(ui.getCurrentItem() instanceof CollectionComponent && !(ui.getCurrentItem() instanceof DBComic)){
            Comic comic = (Comic) ui.getCurrentItem();
            boolean canSlab = false;
            if(comic.isMarkup()) {
                MarkupOption markup = (MarkupOption) comic;
                if(markup.isGraded() && !(markup.isSlabbed())) {canSlab = true;}
            }
            if(canSlab){
                CollectionCategory parent = comic.getParent();
                List<CollectionComponent> children = parent.getChildren();

                int index = 0;
                for(CollectionComponent child: children) {
                    if(comic.getMenuString(0).equals(child.getMenuString(0))){
                        Slabbed slabbedComic = new Slabbed(comic);

                        comic = (Comic)children.remove(index);
                        children.add(index, slabbedComic);
                        ui.updateCurrentItem(slabbedComic);

                        Comic[] revComics = {comic, slabbedComic};
                        RevisionHistory.addUndoRevision(revComics, this);
                        CurrentUser.getPersonalCollection().save();
                        return;
                    }
                    index += 1;
                }
                throw new CommandInputException("Comic not found in current collection.");
            } else {
                throw new CommandInputException("Comic is already slabbed, or not graded.");
            }
        } else{
            throw new CommandInputException("Currently displayed item should be a comic and not be a database comic.");
        }
    }

    public void undo(Revisions revision) throws CommandInputException {
        CollectionCategory parent = revision.getRevised()[1].getParent();
        List<CollectionComponent> children = parent.getChildren();
        int index = 0;
        for(CollectionComponent child: children) {
            if(revision.getRevised()[1].getMenuString(0).equals(child.getMenuString(0))){
                children.remove(index);
                children.add(index, revision.getRevised()[0]);

                ui.updateCurrentItem(revision.getRevised()[0]);
                CurrentUser.getPersonalCollection().save();
                RevisionHistory.addRedoRevision(revision.getRevised(), this);
                return;
            }
            index += 1;
        }
        throw new CommandInputException("Comic not found in current collection.");
    }

    @Override
    public Revisions createRevision(Comic[] comics) {
        return new MarkupRevision(comics, this);

    }

    @Override
    public void redo(Revisions revision) throws CommandInputException {
        CollectionCategory parent = revision.getRevised()[0].getParent();
        List<CollectionComponent> children = parent.getChildren();
        int index = 0;
        for(CollectionComponent child: children) {
            if(revision.getRevised()[0].getMenuString(0).equals(child.getMenuString(0))){
                children.remove(index);
                children.add(index, revision.getRevised()[1]);

                ui.updateCurrentItem(revision.getRevised()[1]);
                CurrentUser.getPersonalCollection().save();
                RevisionHistory.addUndoRevision(revision.getRevised(), this);
                return;
            }
            index += 1;
        }
        throw new CommandInputException("Comic not found in current collection.");
    }
}
