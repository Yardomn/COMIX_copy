package core.comix.controller.commands;

import java.io.IOException;

import core.comix.controller.Command;
import core.comix.controller.CommandInputException;
import core.comix.view.UI;

public class BackCommand extends Command {
    public BackCommand(UI ui){
        super(ui);
    }

    public void execute() throws CommandInputException {
        if (ui.getCurrentItem().getParent() == null) {
            throw new CommandInputException("Can't go back at the top of a hierarchy.");
        }
        ui.updateCurrentItem(ui.getCurrentItem().getParent());
    }
    
}
