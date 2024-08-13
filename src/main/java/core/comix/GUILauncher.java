package core.comix;

import java.io.File;

import java.io.IOException;

import core.comix.controller.CommandInputException;
import core.comix.controller.commands.EditCommand;
import core.comix.controller.commands.RedoCommand;
import core.comix.controller.commands.UndoCommand;
import core.comix.model.collections.database.FullCollection;
import core.comix.model.collections.personal.PersonalCollection;
import core.comix.model.comic.Comic;
import core.comix.model.comic.DBComic;
import core.comix.model.search.PartialSearch;
import core.comix.model.search.SearchResults;
import core.comix.model.user.CurrentUser;
import core.comix.model.user.GuestUser;
import core.comix.model.user.LoginManager;
import core.comix.persistence.db.CustomCSVEditor;
import core.comix.persistence.pc.CSVCollectionManager;
import core.comix.view.GUI;
import core.comix.view.pages.gui.GUICollectionPage;
import core.comix.view.pages.gui.GUIComicPage;
import core.comix.view.pages.gui.GUILoginPage;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class GUILauncher extends Application {

    private Stage primaryStage;
    private Stage popupStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("COMIX Application");
        this.primaryStage = primaryStage;

        CustomCSVEditor dbreader = new CustomCSVEditor("src/main/resources/comics.csv");
        CSVCollectionManager pcreader = new CSVCollectionManager("src/main/resources/userCollections", CurrentUser.getInstance());

        FullCollection fc = new FullCollection();
        fc.setSearchType(new PartialSearch());
        DBComic.setParent(fc);
        PersonalCollection pc = new PersonalCollection(pcreader);
        pc.setSearchType(new PartialSearch());

        dbreader.readComics(fc);
        pcreader.loadCollectionFromStorage(pc);

        LoginManager loginManager = new LoginManager(fc, pcreader);

        GUI gui = new GUI();

        GUILoginPage loginPage = new GUILoginPage(this, gui, loginManager);
        gui.setLoginPage(loginPage);
        gui.setCurrentPage(loginPage);

        UndoCommand undoCommand = new UndoCommand(gui);
        RedoCommand redoCommand = new RedoCommand(gui);

        GUICollectionPage collectionPage = new GUICollectionPage(this, gui, undoCommand, redoCommand);
        GUIComicPage comicPage = new GUIComicPage(this, gui, undoCommand, redoCommand);
        FullCollection.setTargetGUIPage(collectionPage);
        PersonalCollection.setTargetGUIPage(collectionPage);
        SearchResults.setTargetGUIPage(collectionPage);
        Comic.setTargetGUIPage(comicPage);
        gui.displayPage();

        popupStage = new Stage();
        popupStage.initStyle(StageStyle.UNDECORATED);
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.initOwner(primaryStage);
        popupStage.hide();

        
        gui.displayPage();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    public void switchScene(Scene target) {
        primaryStage.setScene(target);
        primaryStage.show();
    }

    public void showPopup(Scene popup) {
        
        popup.getRoot().setStyle("-fx-border-color: black; -fx-border-radius: 2px; -fx-background-radius: 2px;");
        popupStage.setScene(popup);
        popupStage.showAndWait();
    }

    public void hidePopup() {
        popupStage.hide();
    }
    
    // Shows a default text popup, with a Label displaying the message and a close button
    // Should be used as a reference for all other popups
    public void showTextPopup(String error) {
        VBox popupBox = new VBox();
        Label popupText = new Label(error);
        popupText.setMaxWidth(200);
        popupText.setWrapText(true);
        popupText.setPadding(new Insets(0,0,10,0));

        popupBox.getChildren().add(popupText);
        Button closePopup = new Button("Close");
        closePopup.setOnAction(new EventHandler<ActionEvent>() {
    
                @Override
                public void handle(ActionEvent event) {
                    hidePopup();
                }
            });
        popupBox.getChildren().add(closePopup);
        popupBox.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        popupBox.setAlignment(Pos.CENTER);
        popupBox.setPadding(new Insets(10));
        
        showPopup(new Scene(popupBox, 200, 200));
    }

    public void showEditDialog(String fieldName, String currentValue, EditCommand editCommand) {
        VBox popupBox = new VBox();
        Label popupText = new Label("Editing "+fieldName);
        popupText.setMaxWidth(200);
        popupText.setWrapText(true);
        popupText.setPadding(new Insets(0,0,10,0));

        popupBox.getChildren().add(popupText);

        TextField editText = new TextField(currentValue);
        editText.setMaxWidth(200);
        editText.setPadding(new Insets(0,0,10,0));

        popupBox.getChildren().add(editText);

        Button savePopup = new Button("Save");
        savePopup.setOnAction(new EventHandler<ActionEvent>() {
    
                @Override
                public void handle(ActionEvent event) {
                    try {
                        editCommand.execute(fieldName, editText.getText());
                    }
                    catch (CommandInputException e) {
                        showTextPopup(e.getMessage());
                    }
                    hidePopup();
                }
            });
        popupBox.getChildren().add(savePopup);
        popupBox.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        popupBox.setAlignment(Pos.CENTER);
        popupBox.setPadding(new Insets(10));
        
        showPopup(new Scene(popupBox, 200, 200));
    }

    public File showFileChooserOpen(FileChooser fc) {
        return fc.showOpenDialog(primaryStage);
    }

    public File showFileChooserSave(FileChooser fc) {
        return fc.showSaveDialog(primaryStage);
    }
}
