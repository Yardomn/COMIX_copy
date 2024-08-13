package core.comix.view.pages;

import java.io.IOException;

import core.comix.controller.CommandInputException;
import core.comix.model.collections.CollectionCategory;
import core.comix.model.collections.CollectionComponent;

public abstract class Page {
    protected CollectionComponent currentItem;
    protected int scrollAmount;

    public Page(){
        scrollAmount = 0;
    }

    public abstract void displayPage() throws IOException;

    public CollectionComponent getCurrentItem(){
        return currentItem;
    }

    public CollectionComponent updateCurrentItem(CollectionComponent item) throws IOException {
        currentItem = item;
        displayPage();
        return item;
    }

    public void changeScroll(int offset) throws CommandInputException{
        if (scrollAmount + offset < 0) {
            throw new CommandInputException("Already scrolled all the way up");
        }
        if (scrollAmount > ((CollectionCategory)currentItem).getChildren().size()) {
            throw new CommandInputException("Already scrolled all the way down");
        }
        scrollAmount += offset;
    }

    public void resetScroll(){
        scrollAmount = 0;
    }

    public int getScrollAmount() {
        return scrollAmount;
    }
}
