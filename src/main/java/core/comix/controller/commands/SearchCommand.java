package core.comix.controller.commands;
import java.io.IOException;

import core.comix.controller.Command;
import core.comix.controller.CommandInputException;
import core.comix.model.collections.database.FullCollection;
import core.comix.model.collections.personal.PersonalCollection;
import core.comix.model.search.SearchResults;
import core.comix.model.user.CurrentUser;
import core.comix.view.UI;
import core.comix.view.pages.ptui.FullCollectionPage;
import core.comix.view.pages.ptui.PersonalCollectionPage;

public class SearchCommand extends Command {
    public SearchCommand(UI ui){
        super(ui);
    }

    /*
     * If user is in personal collection, searches the personal collection. Else, searches database.
     *
     * input (String) : the term(s) to be searched for
     */
    public void execute(String input) throws CommandInputException 
    {
        if (ui.getCurrentItem() instanceof PersonalCollection)
        {
            CurrentUser.getPersonalCollection().performSearch(input);
            SearchResults.Instance().sort();
            ui.updateCurrentItem(SearchResults.Instance());
        } 
        else if (ui.getCurrentItem() instanceof FullCollection)
        {
            CurrentUser.getDatabase().performSearch(input);
            SearchResults.Instance().sort();
            ui.updateCurrentItem(SearchResults.Instance());
        }
        else
        {
            throw new CommandInputException("Command <search> can only be performed on the personal collection page and the full collection page.");
        }
        
    }
}
