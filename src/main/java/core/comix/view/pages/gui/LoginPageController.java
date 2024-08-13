package core.comix.view.pages.gui;

import java.io.IOException;

import core.comix.GUILauncher;
import core.comix.controller.CommandInputException;
import core.comix.controller.commands.LoginCommand;
import core.comix.controller.commands.SignupCommand;
import core.comix.model.user.CurrentUser;
import core.comix.view.GUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginPageController {
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Button loginButton;
    @FXML
    private Button guestButton;
    @FXML
    private Button signupButton;

    private LoginCommand loginCommand;
    private SignupCommand signupCommand;
    private GUI gui;
    private GUILauncher launcher;
    
    public LoginPageController(){
    }
    public void initparams(GUI gui, GUILauncher launcher, LoginCommand loginCommand, SignupCommand signupCommand){
        this.gui = gui;
        this.launcher = launcher;
        this.loginCommand = loginCommand;
        this.signupCommand = signupCommand;
    }
    public void loginHandle() throws IOException {
        try {
            loginCommand.execute(username.getText(), password.getText());
            gui.updateCurrentItem(CurrentUser.getPersonalCollection());
        } catch (CommandInputException e) {
            launcher.showTextPopup(e.getMessage());
        }
    }
    public void guestHandle(ActionEvent event) throws IOException {
        gui.updateCurrentItem(CurrentUser.getDatabase());
    }
    public void signupHandle(ActionEvent event) throws IOException {
        try {
            System.out.println(gui);
            signupCommand.execute(username.getText(), password.getText());
            gui.updateCurrentItem(CurrentUser.getPersonalCollection());
        } catch (CommandInputException e) {
            launcher.showTextPopup(e.getMessage());
        }
    }
}
