package core.comix.view.pages.gui;

import java.io.File;
import java.io.IOException;

import core.comix.GUILauncher;
import core.comix.controller.CommandInputException;
import core.comix.controller.commands.AddCustomCommand;
import core.comix.controller.commands.BackCommand;
import core.comix.controller.commands.BrowseCollectionCommand;
import core.comix.controller.commands.BrowseIntoCommand;
import core.comix.controller.commands.ExportDBCommand;
import core.comix.controller.commands.ExportPCCommand;
import core.comix.controller.commands.ImportDBCommand;
import core.comix.controller.commands.ImportPCCommand;
import core.comix.controller.commands.LoginCommand;
import core.comix.controller.commands.RedoCommand;
import core.comix.controller.commands.ScrollCommand;
import core.comix.controller.commands.SearchCommand;
import core.comix.controller.commands.SearchTypeCommand;
import core.comix.controller.commands.SignupCommand;
import core.comix.controller.commands.SortTypeCommand;
import core.comix.controller.commands.UndoCommand;
import core.comix.model.Undo.RevisionHistory;
import core.comix.model.collections.CollectionCategory;
import core.comix.model.collections.CollectionComponent;
import core.comix.model.collections.database.FullCollection;
import core.comix.model.collections.personal.PersonalCollection;
import core.comix.model.collections.personal.PublisherCollection;
import core.comix.model.collections.personal.SeriesCollection;
import core.comix.model.collections.personal.VolumeCollection;
import core.comix.model.search.ExactSearch;
import core.comix.model.search.GapsSearch;
import core.comix.model.search.IssueSearch;
import core.comix.model.search.MarkedUpSearch;
import core.comix.model.search.PartialSearch;
import core.comix.model.search.RunSearch;
import core.comix.model.search.SearchResults;
import core.comix.model.sort.DefaultSort;
import core.comix.model.sort.PublicationSort;
import core.comix.model.user.CurrentUser;
import core.comix.model.user.GuestUser;
import core.comix.view.GUI;
import core.comix.view.pages.gui.auxiliary.AddCustomPopup;
import core.comix.view.pages.gui.auxiliary.ImportExportPopup;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

public class CollectionPageController {
    @FXML
    private Button fullCollectionButton;
    @FXML
    private Button personalCollectionButton;
    @FXML
    private Button importButton;
    @FXML
    private Button exportButton;
    @FXML
    private Button logoutButton;
    @FXML
    private Button filterButton;
    @FXML
    private Button settingsButton;
    @FXML
    private Button searchButton;
    @FXML
    private Button backButton;
    @FXML
    private Button upButton;
    @FXML
    private Button downButton;
    @FXML
    private Button addButton;
    @FXML
    private Button undoButton;
    @FXML
    private Button redoButton;

    @FXML
    private TextField searchText;
    @FXML
    private Label browsingText;
    @FXML
    private Text guestText;
    @FXML
    private Text itemNumber;

    @FXML
    private GridPane currentLevelPane;

    private GUI gui;
    private GUILauncher launcher;
    private ScrollCommand scrollCommand;
    private SearchCommand searchCommand;
    private SearchTypeCommand searchTypeCommand;
    private SortTypeCommand sortCommand;
    private BackCommand backCommand;
    private BrowseCollectionCommand browsePCCommand;
    private BrowseIntoCommand browseIntoCommand;
    private UndoCommand undoCommand;
    private RedoCommand redoCommand;

    private ImportExportPopup importExportPopup;
    private AddCustomPopup addCustomPopup;

