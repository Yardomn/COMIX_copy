package core.comix.controller.commands;
import core.comix.controller.Command;
import core.comix.view.UI;

public class QuitCommand extends Command {
    public QuitCommand(UI ui){
        super(ui);
    }

    public void execute(){
        System.exit(0);
    }
}
