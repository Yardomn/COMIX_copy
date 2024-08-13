package core.comix.view.pages.ptui;

public class PersonalCollectionPage extends PTUIPage {
    public PersonalCollectionPage (){
        super();
    }

    public void displayPage(){
        System.out.println("------------------------------");
        System.out.println("Personal Collection Page");
        System.out.println("------------------------------");
        System.out.println(currentItem.getMenuString(scrollAmount));
        System.out.println("------------------------------");
    }
}
