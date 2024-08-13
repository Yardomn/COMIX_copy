package core.comix.controller.commands;
import java.io.IOException;

import core.comix.controller.Command;
import core.comix.controller.CommandInputException;
import core.comix.model.collections.CollectionCategory;
import core.comix.view.UI;

public class ScrollCommand extends Command {
    public ScrollCommand(UI ui){
        super(ui);
    }

    public void execute(boolean up) throws CommandInputException {
        if (!(ui.getCurrentItem() instanceof CollectionCategory)) {
            throw new CommandInputException("Command <scroll> cannot run on a Comic page");
        } else if(((CollectionCategory)ui.getCurrentItem()).getChildren().size() >= ui.getScrollAmount()+ui.getOffset()){
            ui.changeScroll(up);
            ui.displayPage();
        } else{
            ui.resetScroll();
            ui.displayPage();
        }
    }
}
