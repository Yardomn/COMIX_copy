package core.comix.view.pages.gui;

import java.io.IOException;

import core.comix.controller.commands.AddCommand;
import core.comix.controller.commands.RemoveCommand;
import core.comix.controller.commands.EditCommand;
import core.comix.controller.commands.ExportDBCommand;
import core.comix.controller.commands.ExportPCCommand;
import core.comix.controller.commands.GradeCommand;
import core.comix.controller.commands.ImportDBCommand;
import core.comix.controller.commands.ImportPCCommand;
import core.comix.controller.commands.RedoCommand;
import core.comix.controller.commands.SlabCommand;
import core.comix.controller.commands.UndoCommand;
import core.comix.model.comic.Comic;
import core.comix.controller.commands.SignCommand;
import core.comix.controller.commands.AuthenticateCommand;
import core.comix.controller.commands.BackCommand;
import core.comix.controller.commands.BrowseCollectionCommand;
import core.comix.controller.commands.BrowseIntoCommand;
import core.comix.GUILauncher;
import core.comix.view.GUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class GUIComicPage extends GUIPage {
    private AddCommand addCommand;
    private RemoveCommand removeCommand;
    private EditCommand editCommand;
    private GradeCommand gradeCommand;
    private SlabCommand slabCommand;
    private SignCommand signCommand;
    private AuthenticateCommand authenticateCommand;
    private BackCommand backCommand;
    private BrowseCollectionCommand browsePCCommand;
    private ImportDBCommand importDBCommand;
    private ImportPCCommand importPCCommand;
    private ExportDBCommand exportDBCommand;
    private ExportPCCommand exportPCCommand;
    private UndoCommand undoCommand;
    private RedoCommand redoCommand;
    private ComicPageController controller;

    public GUIComicPage(GUILauncher launcher, GUI gui, UndoCommand undoCommand, RedoCommand redoCommand) throws IOException {
        super(launcher, gui);
        this.addCommand = new AddCommand(gui);
        this.removeCommand = new RemoveCommand(gui);
        this.editCommand = new EditCommand(gui);
        this.gradeCommand = new GradeCommand(gui);
        this.slabCommand = new SlabCommand(gui);
        this.signCommand = new SignCommand(gui);
        this.authenticateCommand = new AuthenticateCommand(gui);
        this.backCommand = new BackCommand(gui);
        this.browsePCCommand = new BrowseCollectionCommand(gui);
        this.backCommand = new BackCommand(gui);
        this.importDBCommand = new ImportDBCommand(gui);
        this.importPCCommand = new ImportPCCommand(gui);
        this.exportDBCommand = new ExportDBCommand(gui);
        this.exportPCCommand = new ExportPCCommand(gui);
        this.backCommand = new BackCommand(gui);
        this.undoCommand = undoCommand;
        this.redoCommand = redoCommand;
        setupScene();
    }

    protected void setupScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ComicPage.fxml"));
        Parent root = loader.load();
        controller = loader.<ComicPageController>getController();
        controller.initparams(gui, launcher, editCommand, addCommand, removeCommand, gradeCommand, slabCommand, signCommand, authenticateCommand, undoCommand, redoCommand, browsePCCommand, importDBCommand, importPCCommand, exportDBCommand, exportPCCommand, backCommand);
        myScene = new Scene(root, 600, 400);
    }
    
    public void displayPage(){
        controller.setupText((Comic)gui.getCurrentItem());
        launcher.switchScene(myScene);
    }
}
