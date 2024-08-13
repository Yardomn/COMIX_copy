package core.comix.view.pages.gui.auxiliary;

import java.io.File;
import java.io.IOException;

import core.comix.GUILauncher;
import core.comix.controller.commands.ExportDBCommand;
import core.comix.controller.CommandInputException;
import core.comix.controller.commands.ExportPCCommand;
import core.comix.controller.commands.ImportDBCommand;
import core.comix.controller.commands.ImportPCCommand;
import core.comix.view.GUI;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

public class ImportExportPopup {

    private GUILauncher launcher;
    private GUI gui;
    private ImportPCCommand importPCCommand;
    private ImportDBCommand importDBCommand;
    private ExportPCCommand exportPCCommand;
    private ExportDBCommand exportDBCommand;

    public ImportExportPopup(GUILauncher launcher, GUI gui, ImportPCCommand importPCCommand, ImportDBCommand importDBCommand, ExportPCCommand exportPCCommand, ExportDBCommand exportDBCommand) {
        this.gui = gui;
        this.launcher = launcher;
        this.importDBCommand = importDBCommand;
        this.importPCCommand = importPCCommand;
        this.exportDBCommand = exportDBCommand;
        this.exportPCCommand = exportPCCommand;
    }

    public void importPCPopup() {
        VBox popupBox = new VBox();
        Label popupText = new Label("Choose a filetype to import Personal Collection from");
        popupText.setMaxWidth(200);
        popupText.setWrapText(true);
        popupText.setPadding(new Insets(0,0,10,0));

        popupBox.getChildren().add(popupText);

        Button csvOption = new Button("CSV");
        csvOption.setOnAction(new EventHandler<ActionEvent>() {
    
                @Override
                public void handle(ActionEvent event) {
                    try {
                        FileChooser fileChooser = new FileChooser();
                        fileChooser.setTitle("Importing Personal Collection from CSV");
                        fileChooser.getExtensionFilters().addAll(
                            new FileChooser.ExtensionFilter("All Files", "*.*"),
                            new FileChooser.ExtensionFilter("CSV", "*.csv"));
                        File file = launcher.showFileChooserOpen(fileChooser);

                        importPCCommand.execute("csv", file.getAbsolutePath());
                    } catch (CommandInputException e) {
                        launcher.hidePopup();
                        launcher.showTextPopup(e.getMessage());
                    }
                    launcher.hidePopup();
                }
            });
        popupBox.getChildren().add(csvOption);

        Button jsonOption = new Button("JSON");
        jsonOption.setOnAction(new EventHandler<ActionEvent>() {
    
                @Override
                public void handle(ActionEvent event) {
                    try {
                        FileChooser fileChooser = new FileChooser();
                        fileChooser.setTitle("Importing Personal Collection from JSON");
                        fileChooser.getExtensionFilters().addAll(
                            new FileChooser.ExtensionFilter("All Files", "*.*"),
                            new FileChooser.ExtensionFilter("JSON", "*.json"));
                        File file = launcher.showFileChooserOpen(fileChooser);

                        importPCCommand.execute("json", file.getAbsolutePath());

                    } catch (CommandInputException e) {
                        launcher.hidePopup();
                        launcher.showTextPopup(e.getMessage());
                    }
                    launcher.hidePopup();
                }
            });
        popupBox.getChildren().add(jsonOption);

        Button xmlOption = new Button("XML");
        xmlOption.setOnAction(new EventHandler<ActionEvent>() {
    
                @Override
                public void handle(ActionEvent event) {
                    try {
                        FileChooser fileChooser = new FileChooser();
                        fileChooser.setTitle("Importing Personal Collection from XML");
                        fileChooser.getExtensionFilters().addAll(
                            new FileChooser.ExtensionFilter("All Files", "*.*"),
                            new FileChooser.ExtensionFilter("XML", "*.xml"));
                        File file = launcher.showFileChooserOpen(fileChooser);

                        importPCCommand.execute("xml", file.getAbsolutePath());

                    } catch (CommandInputException e) {
                        launcher.hidePopup();
                        launcher.showTextPopup(e.getMessage());
                    }
                    launcher.hidePopup();
                }
            });
        popupBox.getChildren().add(xmlOption);

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
    
                @Override
                public void handle(ActionEvent event) {
                    launcher.hidePopup();
                }
            });
        popupBox.getChildren().add(cancelButton);

        popupBox.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        popupBox.setAlignment(Pos.CENTER);
        popupBox.setPadding(new Insets(10));
        
        launcher.showPopup(new Scene(popupBox, 200, 200));
    }

    public void importDBPopup() {
        VBox popupBox = new VBox();
        Label popupText = new Label("Choose a filetype to import Database from");
        popupText.setMaxWidth(200);
        popupText.setWrapText(true);
        popupText.setPadding(new Insets(0,0,10,0));

        popupBox.getChildren().add(popupText);

        Button csvOption = new Button("CSV");
        csvOption.setOnAction(new EventHandler<ActionEvent>() {
    
                @Override
                public void handle(ActionEvent event) {
                    try {
                        FileChooser fileChooser = new FileChooser();
                        fileChooser.setTitle("Importing Database from CSV");
                        fileChooser.getExtensionFilters().addAll(
                            new FileChooser.ExtensionFilter("All Files", "*.*"),
                            new FileChooser.ExtensionFilter("CSV", "*.csv"));
                        File file = launcher.showFileChooserOpen(fileChooser);

                        importDBCommand.execute("csv", file.getAbsolutePath());

                    } catch (CommandInputException e) {
                        launcher.hidePopup();
                        launcher.showTextPopup(e.getMessage());
                    }
                    launcher.hidePopup();
                }
            });
        popupBox.getChildren().add(csvOption);

        Button jsonOption = new Button("JSON");
        jsonOption.setOnAction(new EventHandler<ActionEvent>() {
    
                @Override
                public void handle(ActionEvent event) {
                    try {
                        FileChooser fileChooser = new FileChooser();
                        fileChooser.setTitle("Importing Database from JSON");
                        fileChooser.getExtensionFilters().addAll(
                            new FileChooser.ExtensionFilter("All Files", "*.*"),
                            new FileChooser.ExtensionFilter("JSON", "*.json"));
                        File file = launcher.showFileChooserOpen(fileChooser);

                        importDBCommand.execute("json", file.getAbsolutePath());

                    } catch (CommandInputException e) {
                        launcher.hidePopup();
                        launcher.showTextPopup(e.getMessage());
                    }
                    launcher.hidePopup();
                }
            });
        popupBox.getChildren().add(jsonOption);

        Button xmlOption = new Button("XML");
        xmlOption.setOnAction(new EventHandler<ActionEvent>() {
    
                @Override
                public void handle(ActionEvent event) {
                    try {
                        FileChooser fileChooser = new FileChooser();
                        fileChooser.setTitle("Importing Database from XML");
                        fileChooser.getExtensionFilters().addAll(
                            new FileChooser.ExtensionFilter("All Files", "*.*"),
                            new FileChooser.ExtensionFilter("XML", "*.xml"));
                        File file = launcher.showFileChooserOpen(fileChooser);

                        importDBCommand.execute("xml", file.getAbsolutePath());

                    } catch (CommandInputException e) {
                        launcher.hidePopup();
                        launcher.showTextPopup(e.getMessage());
                    }
                    launcher.hidePopup();
                }
            });
        popupBox.getChildren().add(xmlOption);

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
    
                @Override
                public void handle(ActionEvent event) {
                    launcher.hidePopup();
                }
            });
        popupBox.getChildren().add(cancelButton);

        popupBox.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        popupBox.setAlignment(Pos.CENTER);
        popupBox.setPadding(new Insets(10));
        
        launcher.showPopup(new Scene(popupBox, 200, 200));
    }

    public void exportPCPopup() {
        VBox popupBox = new VBox();
        Label popupText = new Label("Choose a filetype to export Personal Collection to");
        popupText.setMaxWidth(200);
        popupText.setWrapText(true);
        popupText.setPadding(new Insets(0,0,10,0));

        popupBox.getChildren().add(popupText);

        Button csvOption = new Button("CSV");
        csvOption.setOnAction(new EventHandler<ActionEvent>() {
    
                @Override
                public void handle(ActionEvent event) {
                    try {
                        FileChooser fileChooser = new FileChooser();
                        fileChooser.setTitle("Exporting Personal Collection to CSV");
                        fileChooser.getExtensionFilters().addAll(
                            new FileChooser.ExtensionFilter("All Files", "*.*"),
                            new FileChooser.ExtensionFilter("CSV", "*.csv"));
                        File file = launcher.showFileChooserSave(fileChooser);

                        exportPCCommand.execute("csv", file.getAbsolutePath());
                    } catch (CommandInputException e) {
                        launcher.hidePopup();
                        launcher.showTextPopup(e.getMessage());
                    }
                    launcher.hidePopup();
                }
            });
        popupBox.getChildren().add(csvOption);

        Button jsonOption = new Button("JSON");
        jsonOption.setOnAction(new EventHandler<ActionEvent>() {
    
                @Override
                public void handle(ActionEvent event) {
                    try {
                        FileChooser fileChooser = new FileChooser();
                        fileChooser.setTitle("Exporting Personal Collection to JSON");
                        fileChooser.getExtensionFilters().addAll(
                            new FileChooser.ExtensionFilter("All Files", "*.*"),
                            new FileChooser.ExtensionFilter("JSON", "*.json"));
                        File file = launcher.showFileChooserSave(fileChooser);

                        exportPCCommand.execute("json", file.getAbsolutePath());

                    } catch (CommandInputException e) {
                        launcher.hidePopup();
                        launcher.showTextPopup(e.getMessage());
                    }
                    launcher.hidePopup();
                }
            });
        popupBox.getChildren().add(jsonOption);

        Button xmlOption = new Button("XML");
        xmlOption.setOnAction(new EventHandler<ActionEvent>() {
    
                @Override
                public void handle(ActionEvent event) {
                    try {
                        FileChooser fileChooser = new FileChooser();
                        fileChooser.setTitle("Exporting Personal Collection to XML");
                        fileChooser.getExtensionFilters().addAll(
                            new FileChooser.ExtensionFilter("All Files", "*.*"),
                            new FileChooser.ExtensionFilter("XML", "*.xml"));
                        File file = launcher.showFileChooserSave(fileChooser);

                        exportPCCommand.execute("xml", file.getAbsolutePath());

                    } catch (CommandInputException e) {
                        launcher.hidePopup();
                        launcher.showTextPopup(e.getMessage());
                    }
                    launcher.hidePopup();
                }
            });
        popupBox.getChildren().add(xmlOption);

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
    
                @Override
                public void handle(ActionEvent event) {
                    launcher.hidePopup();
                }
            });
        popupBox.getChildren().add(cancelButton);

        popupBox.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        popupBox.setAlignment(Pos.CENTER);
        popupBox.setPadding(new Insets(10));
        
        launcher.showPopup(new Scene(popupBox, 200, 200));
    }

    public void exportDBPopup() {
        VBox popupBox = new VBox();
        Label popupText = new Label("Choose a filetype to export Database to");
        popupText.setMaxWidth(200);
        popupText.setWrapText(true);
        popupText.setPadding(new Insets(0,0,10,0));

        popupBox.getChildren().add(popupText);

        Button csvOption = new Button("CSV");
        csvOption.setOnAction(new EventHandler<ActionEvent>() {
    
                @Override
                public void handle(ActionEvent event) {
                    try {
                        FileChooser fileChooser = new FileChooser();
                        fileChooser.setTitle("Exporting Database to CSV");
                        fileChooser.getExtensionFilters().addAll(
                            new FileChooser.ExtensionFilter("All Files", "*.*"),
                            new FileChooser.ExtensionFilter("CSV", "*.csv"));
                        File file = launcher.showFileChooserSave(fileChooser);

                        exportDBCommand.execute("csv", file.getAbsolutePath());

                    } catch (CommandInputException e) {
                        launcher.hidePopup();
                        launcher.showTextPopup(e.getMessage());
                    }
                    launcher.hidePopup();
                }
            });
        popupBox.getChildren().add(csvOption);

        Button jsonOption = new Button("JSON");
        jsonOption.setOnAction(new EventHandler<ActionEvent>() {
    
                @Override
                public void handle(ActionEvent event) {
                    try {
                        FileChooser fileChooser = new FileChooser();
                        fileChooser.setTitle("Exporting Database to JSON");
                        fileChooser.getExtensionFilters().addAll(
                            new FileChooser.ExtensionFilter("All Files", "*.*"),
                            new FileChooser.ExtensionFilter("JSON", "*.json"));
                        File file = launcher.showFileChooserSave(fileChooser);

                        exportDBCommand.execute("json", file.getAbsolutePath());

                    } catch (CommandInputException e) {
                        launcher.hidePopup();
                        launcher.showTextPopup(e.getMessage());
                    }
                    launcher.hidePopup();
                }
            });
        popupBox.getChildren().add(jsonOption);

        Button xmlOption = new Button("XML");
        xmlOption.setOnAction(new EventHandler<ActionEvent>() {
    
                @Override
                public void handle(ActionEvent event) {
                    try {
                        FileChooser fileChooser = new FileChooser();
                        fileChooser.setTitle("Exporting Database to XML");
                        fileChooser.getExtensionFilters().addAll(
                            new FileChooser.ExtensionFilter("All Files", "*.*"),
                            new FileChooser.ExtensionFilter("XML", "*.xml"));
                        File file = launcher.showFileChooserSave(fileChooser);

                        exportDBCommand.execute("xml", file.getAbsolutePath());

                    } catch (CommandInputException e) {
                        launcher.hidePopup();
                        launcher.showTextPopup(e.getMessage());
                    }
                    launcher.hidePopup();
                }
            });
        popupBox.getChildren().add(xmlOption);

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
    
                @Override
                public void handle(ActionEvent event) {
                    launcher.hidePopup();
                }
            });
        popupBox.getChildren().add(cancelButton);

        popupBox.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        popupBox.setAlignment(Pos.CENTER);
        popupBox.setPadding(new Insets(10));
        
        launcher.showPopup(new Scene(popupBox, 200, 200));
    }



}
