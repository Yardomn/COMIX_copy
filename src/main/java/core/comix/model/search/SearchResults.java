package core.comix.model.search;

import java.util.ArrayList;
import java.util.Collections;

import core.comix.model.collections.CollectionCategory;
import core.comix.model.collections.CollectionComponent;
import core.comix.model.comic.Comic;
import core.comix.model.sort.DefaultSort;
import core.comix.model.sort.SortMethod;
import core.comix.view.pages.gui.GUIPage;
import core.comix.view.pages.ptui.PTUIPage;

public class SearchResults extends CollectionCategory {

    private static PTUIPage TARGET_PTUI_PAGE;
    private static GUIPage TARGET_GUI_PAGE;
    private ArrayList<Comic> results;
    public static SearchResults instance = null;



    // Specifies the way to be sorted
    private SortMethod sortMethod = new DefaultSort();

    public SearchResults(ArrayList<Comic> results) {
        this.results = results;
    }

    public SearchResults() {
        this.results = new ArrayList<Comic>();

    }

    /**
     * the first time this is called it will create a new instance
     * all other times it will return the current instance
     * @return the current instance of SearchResults
     */
    public static SearchResults Instance() {
        if (instance == null) {
            instance = new SearchResults();
        }
        return instance;
    }

    public void clearSearchResults() {
        results.clear();
    }

    public void setSortMethod(SortMethod sortMethod) {
        this.sortMethod = sortMethod;
    }

    /*
     * Sorts the comic search results by the sortMethod
     *
     * comics (ArrayList) : a list of comics received from performing a search
     */
    public void sort() {
        Collections.sort(this.results, this.sortMethod);
    }

    public static void setTargetPTUIPage(PTUIPage targetPage) {
        TARGET_PTUI_PAGE = targetPage;
    }

    public static void setTargetGUIPage(GUIPage targetPage) {
        TARGET_GUI_PAGE = targetPage;
    }

    public PTUIPage getTargetPTUIPage() {
        return TARGET_PTUI_PAGE;
    }

    public GUIPage getTargetGUIPage() {
        return TARGET_GUI_PAGE;
    }



    @Override
    public int getIssueCount() {
        throw new UnsupportedOperationException("Unimplemented method 'getIssueCount'");
    }

    @Override
    public float getTotalValue() {
        throw new UnsupportedOperationException("Unimplemented method 'getTotalValue'");
    }

    @Override
    public ArrayList<Comic> getAllComics() {
        throw new UnsupportedOperationException("Unimplemented method 'getAllComics'");
    }

    @Override
    public boolean tryAddComic(Comic comic) {
        boolean found = false;
        for (CollectionComponent c : results) {
            if (c.equals(comic)) {
                found = true;
                break;
            }
        }
        if (!found) {
            results.add(comic);
        }
        return true;

    }

    @Override
    public boolean tryRemoveComic(Comic comic) {
        throw new UnsupportedOperationException("Unimplemented method 'tryRemoveComic'");
    }

    @Override
    public void performSearch(SearchCollector v, String searchPhrase) {
        throw new UnsupportedOperationException("Unimplemented method 'performSearch'");
    }

    public String getMenuString(int scrollAmount) {

        String ret = "";
        for (int i = scrollAmount; i < Math.min(results.size(),
                NUM_MENU_ITEMS + scrollAmount); i++) {
            ret += Integer.toString(i + 1) + " - " + results.get(i).getNamingString() + "\n";
        }
        return ret;
    }

    public CollectionComponent getChild(int childIndex) {
        return super.getChild(childIndex);
    }

    public ArrayList<CollectionComponent> getChildren() {
        ArrayList<CollectionComponent> children = new ArrayList<>(results);
        return children;
    }

    @Override
    public CollectionCategory getParent() {
        return super.getParent();
    }

    @Override
    public String getNamingString() {
        throw new UnsupportedOperationException("Unimplemented method 'getNamingString'");
    }
}
