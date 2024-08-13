package core.comix.view;
import java.io.IOException;

import core.comix.controller.CommandInputException;
import core.comix.model.collections.CollectionComponent;
import core.comix.view.pages.ptui.PTUIPage;

public class PTUI extends UI {
    
    public PTUI (PTUIPage currentPage){
        this.currentPage = currentPage;
    }

    public CollectionComponent updateCurrentItem(CollectionComponent item) {
        resetScroll();
        switchPage(item.getTargetPTUIPage());
        try {
            currentPage.updateCurrentItem(item);
        } catch (IOException e) {}
        return item;
    }
}
