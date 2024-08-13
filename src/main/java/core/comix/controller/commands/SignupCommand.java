package core.comix.controller.commands;

import core.comix.controller.Command;
import core.comix.controller.CommandInputException;
import core.comix.model.user.LoginManager;
import core.comix.view.UI;

public class SignupCommand extends Command {

    private LoginManager loginManager;

    public SignupCommand(UI ui, LoginManager loginManager) {
        super(ui);
        this.loginManager = loginManager;
    }

    public void execute(String username, String password) throws CommandInputException {
        boolean result = loginManager.createActiveUser(username, password);
        if (!result) {
            throw new CommandInputException("Siugnup Failed: Account already exists");
        }
    }
}
