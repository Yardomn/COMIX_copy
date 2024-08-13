package core.comix.model.comic;

import java.util.ArrayList;

import core.comix.model.collections.CollectionCategory;
import core.comix.model.search.SearchCollector;

public class Slabbed extends MarkupOption {

    public Slabbed(Comic comic) {
        super(comic);
    }

    public float getTotalValue() {
        return super.comic.getTotalValue() * 2f;
    }

    @Override
    public void performSearch(SearchCollector v, String searchPhrase) {
        super.comic.performSearch(v, searchPhrase);
    }

    public boolean isGraded(){
        return true;
    }

    public boolean isSlabbed(){
        return true;
    }

    public boolean isSigned(){
        if(super.comic.isMarkup()) {
            MarkupOption markup = (MarkupOption) super.comic;
            return markup.isSigned();
        } else {
            return false;
        }
    }

    public boolean isAuthenticated(){
        if(super.comic.isMarkup()) {
            MarkupOption markup = (MarkupOption) super.comic;
            return markup.isAuthenticated();
        } else {
            return false;
        }
    }

    public boolean checkSigned(String name) {
        if(super.comic.isMarkup()) {
            MarkupOption markup = (MarkupOption) super.comic;
            return markup.checkSigned(name);
        }else {
            return false;
        }
    } 
    
    public boolean checkAuthenticated(String name) {
        if(super.comic.isMarkup()) {
            MarkupOption markup = (MarkupOption) super.comic;
            return markup.checkAuthenticated(name);
        }else {
            return false;
        }
    }  

    public int getGrade() {
        return super.comic.getGrade();
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
        if(super.comic.isMarkup()) {
            MarkupOption markup = (MarkupOption) super.comic;
            return markup.getAuthenticates(authenticates);
        } else {
            return authenticates;
        }
    }

    @Override
    public String getMenuString(int scrollAmount) {
        return super.comic.getMenuStringWithValue(getTotalValue())+ "\nSlabbed              |"; 
    }

    @Override
    public String getMenuStringWithValue(float totalValue) {
        return super.comic.getMenuStringWithValue(totalValue)+ "\nSlabbed              |";
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
