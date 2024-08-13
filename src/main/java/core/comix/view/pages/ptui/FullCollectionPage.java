package core.comix.view.pages.ptui;

public class FullCollectionPage extends PTUIPage {
    public FullCollectionPage (){
        super();
    }

    public void displayPage(){
        System.out.println("------------------------------");
        System.out.println("Full Collection Page");
        System.out.println("------------------------------");
        System.out.println(currentItem.getMenuString(scrollAmount));
    }
}
