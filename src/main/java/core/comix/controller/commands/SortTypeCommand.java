package core.comix.controller.commands;

import java.io.IOException;

import core.comix.controller.Command;
import core.comix.controller.CommandInputException;
import core.comix.model.collections.CollectionComponent;
import core.comix.model.search.SearchResults;
import core.comix.model.sort.SortMethod;
import core.comix.view.UI;


public class SortTypeCommand extends Command {
    public SortTypeCommand(UI ui){
        super(ui);
    }

    /*
     * Uses the Setter of SearchResults to set to the appropriate sort type
     *
     * sortMethod (SortMethod): the users preferred sort type
     */
    public void execute(SortMethod sortMethod) throws CommandInputException {
        CollectionComponent currentItem = this.ui.getCurrentItem();
        // If PTUI page is a search results page, then continue with operation
        if (currentItem instanceof SearchResults) {
            SearchResults currentSearch = (SearchResults) currentItem;
            // Update the state variable for sorting method in the SearchResult
            currentSearch.setSortMethod(sortMethod);
            // Perform the sort
            currentSearch.sort();
            // Update the PTUI Page to show the sorted result
            ui.updateCurrentItem(currentItem);
        }
        else
        {
            throw new CommandInputException("Command <sort-type> can only be run on a search results page");
        }
    }
}
