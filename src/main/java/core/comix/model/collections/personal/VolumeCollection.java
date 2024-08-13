package core.comix.model.collections.personal;
import core.comix.model.collections.CollectionCategory;
import core.comix.model.collections.CollectionComponent;
import core.comix.model.comic.Comic;
import core.comix.model.search.SearchCollector;

public class VolumeCollection extends CollectionCategory {
    private int volumeNumber;

    public VolumeCollection(CollectionCategory parent, int volumeNum) {
        super();
        this.parent = parent;
        this.volumeNumber = volumeNum;
    }

    public Integer getVolumeNumber()
    {
        return volumeNumber;
    }

    public boolean tryAddComic(Comic comic){
        if (comic.getVolumeNum() != volumeNumber) {
            return false;
        }
        children.add(comic);
        comic.setParent(this);
        return true;
    }
    public boolean tryRemoveComic(Comic comic){
        if (comic.getVolumeNum() != volumeNumber) {
            return false;
        }
        children.remove(comic);
        return true;
    }
    public void performSearch(SearchCollector v, String searchPhrase){
        v.visitVolumeCollection(this, searchPhrase);
        for (CollectionComponent comic : children) {
            if(comic instanceof Comic)
            {
                Comic c = (Comic) comic;
                v.visitIssue(c, searchPhrase);

            }
        }
    }
    public String getMenuString(int scrollAmount){
        return getNamingString() + "\n" + super.getMenuString(scrollAmount);
    }
    public String getNamingString() {
        return "Vol." + Integer.toString(volumeNumber) + " - Total Value: " + Float.toString(getTotalValue()) + ", Number of Issues: " + Integer.toString(getIssueCount());
    }

    public float getTotalValue() {
        float totalValue = 0;
        for (CollectionComponent c : children) {
            totalValue += c.getTotalValue();
        }
        return totalValue;
    }

    public int getIssueCount(){
        return children.size();
    }

}