    public void initparams(GUI gui, GUILauncher launcher, ScrollCommand scrollCommand,
            SearchCommand searchCommand, SearchTypeCommand searchTypeCommand,
            SortTypeCommand sortCommand, BackCommand backCommand,
            BrowseCollectionCommand browsePCCommand, BrowseIntoCommand browseIntoCommand,
            AddCustomCommand addCustomCommand, ImportDBCommand importDBCommand,
            ImportPCCommand importPCCommand, ExportDBCommand exportDBCommand,
            ExportPCCommand exportPCCommand, UndoCommand undoCommand, RedoCommand redoCommand) {
        this.gui = gui;
        this.launcher = launcher;
        this.scrollCommand = scrollCommand;
        this.searchCommand = searchCommand;
        this.searchTypeCommand = searchTypeCommand;
        this.sortCommand = sortCommand;
        this.backCommand = backCommand;
        this.browsePCCommand = browsePCCommand;
        this.browseIntoCommand = browseIntoCommand;
        this.undoCommand = undoCommand;
        this.redoCommand = redoCommand;
        this.importExportPopup = new ImportExportPopup(launcher, gui, importPCCommand,
                importDBCommand, exportPCCommand, exportDBCommand);
        this.addCustomPopup = new AddCustomPopup(launcher, gui, addCustomCommand);
        // comment
        for (Node n : currentLevelPane.getChildren()) {
            if (GridPane.getColumnIndex(n) != null && GridPane.getColumnIndex(n) == 1) {
                int row;
                if (GridPane.getRowIndex(n) == null) {
                    row = 0;
                } else {
                    row = GridPane.getRowIndex(n);
                }
                n.setOnMouseClicked(new EventHandler<MouseEvent>() {

                    @Override
                    public void handle(MouseEvent event) {
                        try {

                            browseIntoCommand.execute(row + gui.getScrollAmount() + 1);

                        } catch (CommandInputException e) {
                            launcher.showTextPopup(e.getMessage());
                        }
                    }
                });
                ((Label) n).setTextOverrun(OverrunStyle.ELLIPSIS);
            }
        }
    }

    public void fullCollectionHandle() {
        try {
            browsePCCommand.execute("db");
        } catch (CommandInputException e) {
            launcher.showTextPopup(e.getMessage());
        }
    }

    public void personalCollectionHandle() throws IOException {
        try {
            browsePCCommand.execute("pc");
        } catch (CommandInputException e) {
            launcher.showTextPopup(e.getMessage());
        }
    }

    public void importHandle() {
        if (gui.getCurrentItem() instanceof PersonalCollection) {
            importExportPopup.importPCPopup();
        } else if (gui.getCurrentItem() instanceof FullCollection) {
            importExportPopup.importDBPopup();
        }
    }

    public void exportHandle() {
        if (gui.getCurrentItem() instanceof PersonalCollection) {
            importExportPopup.exportPCPopup();
        } else if (gui.getCurrentItem() instanceof FullCollection) {
            importExportPopup.exportDBPopup();
        }
    }

    public void logoutHandle() {
        gui.updateCurrentItem(null);
    }

    public void backHandle() throws IOException {
        try {
            backCommand.execute();
        } catch (CommandInputException e) {
        }
    }

    public void upHandle() throws IOException {
        try {
            scrollCommand.execute(true);
        } catch (CommandInputException e) {
        }
    }

    public void downHandle() throws IOException {
        try {
            scrollCommand.execute(false);
        } catch (CommandInputException e) {
        }
    }

    public void addHandle() throws IOException {
        addCustomPopup.addCustomPopup();
    }

    public void undoHandle() {
        try {
            undoCommand.execute();
        } catch (CommandInputException e) {
        }
    }

    public void redoHandle() {
        try {
            redoCommand.execute();
        } catch (CommandInputException e) {
        }
    }

