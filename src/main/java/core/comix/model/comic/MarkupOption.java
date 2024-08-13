package core.comix.model.comic;

import java.util.ArrayList;

public abstract class MarkupOption extends Comic{
    protected Comic comic;

    public MarkupOption(Comic comic){
        super(comic.storyTitle, comic.issueNum, comic.publicationDate, comic.principleCharacters, comic.creators, comic.description, comic.value, comic.publisher, comic.series, comic.volumeNum);
        this.comic = comic;
    }

    public float getTotalValue(){
        return comic.getTotalValue();
    }

    public Comic getChild(){
        return comic;
    }

    public abstract int getGrade();

    public abstract ArrayList<String> getSignatures(ArrayList<String> signatures);

    public abstract ArrayList<String> getAuthenticates(ArrayList<String> authenticates);

    public boolean isMarkup(){
        return true;
    };

    public abstract boolean isGraded();

    public abstract boolean isSlabbed();

    public abstract boolean isSigned();

    public abstract boolean isAuthenticated();

    public abstract boolean checkSigned(String name);

    public abstract boolean checkAuthenticated(String name);

    public float getOriginalValue() {
        return comic.getOriginalValue();
    }
}
