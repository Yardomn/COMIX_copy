package core.comix.view.pages.ptui;

public class EditPage extends PTUIPage {
    public EditPage (){
        super();
    }

    public void displayPage(){
        System.out.println("------------------------------");
        System.out.println("Comic Page");
        System.out.println("------------------------------");
        System.out.println(currentItem.getMenuString(scrollAmount));
        System.out.println("------------------------------");
    }
}