    public void filterHandle() {
        VBox popupBox = new VBox();
        HBox labelBox = new HBox();
        labelBox.setAlignment(Pos.CENTER);
        Label popupText = new Label("Choose a filter");
        popupText.setMaxWidth(200);
        popupText.setWrapText(true);
        popupText.setPadding(new Insets(0, 0, 10, 0));
        labelBox.getChildren().add(popupText);
        popupBox.getChildren().add(labelBox);

        Button partialOption = new Button("Partial Search");
        partialOption.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                try {
                    searchTypeCommand.execute(new PartialSearch());
                } catch (CommandInputException e) {
                    launcher.hidePopup();
                    launcher.showTextPopup(e.getMessage());
                }
                launcher.hidePopup();
            }
        });
        popupBox.getChildren().add(partialOption);

        Button exactOption = new Button("Exact Search");
        exactOption.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                try {
                    searchTypeCommand.execute(new ExactSearch());

                } catch (CommandInputException e) {
                    launcher.hidePopup();
                    launcher.showTextPopup(e.getMessage());
                }
                launcher.hidePopup();
            }
        });
        popupBox.getChildren().add(exactOption);

        Button issueOption = new Button("Issue Search");
        issueOption.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                try {
                    searchTypeCommand.execute(new IssueSearch());
                } catch (CommandInputException e) {
                    launcher.hidePopup();
                    launcher.showTextPopup(e.getMessage());
                }
                launcher.hidePopup();
            }
        });
        popupBox.getChildren().add(issueOption);

        Button markedUpOption = new Button("Marked Up Search");
        markedUpOption.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                try {
                    searchTypeCommand.execute(new MarkedUpSearch());

                } catch (CommandInputException e) {
                    launcher.hidePopup();
                    launcher.showTextPopup(e.getMessage());
                }
                launcher.hidePopup();
            }
        });
        popupBox.getChildren().add(markedUpOption);

        Button runsOption = new Button("Runs Search");
        runsOption.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                try {
                    searchTypeCommand.execute(new RunSearch());

                } catch (CommandInputException e) {
                    launcher.hidePopup();
                    launcher.showTextPopup(e.getMessage());
                }
                launcher.hidePopup();
            }
        });
        popupBox.getChildren().add(runsOption);

        Button gapsOption = new Button("Gap Search");
        gapsOption.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                try {
                    searchTypeCommand.execute(new GapsSearch());

                } catch (CommandInputException e) {
                    launcher.hidePopup();
                    launcher.showTextPopup(e.getMessage());
                }
                launcher.hidePopup();
            }
        });
        popupBox.getChildren().add(gapsOption);

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                launcher.hidePopup();
            }
        });
        popupBox.getChildren().add(cancelButton);

        popupBox.setBackground(
                new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        popupBox.setAlignment(Pos.CENTER);
        popupBox.setPadding(new Insets(10));

        launcher.showPopup(new Scene(popupBox, 200, 220));
    }

    public void settingsHandle() {
        VBox popupBox = new VBox();
        Label popupText = new Label("Choose a sort");
        popupText.setMaxWidth(200);
        popupText.setWrapText(true);
        popupText.setPadding(new Insets(0, 0, 10, 0));

        popupBox.getChildren().add(popupText);

        Button defaultOption = new Button("Default Sort");
        defaultOption.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                try {
                    sortCommand.execute(new DefaultSort());
                } catch (CommandInputException e) {
                    launcher.hidePopup();
                    launcher.showTextPopup(e.getMessage());
                }
                launcher.hidePopup();
            }
        });
        popupBox.getChildren().add(defaultOption);

        Button publicationOption = new Button("Publication Sort");
        publicationOption.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                try {
                    sortCommand.execute(new PublicationSort());

                } catch (CommandInputException e) {
                    launcher.hidePopup();
                    launcher.showTextPopup(e.getMessage());
                }
                launcher.hidePopup();
            }
        });
        popupBox.getChildren().add(publicationOption);

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                launcher.hidePopup();
            }
        });
        popupBox.getChildren().add(cancelButton);

        popupBox.setBackground(
                new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        popupBox.setAlignment(Pos.CENTER);
        popupBox.setPadding(new Insets(10));

        launcher.showPopup(new Scene(popupBox, 200, 200));

    }

    public void searchHandle() throws IOException {
        try {
            searchCommand.execute(this.searchText.getText());
        } catch (CommandInputException e) {
        }
    }


    public void setupScene() {
        setEnabled(importButton, true);
        setEnabled(exportButton, true);

        setEnabled(addButton, true);

        setEnabled(settingsButton, true);
        setEnabled(filterButton, true);
        setEnabled(searchButton, true);

        CollectionCategory currentCategory = (CollectionCategory) gui.getCurrentItem();

        for (Node n : currentLevelPane.getChildren()) {
            if (GridPane.getColumnIndex(n) != null && GridPane.getColumnIndex(n) == 1) {
                int row;
                if (GridPane.getRowIndex(n) == null) {
                    row = 0;
                } else {
                    row = GridPane.getRowIndex(n);
                }
                if ((row + gui.getScrollAmount() < currentCategory.getChildren().size())
                        && !(currentCategory instanceof SearchResults)) {
                    ((Label) n).setText(currentCategory.getChild(row + gui.getScrollAmount())
                            .getNamingString());
                } else if (currentCategory instanceof SearchResults) {
                    ((Label) n).setText(currentCategory.getMenuString(row + gui.getScrollAmount()));
                } else {
                    ((Label) n).setText("");
                }
            }
        }

        if (currentCategory.getChildren().size() > 0) {
            itemNumber.setText("Items " + (gui.getScrollAmount() + 1) + "-"
                    + Math.min(gui.getScrollAmount() + 10, currentCategory.getChildren().size())
                    + " of " + currentCategory.getChildren().size());
        } else {
            itemNumber.setText("Items 0-0 of 0");
        }

        // Yellow Buttons
        setEnabled(upButton, true);
        setEnabled(downButton, true);
        if (gui.getScrollAmount() == 0) {
            setEnabled(upButton, false);
        }
        if (gui.getScrollAmount() + 10 >= currentCategory.getChildren().size()) {
            setEnabled(downButton, false);
        }

        setEnabled(backButton, true);
        if (gui.getCurrentItem().getParent() == null) {
            setEnabled(backButton, false);
        }

        setEnabled(undoButton, true);
        setEnabled(redoButton, true);
        if (RevisionHistory.getUndoHistory().size() == 0) {
            setEnabled(undoButton, false);
        }
        if (RevisionHistory.getRedoHistory().size() == 0) {
            setEnabled(redoButton, false);
        }

        if (gui.getCurrentItem() instanceof FullCollection) {
            browsingText.setText("Browsing Full Collection");

            setEnabled(addButton, false);
            setEnabled(settingsButton, false);
            setEnabled(filterButton, true);
            setEnabled(searchButton, true);
        } else if (gui.getCurrentItem() instanceof PersonalCollection
                || gui.getCurrentItem() instanceof PublisherCollection
                || gui.getCurrentItem() instanceof SeriesCollection
                || gui.getCurrentItem() instanceof VolumeCollection) {
            browsingText.setText("Browsing Personal Collection");
            if (gui.getCurrentItem() instanceof PublisherCollection) {
                browsingText.setText("Browsing Personal Collection > "
                        + ((PublisherCollection) gui.getCurrentItem()).getPublisherName());
            } else if (gui.getCurrentItem() instanceof SeriesCollection) {
                browsingText.setText("Browsing Personal Collection > "
                        + ((PublisherCollection) gui.getCurrentItem().getParent())
                                .getPublisherName()
                        + " > " + ((SeriesCollection) gui.getCurrentItem()).getSeriesName());
            } else if (gui.getCurrentItem() instanceof VolumeCollection) {
                browsingText.setText("Browsing Personal Collection > "
                        + ((PublisherCollection) gui.getCurrentItem().getParent().getParent())
                                .getPublisherName()
                        + " > "
                        + ((SeriesCollection) gui.getCurrentItem().getParent()).getSeriesName()
                        + " > Vol " + ((VolumeCollection) gui.getCurrentItem()).getVolumeNumber());
            }
            setEnabled(settingsButton, false);
            setEnabled(filterButton, true);
            setEnabled(searchButton, true);
        } else if (gui.getCurrentItem() instanceof SearchResults) {
            browsingText.setText("Browsing Search Results");
            setEnabled(importButton, false);
            setEnabled(exportButton, false);

            setEnabled(addButton, false);
            setEnabled(settingsButton, true);
            setEnabled(filterButton, false);
            setEnabled(searchButton, false);
        }
        guestText.setText("Signed in as " + CurrentUser.getUsername());

        if (CurrentUser.getInstance() instanceof GuestUser) {
            logoutButton.setText("Log In");
            setEnabled(addButton, false);
        } else {
            logoutButton.setText("Log Out");
        }
    }

    private void setEnabled(Button b, boolean e) {
        if (e) {
            b.setDisable(false);
            b.setOpacity(1);
        }
        else {
            b.setDisable(true);
            b.setOpacity(0.5);
        }
    }

}
