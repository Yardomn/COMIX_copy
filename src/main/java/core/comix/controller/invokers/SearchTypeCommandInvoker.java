package core.comix.controller.invokers;

import core.comix.controller.CommandInputException;
import core.comix.controller.Invoker;
import core.comix.controller.commands.SearchTypeCommand;
import core.comix.model.search.MarkedUpSearch;
import core.comix.model.search.ExactSearch;
import core.comix.model.search.GapsSearch;
import core.comix.model.search.PartialSearch;
import core.comix.model.search.RunSearch;
import core.comix.model.search.SearchCollector;
import core.comix.model.search.IssueSearch;

public class SearchTypeCommandInvoker extends Invoker {

    private SearchTypeCommand searchTypeCommand;

    public SearchTypeCommandInvoker(SearchTypeCommand command) {
        searchTypeCommand = command;
    }

    // TODO: Check this code, feels like typecoding but I'm not really sure how to avoid it
    public void executeCommand(String input) throws CommandInputException {
        SearchCollector searchType;

        switch (input) {
            case "partial":
                searchType = new PartialSearch();
                break;
            case "exact":
                searchType = new ExactSearch();
                break;
            case "issue":
                searchType = new IssueSearch();
                break;
            case "markedUp":
                searchType = new MarkedUpSearch();
                break;
            case "run":
                searchType = new RunSearch();
                break;
            case "gap":
                searchType = new GapsSearch();
                break;
            default:
                throw new CommandInputException(
                        "Search type not recognized. Please use 'partial' or 'exact' as arguements.");
        }

        searchTypeCommand.execute(searchType);
    }

    public static String getHelpString() {
        return "--SEARCH TYPE command--\nsearch-type <exact | partial>\n";
    }
}
