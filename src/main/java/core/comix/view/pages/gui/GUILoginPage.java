package core.comix.view.pages.gui;

import java.io.IOException;

import core.comix.GUILauncher;
import core.comix.controller.CommandInputException;
import core.comix.controller.commands.LoginCommand;
import core.comix.controller.commands.SignupCommand;
import core.comix.model.user.CurrentUser;
import core.comix.model.user.LoginManager;
import core.comix.view.GUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class GUILoginPage extends GUIPage {
    private LoginCommand loginCommand;
    private SignupCommand signupCommand;

    public GUILoginPage(GUILauncher launcher, GUI gui, LoginManager loginManager) throws IOException {
        super(launcher, gui);
        this.loginCommand = new LoginCommand(gui, loginManager);
        this.signupCommand = new SignupCommand(gui, loginManager);
        setupScene();
    }
    
    protected void setupScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Loginpage.fxml"));
        Parent root = loader.load();
        LoginPageController controller = loader.<LoginPageController>getController();
        controller.initparams(gui, launcher, this.loginCommand, this.signupCommand);
        myScene = new Scene(root, 600, 400);
    }
}
