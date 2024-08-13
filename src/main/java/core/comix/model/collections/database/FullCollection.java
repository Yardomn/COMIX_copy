package core.comix.model.collections.database;
import java.util.ArrayList;
import core.comix.model.collections.CollectionCategory;
import core.comix.model.collections.CollectionComponent;
import core.comix.model.comic.Comic;
import core.comix.model.comic.DBComic;
import core.comix.model.search.SearchCollector;
import core.comix.model.search.SearchResults;
import core.comix.view.pages.gui.GUIPage;
import core.comix.view.pages.ptui.PTUIPage;

public class FullCollection extends CollectionCategory {
    private static PTUIPage TARGET_PTUI_PAGE;
    private static GUIPage TARGET_GUI_PAGE;
    private SearchCollector searchType;

    public FullCollection(){
        super();
    }

    public void populateDB(ArrayList<DBComic> dbcomics){
        for (DBComic comic : dbcomics) {
            tryAddComic(comic);
        }
    }

    public void performSearch(SearchCollector v, String searchPhrase){
        SearchResults.Instance().clearSearchResults();
        setSearchType(v);
        searchType.visitFullCollection(this, searchPhrase);
    }

    public void performSearch(String searchPhrase){
        performSearch(searchType, searchPhrase);
    }

    public void setSearchType(SearchCollector v){
        this.searchType = v;
    }

    public boolean tryAddComic(Comic comic){
        children.add(comic);
        return true;
    }

    public boolean tryRemoveComic(Comic comic){
        for (CollectionComponent c : children) {
            if (c == comic) {
                children.remove(c);
                return true;
            }
        }
        return false;
    }

    public CollectionCategory getParent(){return null;}
    
    public String getMenuString(int scrollAmount){
        return super.getMenuString(scrollAmount);
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

    public String getNamingString() {
        return "";
    }

    public void clear() {
        children = new ArrayList<CollectionComponent>();
    }
}
