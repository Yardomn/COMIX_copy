package core.comix.view.pages.gui;

import java.io.IOException;

import core.comix.GUILauncher;
import core.comix.controller.commands.BackCommand;
import core.comix.controller.commands.BrowseCollectionCommand;
import core.comix.controller.commands.AddCustomCommand;
import core.comix.controller.commands.BrowseIntoCommand;
import core.comix.controller.commands.ExportDBCommand;
import core.comix.controller.commands.ExportPCCommand;
import core.comix.controller.commands.ImportDBCommand;
import core.comix.controller.commands.ImportPCCommand;
import core.comix.controller.commands.RedoCommand;
import core.comix.controller.commands.ScrollCommand;
import core.comix.controller.commands.SearchCommand;
import core.comix.controller.commands.SearchTypeCommand;
import core.comix.controller.commands.SortTypeCommand;
import core.comix.controller.commands.UndoCommand;
import core.comix.model.collections.database.FullCollection;
import core.comix.model.collections.personal.PersonalCollection;
import core.comix.model.sort.DefaultSort;
import core.comix.view.GUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class GUICollectionPage extends GUIPage {

    private ScrollCommand scrollCommand;
    private SearchCommand searchCommand;
    private SearchTypeCommand searchTypeCommand;
    private SortTypeCommand sortCommand;
    private BackCommand backCommand;
    private BrowseCollectionCommand browsePCCommand;
    private BrowseIntoCommand browseIntoCommand;
    private AddCustomCommand addCustomCommand;
    private ImportDBCommand importDBCommand;
    private ImportPCCommand importPCCommand;
    private ExportDBCommand exportDBCommand;
    private ExportPCCommand exportPCCommand;
    private CollectionPageController controller;
    private UndoCommand undoCommand;
    private RedoCommand redoCommand;

    public GUICollectionPage(GUILauncher launcher, GUI gui, UndoCommand undoCommand, RedoCommand redoCommand) throws IOException {
        super(launcher, gui);
        this.scrollCommand = new ScrollCommand(gui);
        this.searchCommand = new SearchCommand(gui);
        this.searchTypeCommand = new SearchTypeCommand(gui);
        this.sortCommand = new SortTypeCommand(gui);
        this.backCommand = new BackCommand(gui);
        this.browsePCCommand = new BrowseCollectionCommand(gui);
        this.browseIntoCommand = new BrowseIntoCommand(gui);
        this.addCustomCommand = new AddCustomCommand(gui);
        this.importDBCommand = new ImportDBCommand(gui);
        this.importPCCommand = new ImportPCCommand(gui);
        this.exportDBCommand = new ExportDBCommand(gui);
        this.exportPCCommand = new ExportPCCommand(gui);
        this.undoCommand = undoCommand;
        this.redoCommand = redoCommand;
        setupScene();
    }
    
    protected void setupScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("CollectionPage.fxml"));
        Parent root = loader.load();
        controller = loader.<CollectionPageController>getController();
        controller.initparams(gui, launcher, this.scrollCommand, this.searchCommand, this.searchTypeCommand, this.sortCommand, this.backCommand, this.browsePCCommand, this.browseIntoCommand, this.addCustomCommand, this.importDBCommand, this.importPCCommand, this.exportDBCommand, this.exportPCCommand, this.undoCommand, this.redoCommand);
        myScene = new Scene(root, 600, 400);
    }

    public void displayPage() throws IOException {
        controller.setupScene();
        launcher.switchScene(myScene);
    }
}
