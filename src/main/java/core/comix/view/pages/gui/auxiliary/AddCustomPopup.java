package core.comix.view.pages.gui.auxiliary;

import java.io.File;
import java.util.ArrayList;

import core.comix.GUILauncher;
import core.comix.controller.CommandInputException;
import core.comix.controller.commands.AddCustomCommand;
import core.comix.controller.commands.ExportDBCommand;
import core.comix.controller.commands.ExportPCCommand;
import core.comix.controller.commands.ImportDBCommand;
import core.comix.controller.commands.ImportPCCommand;
import core.comix.view.GUI;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;

public class AddCustomPopup {
    private GUILauncher launcher;
    private GUI gui;
    private AddCustomCommand addCustomCommand;

    public AddCustomPopup(GUILauncher launcher, GUI gui, AddCustomCommand addCustomCommand) {
        this.gui = gui;
        this.launcher = launcher;
        this.addCustomCommand = addCustomCommand;
    }

    public void addCustomPopup() {
        VBox popupBox = new VBox();
        Label popupText = new Label("Add your own comic");
        popupText.setMaxWidth(200);
        popupText.setWrapText(true);
        popupText.setPadding(new Insets(0,0,10,0));

        popupBox.getChildren().add(popupText);

        GridPane mainPane = new GridPane();
        Text storyText = new Text("Story Title:");
        storyText.setTextAlignment(TextAlignment.RIGHT);
        TextField storyTitle = new TextField();
        mainPane.add(storyText, 0, 0);
        mainPane.add(storyTitle, 1, 0);
        Text publisherText = new Text("Publisher:");
        publisherText.setTextAlignment(TextAlignment.RIGHT);
        TextField publisher = new TextField();
        mainPane.add(publisherText, 0, 1);
        mainPane.add(publisher, 1, 1);
        Text seriesText = new Text("Series:");
        seriesText.setTextAlignment(TextAlignment.RIGHT);
        TextField series = new TextField();
        mainPane.add(seriesText, 0, 2);
        mainPane.add(series, 1, 2);
        Text volumeText = new Text("Volume Number:");
        volumeText.setTextAlignment(TextAlignment.RIGHT);
        TextField volumeNum = new TextField();
        mainPane.add(volumeText, 0, 3);
        mainPane.add(volumeNum, 1, 3);
        Text issueText = new Text("Issue Number:");
        issueText.setTextAlignment(TextAlignment.RIGHT);
        TextField issueNum = new TextField();
        mainPane.add(issueText, 0, 4);
        mainPane.add(issueNum, 1, 4);
        Text pubText = new Text("Publication Date:");
        pubText.setTextAlignment(TextAlignment.RIGHT);
        TextField publication = new TextField();
        mainPane.add(pubText, 0, 5);
        mainPane.add(publication, 1, 5);
        Text creatorsText = new Text("Creators:");
        creatorsText.setTextAlignment(TextAlignment.RIGHT);
        TextField creators = new TextField();
        mainPane.add(creatorsText, 0, 6);
        mainPane.add(creators, 1, 6);
        Text charactersText = new Text("Characters:");
        charactersText.setTextAlignment(TextAlignment.RIGHT);
        TextField characters = new TextField();
        mainPane.add(charactersText, 0, 7);
        mainPane.add(characters, 1, 7);
        Text descriptionText = new Text("Description:");
        descriptionText.setTextAlignment(TextAlignment.RIGHT);
        TextField description = new TextField();
        mainPane.add(descriptionText, 0, 8);
        mainPane.add(description, 1, 8);
        Text valueText = new Text("Value:");
        valueText.setTextAlignment(TextAlignment.RIGHT);
        TextField value = new TextField();
        mainPane.add(valueText, 0, 9);
        mainPane.add(value, 1, 9);
        popupBox.getChildren().add(mainPane);

        Button saveButton = new Button("Save");
        saveButton.setOnAction(new EventHandler<ActionEvent>() {
    
                @Override
                public void handle(ActionEvent event) {
                    try {
                        float valueInt;
                        if (value.getText().equals("")) {
                            valueInt = 0;
                        }
                        else {
                            valueInt = Float.parseFloat(value.getText());
                        }
                        ArrayList<String> creatorsList = new ArrayList<String>();
                        for (String i : creators.getText().split(" \\| ")) {
                            creatorsList.add(i);
                        }
                        ArrayList<String> charactersList = new ArrayList<String>();
                        for (String i : characters.getText().split(" \\| ")) {
                            charactersList.add(i);
                        }
                        addCustomCommand.execute(storyTitle.getText(), publisher.getText(), series.getText(), Integer.parseInt(volumeNum.getText()), issueNum.getText(), publication.getText(), creatorsList, charactersList, description.getText(), valueInt);
                    } catch (CommandInputException e) {
                        launcher.hidePopup();
                        launcher.showTextPopup(e.getMessage());
                    } catch (NumberFormatException nfe) {
                        launcher.hidePopup();
                        launcher.showTextPopup("Failed to Add Comic: Value must be a float and Volume Number must be an integer.");
                    }
                    launcher.hidePopup();
                }
            });

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
    
                @Override
                public void handle(ActionEvent event) {
                    launcher.hidePopup();
                }
            });

        HBox buttons = new HBox();
        buttons.setAlignment(Pos.CENTER);
        buttons.setPadding(new Insets(5,0,0,0));
        buttons.getChildren().add(cancelButton);
        buttons.getChildren().add(saveButton);
        popupBox.getChildren().add(buttons);

        popupBox.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        popupBox.setAlignment(Pos.CENTER);
        popupBox.setPadding(new Insets(10));
        
        launcher.showPopup(new Scene(popupBox, 220, 360));
    }
}
