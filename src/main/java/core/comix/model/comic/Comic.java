package core.comix.model.comic;

import java.util.ArrayList;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import core.comix.model.collections.CollectionCategory;
import core.comix.model.collections.CollectionComponent;
import core.comix.model.search.SearchResults;
import core.comix.model.user.User;
import core.comix.persistence.db.ComicJSONSerializer;
import core.comix.view.pages.gui.GUIPage;
import core.comix.view.pages.ptui.PTUIPage;
import core.comix.model.search.SearchCollector;
@JsonSerialize(using = ComicJSONSerializer.class)
public abstract class Comic implements CollectionComponent, Comparable<Comic>{
    private static PTUIPage TARGET_PTUI_PAGE;
    private static GUIPage TARGET_GUI_PAGE;
    
    protected String storyTitle;
    protected String issueNum;
    protected String publicationDate;
    protected ArrayList<String> principleCharacters;
    protected ArrayList<String> creators;
    protected String description;
    protected float value;
    protected String publisher;
    protected String series;
    protected int volumeNum;

    public Comic(String storyTitle, String issueNum, String publicationDate, ArrayList<String> principleCharacters, ArrayList<String> creators, String description, float value, String publisher, String series, int volumeNum) {
        this.storyTitle = storyTitle;
        this.issueNum = issueNum;
        this.publicationDate = publicationDate;
        this.principleCharacters = principleCharacters;
        this.creators = creators;
        this.description = description;
        this.value = value;
        this.publisher = publisher;
        this.series = series;
        this.volumeNum = volumeNum;
    }
    
    public abstract void performSearch(SearchCollector v, String searchPhrase);

    public UserComic copyToUserComic() {
        return new UserComic(storyTitle, issueNum, publicationDate, principleCharacters, creators, description, value, publisher, series, volumeNum);
    }

    public static void setTargetPTUIPage(PTUIPage targetPage) {
        Comic.TARGET_PTUI_PAGE = targetPage;
    }

    public static void setTargetGUIPage(GUIPage targetPage) {
        Comic.TARGET_GUI_PAGE = targetPage;
    }

    public PTUIPage getTargetPTUIPage() {
        return Comic.TARGET_PTUI_PAGE;
    }

    public GUIPage getTargetGUIPage() {
        return Comic.TARGET_GUI_PAGE;
    }

    public abstract boolean isMarkup();

    public abstract void setIssueNum(String issueNum);

    public abstract void setPublicationDate(String publicationDate);

    public abstract void setPrincipleCharacters(ArrayList<String> principleCharacters);

    public abstract void setCreators(ArrayList<String> creatorz);

    public abstract void setDescription(String description);

    public abstract void setValue(float value);

    public void setParent(CollectionCategory newParent) {}

    public String getNamingString() {
        return storyTitle + " - Value: " + Float.toString(getTotalValue());
    }
    
    // Gets the generic comic menu string. Child classes should call this and add a header, or other specific information.
    public String getMenuString(int scrollAmount) {
        String princpChars = String.join(", ", principleCharacters);
        String creatorz = String.join(", ", creators);
        //TODO check if getting total value will work with grade/slab decorators.
        String menuString = "\nTitle                |" + storyTitle + "\nPublisher            |" + publisher + "\nSeries               |" + series + "\nVolume #             |" + String.valueOf(volumeNum) + "\nIssue #              |"
             + String.valueOf(issueNum) + "\nPublication Date     |" + publicationDate
             + "\nPrinciple Characters |" + princpChars + "\nCreators             |" + creatorz +  "\nDescription          |" + description + "\nValue                |" + value;
        return menuString;
    }

    public String getMenuStringWithValue(float totalValue) {
        String princpChars = String.join(", ", principleCharacters);
        String creatorz = String.join(", ", creators);
        //TODO check if getting total value will work with grade/slab decorators.
        String menuString = "\nTitle                |" + storyTitle + "\nPublisher            |" + publisher + "\nSeries               |" + series + "\nVolume #             |" + String.valueOf(volumeNum) + "\nIssue #              |"
             + String.valueOf(issueNum) + "\nPublication Date     |" + publicationDate
             + "\nPrinciple Characters |" + princpChars + "\nCreators             |" + creatorz +  "\nDescription          |" + description + "\nValue                |" + value + "\nCalculated Value     |" + totalValue; 
        return menuString;
    }

    public float getTotalValue() {
        return value;
    }

    public float getOriginalValue() {
        return value;
    }

    public ArrayList<Comic> getAllComics() {
        ArrayList<Comic> returnArray = new ArrayList<Comic>();
        returnArray.add(this);
        return returnArray;
    }

    public ArrayList<String> getPrincipleCharacters()
    {
        return principleCharacters;
    }

    public ArrayList<String> getCreators()
    {
        return creators;
    }

    public String getDescription()
    {
        return description;
    }

    public int getIssueCount() {
        return 1;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getSeries() {
        return series;
    }

    public int getVolumeNum() {
        return volumeNum;
    }

    public String getStoryTitle() {
        return storyTitle;
    }

    public String getIssueNum(){
        String numericString = issueNum.replaceAll("[^0-9]", "");
        return numericString;
    }

    public String getPublicationDate(){
        return publicationDate;
    }

    public int getGrade() {
        return -1;
    }

    //TODO what is this
    public MarkupOption getMarkup(){
        return (MarkupOption) this;
    }

    public float getValue() {
        return value;
    }

    public String[] getSerializedComic() {
        String storyTitleWrite = getStoryTitle();
        String issueNumWrite = getIssueNum();
        String publicationDateWrite = getPublicationDate();
        ArrayList<String> principleCharactersWrite = getPrincipleCharacters();
        ArrayList<String> creatorsWrite = getCreators();
        String descriptionWrite = getDescription();
        Float valueWrite = getValue();
        String publisherWrite = getPublisher();
        String seriesWrite = getSeries();
        int volumeNumWrite = getVolumeNum();

        String grade = "";
        String slabbed = "";
        ArrayList<String> signatures = new ArrayList<>();
        ArrayList<String> authenticates = new ArrayList<>();
        if(isMarkup()) {
            MarkupOption markup = (MarkupOption) this;
            if (markup.isGraded()) {
                grade = Integer.toString(markup.getGrade());
            }
            if (markup.isSlabbed()) {
                slabbed = "True";
            }
            if (markup.isSigned() && !(markup.isAuthenticated())) {
                Signed signed = (Signed) markup;
                signatures = signed.getSignatures(signatures);
            }
            if (markup.isAuthenticated()) {
                Authenticated authenticated = (Authenticated) markup;
                signatures = authenticated.getSignatures(signatures);
                authenticates = authenticated.getAuthenticates(authenticates);
            }
        }

        return new String[]{storyTitleWrite, issueNumWrite, publicationDateWrite, String.join(" | ", principleCharactersWrite), String.join(" | ", creatorsWrite), descriptionWrite, Float.toString(valueWrite), publisherWrite, seriesWrite, Integer.toString(volumeNumWrite), grade, slabbed, String.join(" | ", signatures), String.join(" | ", authenticates)};

    }

    @Override
    public int compareTo(Comic other) {
        return this.getIssueNum().compareTo(other.getIssueNum());
    }
}
