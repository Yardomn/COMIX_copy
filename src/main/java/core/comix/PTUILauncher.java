package core.comix;


import java.io.IOException;

import core.comix.controller.CommandInputException;
import core.comix.controller.Invoker;
import core.comix.controller.commands.*;
import core.comix.controller.invokers.*;
import core.comix.model.collections.database.FullCollection;
import core.comix.model.collections.personal.PersonalCollection;
import core.comix.model.comic.Comic;
import core.comix.model.comic.DBComic;
import core.comix.model.search.PartialSearch;
import core.comix.model.search.SearchResults;
import core.comix.model.user.CurrentUser;
import core.comix.persistence.db.CustomCSVEditor;
import core.comix.persistence.db.CustomJSONEditor;
import core.comix.persistence.db.CustomXMLEditor;
import core.comix.model.user.LoginManager;
import core.comix.model.user.User;
import core.comix.persistence.pc.CSVCollectionManager;
import core.comix.persistence.pc.InMemoryCollectionManager;
import core.comix.persistence.pc.JSONCollectionManager;
import core.comix.persistence.pc.XMLCollectionManager;
import core.comix.view.InputListener;
import core.comix.view.PTUI;
import core.comix.view.pages.ptui.EditPage;
import core.comix.view.pages.ptui.FullCollectionPage;
import core.comix.view.pages.ptui.PTUIPage;
import core.comix.view.pages.ptui.PersonalCollectionPage;
import core.comix.view.pages.ptui.SearchResultPage;

public class PTUILauncher {
    public static void main(String[] args) throws IOException {
        System.out.println("WELCOME TO COMIX PTUI");
        PTUIPage initialPage = setupTargetPages();
        setupViewController(initialPage);
        
    }

    public static PTUIPage setupTargetPages() {
        FullCollectionPage fcPage = new FullCollectionPage();
        EditPage ePage = new EditPage();
        PersonalCollectionPage pcPage = new PersonalCollectionPage();
        SearchResultPage srPage = new SearchResultPage();
        FullCollection.setTargetPTUIPage(fcPage);
        Comic.setTargetPTUIPage(ePage);
        PersonalCollection.setTargetPTUIPage(pcPage);
        SearchResults.setTargetPTUIPage(srPage);
        return fcPage;
    }

