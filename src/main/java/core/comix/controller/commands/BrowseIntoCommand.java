package core.comix.controller.commands;
import java.io.IOException;
import java.util.ArrayList;

import core.comix.controller.Command;
import core.comix.controller.CommandInputException;
import core.comix.model.collections.CollectionComponent;
import core.comix.model.collections.CollectionCategory;
import core.comix.view.UI;

public class BrowseIntoCommand extends Command {
    public BrowseIntoCommand(UI ui){
        super(ui);
    }

    public void execute(int targetChild) throws CommandInputException {
        int len = ((CollectionCategory)ui.getCurrentItem()).getChildren().size();
        if(targetChild<=len){
            ArrayList<CollectionComponent> children = ((CollectionCategory)ui.getCurrentItem()).getChildren();
            ui.resetScroll();
            ui.updateCurrentItem(children.get(targetChild-1));
        } else {
            throw new CommandInputException("Index entered is outside of the range available");
        }
    }
}
