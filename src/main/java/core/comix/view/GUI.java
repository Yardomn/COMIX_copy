package core.comix.view;

import java.io.IOException;

import core.comix.model.collections.CollectionComponent;
import core.comix.view.pages.gui.GUILoginPage;
import core.comix.view.pages.gui.GUIPage;

public class GUI extends UI {

    private GUILoginPage loginPage;
    
    public GUI (){

    }

    public CollectionComponent updateCurrentItem(CollectionComponent item) {
        resetScroll();
        if (item == null) {
            switchPage(loginPage);
        }
        else {
            switchPage(item.getTargetGUIPage());
        }
        try {
            currentPage.updateCurrentItem(item);
        } catch (IOException e) {}
        return item;
    }

    public void setLoginPage(GUILoginPage loginPage) {
        this.loginPage = loginPage;
    }

    public void setCurrentPage(GUIPage page) {
        currentPage = page;
    }

    public int getScrollAmount() {
        return currentPage.getScrollAmount();
    }
}
