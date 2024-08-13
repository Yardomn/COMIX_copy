package core.comix.view;

import java.io.IOException;

import core.comix.controller.CommandInputException;
import core.comix.model.collections.CollectionComponent;
import core.comix.view.pages.Page;

public abstract class UI {
    protected Page currentPage;
    protected static int offset = 10;

    public void switchPage(Page target){
        currentPage = target;
    }

    public void displayPage() {
        try {
            currentPage.displayPage();
        } catch (IOException e) {}
    }

    public CollectionComponent getCurrentItem(){
        return currentPage.getCurrentItem();
    }

    public abstract CollectionComponent updateCurrentItem(CollectionComponent item);
    
    public void changeScroll(boolean up) throws CommandInputException{
        if(up) {
            currentPage.changeScroll(-offset);
        } else {
            currentPage.changeScroll(offset);
        }
    }

    public void resetScroll(){
        try {
            currentPage.resetScroll();
        } catch (Exception e) {}
    }
    
    public void setOffset(int newOffset){
        offset = newOffset;
    }

    public Page getCurrentPage(){
        return this.currentPage;
    }

    public int getScrollAmount() {
        return currentPage.getScrollAmount();
    }

    public int getOffset(){
        return offset;
    }
}
