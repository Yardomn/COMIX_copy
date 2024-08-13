package core.comix.view.pages.gui;

import java.io.IOException;

import core.comix.GUILauncher;
import core.comix.view.GUI;
import core.comix.view.pages.Page;
import javafx.scene.Scene;

public abstract class GUIPage extends Page {

    protected Scene myScene;
    protected GUILauncher launcher;
    protected GUI gui;
    
    public GUIPage(GUILauncher launcher, GUI gui) throws IOException {
        this.launcher = launcher;
        this.gui = gui;
    }

    protected abstract void setupScene() throws IOException ;

    public void displayPage() throws IOException {
        launcher.switchScene(myScene);
        System.out.println(myScene);
    }
}
