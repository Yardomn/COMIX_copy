package core.comix.controller.commands;

import core.comix.controller.Command;
import core.comix.controller.CommandInputException;
import core.comix.model.user.ActiveUser;
import core.comix.model.user.LoginManager;
import core.comix.view.UI;

public class LoginCommand extends Command {

    private LoginManager loginManager;

    public LoginCommand(UI ui, LoginManager loginManager) {
        super(ui);
        this.loginManager = loginManager;
    }

    public void execute(String username, String password) throws CommandInputException {
        ActiveUser result = loginManager.tryLogin(username, password);
        if (result == null) {
            throw new CommandInputException("Login Failed: Account does not exist");
        }
    }
}
