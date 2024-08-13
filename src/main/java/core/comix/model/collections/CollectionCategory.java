package core.comix.model.collections;

import core.comix.model.comic.Comic;
import core.comix.model.search.SearchCollector;
import core.comix.view.pages.gui.GUIPage;
import core.comix.view.pages.ptui.PTUIPage;

import java.util.ArrayList;

public abstract class CollectionCategory implements CollectionComponent {

    public final static int NUM_MENU_ITEMS = 10;

    protected ArrayList<CollectionComponent> children;
    protected CollectionCategory parent;

    public CollectionCategory(){
        children = new ArrayList<CollectionComponent>();
    }

    public abstract void performSearch(SearchCollector v, String searchPhrase);

    public abstract boolean tryAddComic(Comic comic);

    public abstract boolean tryRemoveComic(Comic comic);

    public CollectionComponent getChild(int childID) {
        return children.get(childID);
    }

    public ArrayList<CollectionComponent> getChildren() {
        return children;
    }
    
    public String getMenuString(int scrollAmount) {
        String ret = "";
        for (int i = scrollAmount; i < Math.min(children.size(), NUM_MENU_ITEMS + scrollAmount); i++) {
            ret += Integer.toString(i + 1) + " - " + children.get(i).getNamingString() + "\n";
        }
        return ret;
    }

    public abstract String getNamingString();

    public float getTotalValue() {
        float totalValue = 0;
        for (CollectionComponent c : children) {
            totalValue += c.getTotalValue();
        }
        return totalValue;
    }

    public ArrayList<Comic> getAllComics() {
        ArrayList<Comic> allChildren = new ArrayList<Comic>();
        for (CollectionComponent c : children) {
            allChildren.addAll(c.getAllComics());
        }
        return allChildren;
    }

    public int getIssueCount() {
        int count = 0;
        for (CollectionComponent c : children) {
            count += c.getIssueCount();
        }
        return count;
    }

    public CollectionCategory getParent() {
        return parent;
    }

    public void setTargetPage() {}
    
    public PTUIPage getTargetPTUIPage() {
        return parent.getTargetPTUIPage();
    }

    public GUIPage getTargetGUIPage() {
        return parent.getTargetGUIPage();
    }
}
