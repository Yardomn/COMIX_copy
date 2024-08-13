package core.comix.model.comic;

import java.util.ArrayList;

import core.comix.model.collections.CollectionCategory;
import core.comix.model.search.SearchCollector;

public class UserComic extends Comic {

    private CollectionCategory parent;

    public UserComic(String storyTitle, String issueNum, String publicationDate, ArrayList<String> principleCharacters, ArrayList<String> creators, String description, float value, String publisher, String series, int volumeNum) {
        super(storyTitle, issueNum, publicationDate, principleCharacters, creators, description, value, publisher, series, volumeNum);
    }

    @Override
    public void performSearch(SearchCollector v, String searchPhrase) {
    }

    public void setIssueNum(String newIssueNum){
        this.issueNum = newIssueNum;
    }

    public void setPublicationDate(String newPubDate){
        this.publicationDate = newPubDate;
    }

    public void setPrincipleCharacters(ArrayList<String> newPrincpChars){
        this.principleCharacters = newPrincpChars;
    }

    public void setCreators(ArrayList<String> creatorz){
        this.creators = creatorz;
    }

    public void setDescription(String newDescription){
        this.description = newDescription;
    }

    public void setValue(float newValue){
        this.value = newValue;
    }

    public void setParent(CollectionCategory newParent){
        this.parent = newParent;
    }

    public boolean isMarkup(){
        return false;
    }

    @Override
    public String getMenuString(int scrollAmount) {
        return "USERCOMIC\n---------" + super.getMenuString(scrollAmount);
    }

    @Override
    public CollectionCategory getParent() {
        return this.parent;
    }
}