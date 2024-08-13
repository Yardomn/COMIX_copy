package core.comix.model.collections.personal;
import java.util.*;

import core.comix.controller.Command;
import core.comix.controller.CommandInputException;
import core.comix.model.collections.CollectionCategory;
import core.comix.model.collections.CollectionComponent;
import core.comix.model.comic.Comic;
import core.comix.model.search.SearchCollector;
import core.comix.model.search.SearchResults;
import core.comix.model.user.CurrentUser;
import core.comix.persistence.pc.CSVCollectionManager;
import core.comix.persistence.pc.ICollectionStorageManager;
import core.comix.view.pages.gui.GUIPage;
import core.comix.view.pages.ptui.PTUIPage;

public class PersonalCollection extends CollectionCategory implements IPopulableCollection {
    private static PTUIPage TARGET_PTUI_PAGE;
    private static GUIPage TARGET_GUI_PAGE;
    private SearchCollector searchType;
    protected CollectionCategory parent;
    private ICollectionStorageManager storageManager;
    
    public PersonalCollection(ICollectionStorageManager storageManager){
        super();
        this.children = new ArrayList<CollectionComponent>();
        this.storageManager = storageManager;
    }
    public boolean tryAddComic(Comic comic){
        boolean result = tryAddComicNoSave(comic);
        save();
        return result;
    }
    private boolean tryAddComicNoSave(Comic comic) {
        boolean found = false;
        for (CollectionComponent c : children) {
            if (((CollectionCategory)c).tryAddComic(comic)) {
                found = true;
                break;
            }
        }
        if (!found) {
            CollectionCategory newCategory = new PublisherCollection(this, comic.getPublisher());
            children.add(newCategory);
            newCategory.tryAddComic(comic);
        }
        
        return true;
    }
    public boolean tryRemoveComic(Comic comic){
        for (CollectionComponent c : children) {
            if (((CollectionCategory)c).tryRemoveComic(comic)) {
                if (((CollectionCategory)c).getChildren().size() == 0) {
                    children.remove(c);
                }
                save();
                return true;
            }
        }
        save();
        return false;
    }

    public void save() {
        storageManager = new CSVCollectionManager("src/main/resources/userCollections/", CurrentUser.getInstance());
        try {
            storageManager.saveCollectionToStorage(this);
        } catch (CommandInputException e) {
            System.out.println("Error: Unable to save Personal Collection to storage");
        }
        
    }
    public Comic addComic(Comic comic) {
        tryAddComicNoSave(comic);
        return comic;
    }
    public Comic removeComic(Comic comic) {
        tryRemoveComic(comic);
        return comic;
    }
    public void performSearch(SearchCollector v, String searchPhrase)
    {
        SearchResults.Instance().clearSearchResults();
        for(CollectionComponent publisherCollection: children)
        {
            publisherCollection.performSearch(v, searchPhrase);
        }


    }
    public void performSearch(String searchPhrase){
        performSearch(searchType, searchPhrase);
    }
    public String getMenuString(int scrollAmount){
        return getNamingString() + "\n" + super.getMenuString(scrollAmount);
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
        return "Personal Collection - Total Value: " + Float.toString(getTotalValue()) + ", Number of Issues: " + Integer.toString(getIssueCount());
    }

    public SearchCollector getSearchType(){
        return this.searchType;
    }

    public void setSearchType(SearchCollector searchType){
        this.searchType = searchType;
    }

    public float getTotalValue() {
        float totalValue = 0;
        for (CollectionComponent c : children) {
            totalValue += c.getTotalValue();
        }
        return totalValue;
    }

    public int getIssueCount(){
        int sum = 0;
        for(CollectionComponent child: children){
            sum += child.getIssueCount();
        }
        return sum;
    }

    public void clear() {
        for (Comic c : getAllComics()) {
            tryRemoveComic(c);
        }
    }

    public void clearNoSave() {
        for (Comic c : getAllComics()) {
            tryRemoveComicNoSave(c);
        }
    }

    public boolean tryRemoveComicNoSave(Comic comic){
        for (CollectionComponent c : children) {
            if (((CollectionCategory)c).tryRemoveComic(comic)) {
                if (((CollectionCategory)c).getChildren().size() == 0) {
                    children.remove(c);
                }
                return true;
            }
        }
        return false;
    }
}
