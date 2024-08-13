package core.comix.model.comic;
import core.comix.model.collections.CollectionCategory;
import core.comix.model.collections.database.FullCollection;
import core.comix.model.search.SearchCollector;
import java.util.ArrayList;

public class DBComic extends Comic{

    private static CollectionCategory DATABASE;

    public DBComic(String storyTitle, String issueNum, String publicationDate, ArrayList<String> principleCharacters, ArrayList<String> creators, String description, float value, String publisher, String series, int volumeNum) {
        super(storyTitle, issueNum, publicationDate, principleCharacters, creators, description, value, publisher, series, volumeNum);
    }

    @Override
    public void performSearch(SearchCollector v, String searchPhrase) {
    }

    public static void setParent(FullCollection db) {
        DATABASE = db;
    }

    public boolean isMarkup(){
        return false;
    }

    @Override
    public String getMenuString(int scrollAmount) {
        return "DBCOMIC\n-------" + super.getMenuString(scrollAmount);
    }

    @Override
    public CollectionCategory getParent() {
        return DBComic.DATABASE;
    }

    @Override
    public void setIssueNum(String issueNum) {
        //shouldn't happen
    }

    @Override
    public void setPublicationDate(String publicationDate) {
        //shouldn't happen
    }

    @Override
    public void setPrincipleCharacters(ArrayList<String> principleCharacters) {
        //shouldn't happen
    }

    @Override
    public void setDescription(String description) {
        //shouldn't happen
    }

    @Override
    public void setValue(float value) {
        //shouldn't happen
    }

    @Override
    public void setCreators(ArrayList<String> creatorz) {
        //hahaha u thought
    }
    
}