package core.comix.model.collections.personal;

import core.comix.model.collections.CollectionCategory;
import core.comix.model.collections.CollectionComponent;
import core.comix.model.comic.Comic;
import core.comix.model.search.SearchCollector;

public class PublisherCollection extends CollectionCategory {
    private String publisherName;

    public PublisherCollection(CollectionCategory parent, String publisherName) {
        super();
        this.parent = parent;
        this.publisherName = publisherName;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public boolean tryAddComic(Comic comic) {
        if (!comic.getPublisher().equals(publisherName)) {
            return false;
        }
        boolean found = false;
        for (CollectionComponent c : children) {
            if (((CollectionCategory) c).tryAddComic(comic)) {
                found = true;
                break;
            }
        }
        if (!found) {
            CollectionCategory newCategory = new SeriesCollection(this, comic.getSeries());
            children.add(newCategory);
            newCategory.tryAddComic(comic);
        }
        return true;
    }

    public boolean tryRemoveComic(Comic comic) {
        if (!comic.getPublisher().equals(publisherName)) {
            return false;
        }
        for (CollectionComponent c : children) {
            if (((CollectionCategory) c).tryRemoveComic(comic)) {
                if (((CollectionCategory)c).getChildren().size() == 0) {
                    children.remove(c);
                }
                return true;
            }
        }
        return false;
    }

    public void performSearch(SearchCollector v, String searchPhrase) {
        v.visitPublisherCollection(this, searchPhrase);
        for (CollectionComponent seriesCollection : children) {
            seriesCollection.performSearch(v, searchPhrase);
        }
    }

    public String getMenuString(int scrollAmount) {
        return getNamingString() + "\n" + super.getMenuString(scrollAmount);
    }

    public String getNamingString() {
        return publisherName + " - Total Value: " + Float.toString(getTotalValue()) + ", Number of Issues: " + Integer.toString(getIssueCount());
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
}