    public static void setupViewController(PTUIPage initialPage) throws IOException {
        
        PTUI ptui = new PTUI(initialPage);
        InputListener il = new InputListener(ptui);

        CustomCSVEditor dbreader = new CustomCSVEditor("src/main/resources/comics.csv");
        CSVCollectionManager pcreader = new CSVCollectionManager("src/main/resources/userCollections", CurrentUser.getInstance());

        FullCollection fc = new FullCollection();
        fc.setSearchType(new PartialSearch());
        DBComic.setParent(fc);
        PersonalCollection pc = new PersonalCollection(pcreader);
        pc.setSearchType(new PartialSearch());

        try {
            dbreader.readComics(fc);
            pcreader.loadCollectionFromStorage(pc);
        } catch (CommandInputException e) {
            System.out.println("Error loading comic info for DB and PC.");
        }

        LoginManager loginManager = new LoginManager(fc, pcreader);

        // Set up all invokers and commands
        AddCommand addCommand = new AddCommand(ptui);
        Invoker addCommandInvoker = new AddCommandInvoker(addCommand);
        il.addMapping("add", addCommandInvoker);

        AddCustomCommand addCustomCommand = new AddCustomCommand(ptui);
        Invoker addCustomCommandInvoker = new AddCustomCommandInvoker(addCustomCommand);
        il.addMapping("add-custom", addCustomCommandInvoker);

        AuthenticateCommand authenticateCommand = new AuthenticateCommand(ptui);
        Invoker authenticateInvoker = new AuthenticateCommandInvoker(authenticateCommand);
        il.addMapping("authenticate", authenticateInvoker);

        BackCommand backCommand = new BackCommand(ptui);
        Invoker backCommandInvoker = new BackCommandInvoker(backCommand);
        il.addMapping("back", backCommandInvoker);

        BrowseCollectionCommand browseCollectionCommand = new BrowseCollectionCommand(ptui);
        BrowseIntoCommand browseIntoCommand = new BrowseIntoCommand(ptui);
        BrowseCommandInvoker browseCommandInvoker = new BrowseCommandInvoker(browseCollectionCommand, browseIntoCommand);
        il.addMapping("browse", browseCommandInvoker);

        EditCommand editCommand = new EditCommand(ptui);
        Invoker editCommandInvoker = new EditCommandInvoker(editCommand);
        il.addMapping("edit", editCommandInvoker);

        ExportDBCommand exportDBCommand = new ExportDBCommand(ptui);
        Invoker exportDBCommandInvoker = new ExportDBCommandInvoker(exportDBCommand);
        il.addMapping("exportdb", exportDBCommandInvoker);

        ExportPCCommand exportPCCommand = new ExportPCCommand(ptui);
        Invoker exportPCCommandInvoker = new ExportPCCommandInvoker(exportPCCommand);
        il.addMapping("exportpc", exportPCCommandInvoker);

        ImportDBCommand importDBCommand = new ImportDBCommand(ptui);
        Invoker importDBCommandInvoker = new ImportDBCommandInvoker(importDBCommand);
        il.addMapping("importdb", importDBCommandInvoker);

        ImportPCCommand importPCCommand = new ImportPCCommand(ptui);
        Invoker importPCCommandInvoker = new ImportPCCommandInvoker(importPCCommand);
        il.addMapping("importpc", importPCCommandInvoker);

        GradeCommand gradeCommand = new GradeCommand(ptui);
        Invoker gradeCommandInvoker = new GradeCommandInvoker(gradeCommand);
        il.addMapping("grade", gradeCommandInvoker);

        QuitCommand quitCommand = new QuitCommand(ptui);
        Invoker quitCommandInvoker = new QuitCommandInvoker(quitCommand);
        il.addMapping("quit", quitCommandInvoker);

        RemoveCommand removeCommand = new RemoveCommand(ptui);
        Invoker removeCommandInvoker = new RemoveCommandInvoker(removeCommand);
        il.addMapping("remove", removeCommandInvoker);

        ScrollCommand scrollCommand = new ScrollCommand(ptui);
        Invoker scrollCommandInvoker = new ScrollCommandInvoker(scrollCommand);
        il.addMapping("scroll", scrollCommandInvoker);

        SearchCommand searchCommand = new SearchCommand(ptui);
        Invoker searchCommandInvoker = new SearchCommandInvoker(searchCommand);
        il.addMapping("search", searchCommandInvoker);

        SearchTypeCommand searchTypeCommand = new SearchTypeCommand(ptui);
        Invoker searchTypeCommandInvoker = new SearchTypeCommandInvoker(searchTypeCommand);
        il.addMapping("search-type", searchTypeCommandInvoker);

        SignCommand signCommand = new SignCommand(ptui);
        Invoker signInvoker = new SignCommandInvoker(signCommand);
        il.addMapping("sign", signInvoker);

        SlabCommand slabCommand = new SlabCommand(ptui);
        Invoker slabCommandInvoker = new SlabCommandInvoker(slabCommand);
        il.addMapping("slab", slabCommandInvoker);

        SortTypeCommand sortTypeCommand = new SortTypeCommand(ptui);
        Invoker sortTypeCommandInvoker = new SortTypeCommandInvoker(sortTypeCommand);
        il.addMapping("sort-type", sortTypeCommandInvoker);

        LoginCommand loginCommand = new LoginCommand(ptui, loginManager);
        Invoker loginCommandInvoker = new LoginCommandInvoker(loginCommand);
        il.addMapping("login", loginCommandInvoker);

        SignupCommand signupCommand = new SignupCommand(ptui, loginManager);
        Invoker signupCommandInvoker = new SignupCommandInvoker(signupCommand);
        il.addMapping("signup", signupCommandInvoker);

        HelpCommand helpCommand = new HelpCommand(ptui);
        Invoker helpCommandInvoker = new HelpCommandInvoker(helpCommand);
        il.addMapping("help", helpCommandInvoker);

        ptui.updateCurrentItem(fc);

        il.getInputLoop();
    }
}
