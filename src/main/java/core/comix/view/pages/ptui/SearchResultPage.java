package core.comix.view.pages.ptui;

public class SearchResultPage extends PTUIPage {
    public SearchResultPage (){
        super();
    }

    public void displayPage(){
        System.out.println("------------------------------");
        System.out.println("Search Results Page");
        System.out.println("------------------------------");
        System.out.println(currentItem.getMenuString(scrollAmount));
        System.out.println("------------------------------");
    }
}
