package core.comix.model.comic;

import java.util.ArrayList;

import core.comix.model.collections.CollectionCategory;
import core.comix.model.search.SearchCollector;
import core.comix.model.search.SearchResults;

public class Authenticated extends MarkupOption {
    private String name;

    public Authenticated(String name, Comic comic) {
        super(comic);
        this.name = name;
    }

    public float getTotalValue() {
        return super.comic.getTotalValue() * 1.2f;
    }

    @Override
    public void performSearch(SearchCollector v, String searchPhrase) {
        super.comic.performSearch(v, searchPhrase);
    }

    public boolean isGraded(){
        if(super.comic.isMarkup()) {
            MarkupOption markup = (MarkupOption) super.comic;
            return markup.isGraded();
        } else {
            return false;
        }
    }

    public boolean isSlabbed(){
        if(super.comic.isMarkup()) {
            MarkupOption markup = (MarkupOption) super.comic;
            return markup.isSlabbed();
        } else {
            return false;
        }
    }

    public boolean isSigned() {
        return true;
    }

    public boolean isAuthenticated(){
        return true;
    }

    public boolean checkSigned(String name) {
        if(super.comic.isMarkup()) {
            MarkupOption markup = (MarkupOption) super.comic;
            return markup.checkSigned(name);
        } else {
            return false;
        }
    } 

    public boolean checkAuthenticated(String name) {
        if(getSignature().equals(name)) {
            return true;
        } else {
            return false;
        }
    }

    public int getGrade() {
        return super.comic.getGrade();
    }

    public String getSignature() {
        return name;
    }

    public ArrayList<String> getSignatures(ArrayList<String> signatures) {
        if(super.comic.isMarkup()) {
            MarkupOption markup = (MarkupOption) super.comic;
            return markup.getSignatures(signatures);
        } else {
            return signatures;
        }
    }

    public ArrayList<String> getAuthenticates(ArrayList<String> authenticates) {
        authenticates.add(getSignature());
        if(super.comic.isMarkup()) {
            MarkupOption markup = (MarkupOption) super.comic;
            return markup.getAuthenticates(authenticates);
        } else {
            return authenticates;
        }
    }

    @Override
    public String getMenuString(int scrollAmount) {
        return super.comic.getMenuStringWithValue(getTotalValue())+ "\nAuthenticate                |" + getSignature(); 
    }

    @Override
    public String getMenuStringWithValue(float totalValue) {
        return super.comic.getMenuStringWithValue(totalValue)+ "\nAuthenticate                |" + getSignature();
    }

    public String getNamingString() {
        return comic.getStoryTitle() + " - Value: " + Float.toString(getTotalValue());
    }    

    @Override
    public CollectionCategory getParent() {
        return super.comic.getParent();
    }

    public void setParent(CollectionCategory newParent){
        super.comic.setParent(newParent);
    }

    @Override
    public void setIssueNum(String issueNum) {
        super.comic.setIssueNum(issueNum);
    }

    @Override
    public void setPublicationDate(String publicationDate) {
        super.comic.setPublicationDate(publicationDate);
    }

    @Override
    public void setPrincipleCharacters(ArrayList<String> principleCharacters) {
        super.comic.setPrincipleCharacters(principleCharacters);
    }
    @Override
    public void setCreators(ArrayList<String> creatorz) {
        super.comic.setCreators(creatorz);
    }

    @Override
    public void setDescription(String description) {
        super.comic.setDescription(description);
    }

    @Override
    public void setValue(float value) {
        super.comic.setValue(value);
    }

    @Override
    public float getValue() {
        return super.comic.getValue();
    }
}
