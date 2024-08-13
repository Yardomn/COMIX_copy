package core.comix.controller.commands;
import java.io.IOException;

import core.comix.controller.Command;
import core.comix.view.UI;
import core.comix.controller.CommandInputException;
import core.comix.model.user.CurrentUser;

public class BrowseCollectionCommand extends Command {
    public BrowseCollectionCommand(UI ui){
        super(ui);
    }

    public void execute(String targetCategory) throws CommandInputException {
        if(targetCategory.equals("personal collection") || targetCategory.equals("pc")){
            ui.resetScroll();
            ui.updateCurrentItem(CurrentUser.getPersonalCollection());
        } else if(targetCategory.equals("database") || targetCategory.equals("full collection") || targetCategory.equals("db")){
            ui.resetScroll();
            ui.updateCurrentItem(CurrentUser.getDatabase());
        } else{
            throw new CommandInputException("Command <browse> onlys takes collection indecies, 'personal collection', and 'full collection' as arguements");
        }
    }
}
