package core.comix.controller.commands;

import java.util.ArrayList;

import core.comix.controller.Command;
import core.comix.controller.invokers.*;
import core.comix.model.collections.personal.PersonalCollection;
import core.comix.model.comic.Comic;
import core.comix.view.UI;

public class HelpCommand extends Command {
    public HelpCommand(UI ui){
        super(ui);
    }

    public void execute() {
        ArrayList<String> helpCommandStrings = new ArrayList<String>();
        if (ui.getCurrentItem() instanceof Comic) {
            helpCommandStrings.add(AddCommandInvoker.getHelpString());
            helpCommandStrings.add(RemoveCommandInvoker.getHelpString());
            helpCommandStrings.add(EditCommandInvoker.getHelpString());
            helpCommandStrings.add(GradeCommandInvoker.getHelpString());
            helpCommandStrings.add(SlabCommandInvoker.getHelpString());
            helpCommandStrings.add(SignCommandInvoker.getHelpString());
            helpCommandStrings.add(AuthenticateCommandInvoker.getHelpString());
        }
        else if (ui.getCurrentItem() instanceof PersonalCollection) {
            helpCommandStrings.add(AddCustomCommandInvoker.getHelpString());
        }
        else {
            helpCommandStrings.add(ScrollCommandInvoker.getHelpString());
            helpCommandStrings.add(SearchCommandInvoker.getHelpString());
            helpCommandStrings.add(SearchTypeCommandInvoker.getHelpString());
            helpCommandStrings.add(SortTypeCommandInvoker.getHelpString());
        }
        helpCommandStrings.add(BackCommandInvoker.getHelpString());
        helpCommandStrings.add(BrowseCommandInvoker.getHelpString());
        helpCommandStrings.add(QuitCommandInvoker.getHelpString());
    
        for (String s : helpCommandStrings) {
            System.out.println(s);
        }
    }
}
