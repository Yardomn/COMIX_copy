package core.comix.model.collections;

import java.util.ArrayList;

import core.comix.model.comic.Comic;
import core.comix.model.search.SearchCollector;
import core.comix.view.pages.gui.GUIPage;
import core.comix.view.pages.ptui.PTUIPage;

public interface CollectionComponent {
    public void performSearch(SearchCollector v, String searchPhrase);

    // Gets a pretty version of this CollectionComponent to display when it is the highlight of the current PTUIPage.
    public String getMenuString(int scrollAmount);

    public float getTotalValue();

    public ArrayList<Comic> getAllComics();
    
    // Gets the PTUIPage that the state should switch to when it is given this Component.
    public PTUIPage getTargetPTUIPage();

    // Gets the GUIPage that the state should switch to when it is given this Component.
    public GUIPage getTargetGUIPage();

    public int getIssueCount();

    public CollectionCategory getParent();

    // Gets the "short" name of this Component, to display as one line of the hierarchy
    public String getNamingString();

}
