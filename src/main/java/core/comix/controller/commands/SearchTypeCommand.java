package core.comix.controller.commands;

import core.comix.controller.Command;
import core.comix.controller.CommandInputException;
import core.comix.model.collections.database.FullCollection;
import core.comix.model.collections.personal.PersonalCollection;
import core.comix.model.search.SearchCollector;
import core.comix.model.user.CurrentUser;
import core.comix.view.UI;
import core.comix.view.pages.ptui.FullCollectionPage;
import core.comix.view.pages.ptui.PersonalCollectionPage;

public class SearchTypeCommand extends Command {
    public SearchTypeCommand(UI ui) {
        super(ui);
    }

    /*
     * If user is in personal collection, sets search type for the personal collection. Else, sets
     * it for the database.
     *
     * searchType (String) : specifies the search type, either partial or exact
     */
    public void execute(SearchCollector searchType) throws CommandInputException {
        if (ui.getCurrentItem() instanceof PersonalCollection) {
            CurrentUser.getPersonalCollection().setSearchType(searchType);
        } else if (ui.getCurrentItem() instanceof FullCollection) {
            CurrentUser.getDatabase().setSearchType(searchType);
        } else {
            throw new CommandInputException(
                    "Command <search-type> can only be run on a full collection or personal collection page");

        }

    }
}
