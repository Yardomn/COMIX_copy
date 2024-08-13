package core.comix.view.pages.gui;

import javafx.event.EventHandler;
import java.io.IOException;
import java.util.ArrayList;
import javafx.geometry.Insets;

import core.comix.GUILauncher;
import core.comix.controller.CommandInputException;
import core.comix.controller.commands.AddCommand;
import core.comix.controller.commands.AuthenticateCommand;
import core.comix.controller.commands.BackCommand;
import core.comix.controller.commands.BrowseCollectionCommand;
import core.comix.controller.commands.EditCommand;
import core.comix.controller.commands.ExportDBCommand;
import core.comix.controller.commands.ExportPCCommand;
import core.comix.controller.commands.GradeCommand;
import core.comix.controller.commands.ImportDBCommand;
import core.comix.controller.commands.ImportPCCommand;
import core.comix.controller.commands.LoginCommand;
import core.comix.controller.commands.RedoCommand;
import core.comix.controller.commands.RemoveCommand;
import core.comix.controller.commands.SignCommand;
import core.comix.controller.commands.SignupCommand;
import core.comix.controller.commands.SlabCommand;
import core.comix.controller.commands.UndoCommand;
import core.comix.model.Undo.RevisionHistory;
import core.comix.model.collections.database.FullCollection;
import core.comix.model.collections.personal.PersonalCollection;
import core.comix.model.comic.Authenticated;
import core.comix.model.comic.Comic;
import core.comix.model.comic.MarkupOption;
import core.comix.model.comic.Signed;
import core.comix.model.comic.UserComic;
import core.comix.model.user.CurrentUser;
import core.comix.model.user.GuestUser;
import core.comix.view.GUI;
import core.comix.view.pages.gui.auxiliary.ImportExportPopup;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class ComicPageController {
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
    private Button editIssueButton;
    @FXML
    private Button editCharactersButton;
    @FXML
    private Button editCreatorButton;
    @FXML
    private Button editDescriptionButton;
    @FXML
    private Button gradeButton;
    @FXML
    private Button slabButton;
    @FXML
    private Button signButton;
    @FXML
    private Button authenticateButton;
    @FXML
    private Button backButton;
    @FXML
    private Button undoButton;
    @FXML
    private Button redoButton;
    @FXML
    private Text publisherName;
    @FXML
    private Text seriesName;
    @FXML
    private Text volumeNum;
    @FXML
    private TextField issueNum;
    @FXML
    private TextField principleCharacters;
    @FXML
    private TextField creators;
    @FXML
    private TextField description;
    @FXML
    private TextField grade;
    @FXML
    private Text slabbed;
    @FXML
    private Text calculatedValue;
    @FXML
    private Button addButton;
    @FXML
    private Button removeButton;
    @FXML
    private Text userText;
    @FXML
    private Label storyTitle;
    @FXML
    private Text authenticateSignatures;
    @FXML
    private Text unauthenticatedSignatures;
    @FXML
    private TextField signatureNamer;
    @FXML
    private Button confirmSignature;
    @FXML
    private Button valueButton;
    @FXML
    private TextField originalValue;

    private static GUI gui;
    private static GUILauncher launcher;
    private EditCommand editCommand;
    private AddCommand addCommand;
    private RemoveCommand removeCommand;
    private GradeCommand gradeCommand;
    private SlabCommand slabCommand;
    private static SignCommand signCommand;
    private static AuthenticateCommand authenticateCommand;
    private UndoCommand undoCommand;
    private RedoCommand redoCommand;
    private BrowseCollectionCommand browsePCCommand;
    private BackCommand backCommand;
    private ImportExportPopup importExportPopup;
    private boolean isUserComic;

    public ComicPageController(){}

    public void initparams(GUI gui, GUILauncher launcher, EditCommand editCommand, AddCommand addCommand, RemoveCommand removeCommand, 
                            GradeCommand gradeCommand, SlabCommand slabCommand, SignCommand signCommand, AuthenticateCommand authenticateCommand,
                            UndoCommand undoCommand, RedoCommand redoCommand, BrowseCollectionCommand browsePCCommand, ImportDBCommand importDBCommand, 
                            ImportPCCommand importPCCommand, ExportDBCommand exportDBCommand, ExportPCCommand exportPCCommand, BackCommand backCommand){
        this.gui = gui;
        this.launcher = launcher;
        this.editCommand = editCommand;
        this.addCommand = addCommand;
        this.removeCommand = removeCommand;
        this.gradeCommand = gradeCommand;
        this.slabCommand = slabCommand;
        this.signCommand = signCommand;
        this.authenticateCommand = authenticateCommand;
        this.undoCommand = undoCommand;
        this.redoCommand = redoCommand;
        this.browsePCCommand = browsePCCommand;
        this.backCommand = backCommand;
        this.importExportPopup = new ImportExportPopup(launcher, gui, importPCCommand, importDBCommand, exportPCCommand, exportDBCommand);
    }
    public void fullCollectionHandle(){
        try {
            browsePCCommand.execute("db");
        } catch (CommandInputException e) {
            launcher.showTextPopup(e.getMessage());
        }
    }

    public void personalCollectionHandle() throws IOException{
        try {
            browsePCCommand.execute("pc");
        } catch (CommandInputException e) {
            launcher.showTextPopup(e.getMessage());
        }
    }

    public void importHandle() {
        // Shouldn't happen
    }

    public void exportHandle() {
        // Shouldn't happen
    }

    public void logoutHandle() {
        gui.updateCurrentItem(null);
    }
    public void editIssueHandle() throws CommandInputException, IOException{
        System.out.println(isUserComic);
        if(isUserComic){
            editCommand.execute("issueNum", issueNum.getText());
        }
    }
    public void editCharactersHandle() throws CommandInputException, IOException{
        if(isUserComic){
            editCommand.execute("principleCharacters", principleCharacters.getText());
        }
    }
    public void editCreatorHandle() throws CommandInputException, IOException{
        if(isUserComic){
            editCommand.execute("creators", creators.getText());
        }
    }
    public void editDescriptionHandle() throws CommandInputException, IOException{
        try {
            editCommand.execute("description", description.getText());
        } catch (CommandInputException e) {
            launcher.showTextPopup(e.getMessage());
        }
    }
    public void gradeHandle() throws NumberFormatException{
        try {
                if (grade.getText().matches("\\d+")) {
                    int gradeNum = Integer.parseInt(grade.getText());
                    if(gradeNum >= 1 && gradeNum <= 10){
                        gradeCommand.execute(gradeNum);
                    } else {
                        throw new CommandInputException("The grade score should be a number between 1-10");
                    }
                } else {
                    throw new CommandInputException("The grade score should be a number between 1-10");
                }      
        } catch (CommandInputException e) {
            launcher.showTextPopup(e.getMessage());
        }
    }
    public void slabHandle(){
        try {
            slabCommand.execute();
        } catch (CommandInputException e) {
            launcher.showTextPopup(e.getMessage());
        }
    }
    public void signHandle() throws IOException{
        if(isUserComic){
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("SignatureNamer.fxml"));
            Parent root = loader.load();
            launcher.showPopup(new Scene(root, 300, 200));
        }
    }
    public void signPopupHandle() throws CommandInputException, IOException{
        launcher.hidePopup();
        try {
            signCommand.execute(signatureNamer.getText());
        } catch (CommandInputException e) {
            launcher.showTextPopup(e.getMessage());
        }
    }

    public void cancelPopupHandle() throws CommandInputException, IOException{
        launcher.hidePopup();
    }
    public void valueHandle(){
        try {
            editCommand.execute("value", originalValue.getText());
        } catch (CommandInputException e) {
            launcher.showTextPopup(e.getMessage());
        }
    }
    public void authenticateSignatures(){
        VBox popupbox = new VBox();
        HBox labelBox = new HBox();
        labelBox.setAlignment(Pos.CENTER);
        Label popupTitle = new Label("Authenticate:");
        //allign label center
        popupTitle.setFont(new Font(20));
        popupTitle.setMaxWidth(200);
        popupTitle.setWrapText(true);
        popupTitle.setPadding(new Insets(0,0,10,0));
        labelBox.getChildren().add(popupTitle);
        popupbox.getChildren().add(labelBox);
        ArrayList<String> signatures = new ArrayList<String>();
        if(gui.getCurrentItem() instanceof Signed){
            Signed currentItem = (Signed) gui.getCurrentItem();
            currentItem.getSignatures(signatures);
            for(String signature: signatures){
                VBox signatureBox = new VBox();
                Button popupButton = new Button(signature);
                signatureBox.getChildren().add(popupButton);
                signatureBox.setMargin(popupButton, new Insets(0,0,5,0));
                popupButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        try {
                            authenticateCommand.execute(popupButton.getText());
                            launcher.hidePopup();
                        } catch (CommandInputException e) {
                            launcher.showTextPopup(e.getMessage());
                        }
                    }
                });
                popupButton.setMaxWidth(150);
                popupButton.setWrapText(true);
                popupButton.setPadding(new Insets(5,5,10,5));
                popupButton.setStyle("-fx-background-color: #faff70; -fx-border-color: black; -fx-border-radius: 2px;");
                popupbox.getChildren().add(popupButton);
                popupButton.getText();
            }
        }
        Button cancel = new Button("Cancel");
        cancel.setStyle("-fx-background-color: #ff7070; -fx-border-color: black; -fx-border-radius: 2px;");
        cancel.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    launcher.hidePopup();
                }
        });
        popupbox.getChildren().add(cancel);
        popupbox.setAlignment(Pos.CENTER);
        popupbox.setStyle("-fx-border-color: black; -fx-border-radius: 2px;");
        launcher.showPopup(new Scene(popupbox, 200, 300));
    }
    public void authenticateSignatureHandle(){
        authenticateSignatures();
    }
    public void viewAuthenticatedSignatures(){
        VBox popupbox = new VBox();
        HBox labelBox = new HBox();
        labelBox.setAlignment(Pos.CENTER);
        Label popupTitle = new Label("Authenticated Signatures:");
        popupTitle.setFont(new Font(20));
        popupTitle.setMaxWidth(200);
        popupTitle.setWrapText(true);
        popupTitle.setPadding(new Insets(0,0,10,0));
        popupTitle.setTextAlignment(TextAlignment.CENTER);
        labelBox.getChildren().add(popupTitle);
        popupbox.getChildren().add(labelBox);
        ArrayList<String> authenticated = new ArrayList<String>();
        if(gui.getCurrentItem() instanceof Authenticated){
            Authenticated currentItem = (Authenticated) gui.getCurrentItem();
            currentItem.getAuthenticates(authenticated);
            for(String authenticate: authenticated){
                HBox textBox = new HBox();
                textBox.setAlignment(Pos.CENTER);
                Label popupText = new Label(authenticate);
                popupText.setMaxWidth(200);
                popupText.setWrapText(true);
                popupText.setPadding(new Insets(0,0,10,0));
                textBox.getChildren().add(popupText);
                popupbox.getChildren().add(textBox);
            }
        }
        Button cancel = new Button("Cancel");
        cancel.setStyle("-fx-background-color: #ff7070; -fx-border-color: black; -fx-border-radius: 2px;");
        cancel.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    launcher.hidePopup();
                }
        });
        popupbox.getChildren().add(cancel);
        popupbox.setAlignment(Pos.CENTER);
        launcher.showPopup(new Scene(popupbox, 200, 300));
    }
    public void viewAuthenticatedSignaturesHandle(){
        viewAuthenticatedSignatures();
    }
    public void viewUnAuthenticatedSignatures(){
        VBox popupbox = new VBox();
        HBox labelBox = new HBox();
        labelBox.setAlignment(Pos.CENTER);
        Label popupTitle = new Label("Signatures:");
        popupTitle.setFont(new Font(20));
        popupTitle.setMaxWidth(200);
        popupTitle.setWrapText(true);
        popupTitle.setPadding(new Insets(0,0,10,0));
        labelBox.getChildren().add(popupTitle);
        popupbox.getChildren().add(labelBox);
        ArrayList<String> signatures = new ArrayList<String>();
        if(gui.getCurrentItem() instanceof Signed){
            Signed currentItem = (Signed) gui.getCurrentItem();
            currentItem.getSignatures(signatures);
            for(String signature: signatures){
                HBox textBox = new HBox();
                textBox.setAlignment(Pos.CENTER);
                Label popupText = new Label(signature);
                popupText.setMaxWidth(200);
                popupText.setWrapText(true);
                popupText.setPadding(new Insets(0,0,10,0));
                textBox.getChildren().add(popupText);
                popupbox.getChildren().add(textBox);
            }
        }
        Button cancel = new Button("Cancel");
        cancel.setStyle("-fx-background-color: #ff7070; -fx-border-color: black; -fx-border-radius: 2px;");
        cancel.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    launcher.hidePopup();
                }
        });
        popupbox.getChildren().add(cancel);
        popupbox.setAlignment(Pos.CENTER);
        popupbox.setStyle("-fx-border-color: black; -fx-border-radius: 2px;");
        launcher.showPopup(new Scene(popupbox, 200, 300));
    }
    public void viewUnAuthenticatedSignaturesHandle(){
        viewUnAuthenticatedSignatures();
    }
    public void backHandle(){
        try {
            backCommand.execute();
        } catch (CommandInputException e) {}
    }
    public void undoHandle(){
        try {
            undoCommand.execute();
            System.out.println("command run");
        } catch (CommandInputException e) {
            launcher.showTextPopup(e.getMessage());
        }
    }
    public void redoHandle(){
        try {
            redoCommand.execute();
        } catch (CommandInputException e) {
            launcher.showTextPopup(e.getMessage());
        }
    }
    public void addHandle(){
        try {
            if(!isUserComic){
                addCommand.execute();
            }
        } catch (CommandInputException e) {}
    }
    public void removeHandle(){
        try {
            if(isUserComic){
                removeCommand.execute();
            }
        } catch (CommandInputException e) {}
    }
    public void setupText(Comic comic){
        
        setEnabled(addButton, true);
        setEnabled(removeButton, true);

        setEnabled(importButton, false);
        setEnabled(exportButton, false);

        if (!CurrentUser.hasAddRemovePermissions()) {
            setEnabled(addButton, false);
            setEnabled(removeButton, false);
        }
        if(comic instanceof UserComic || comic instanceof MarkupOption){
            isUserComic = true;
            setEnabled(editCharactersButton, true);
            setEnabled(editCreatorButton, true);
            setEnabled(editDescriptionButton, true);
            setEnabled(editIssueButton, true);
            setEnabled(gradeButton, true);
            setEnabled(slabButton, true);
            setEnabled(signButton, true);
            setEnabled(authenticateButton, true);
            setEnabled(addButton, false);
            setEnabled(valueButton, true);
            authenticateSignatures.setText("View Authenticated Signatures");
            unauthenticatedSignatures.setText("View Signatures");
        }else{
            isUserComic = false;
            setEnabled(editCharactersButton, false);
            setEnabled(editCreatorButton, false);
            setEnabled(editDescriptionButton, false);
            setEnabled(editIssueButton, false);
            setEnabled(gradeButton, false);
            setEnabled(slabButton, false);
            setEnabled(signButton, false);
            setEnabled(authenticateButton, false);
            setEnabled(removeButton, false);
            setEnabled(valueButton, false);
            authenticateSignatures.setText("No Signatures");
            unauthenticatedSignatures.setText("No Signatures");
            // DO THIS PLEASE FIX TEXT OF BROWSING PERSONAL COLLECTION TO USE TEXTFIELD AND LEFT ELLIPSIS
        }
        publisherName.setText(comic.getPublisher());
        seriesName.setText(comic.getSeries());
        volumeNum.setText(Integer.toString(comic.getVolumeNum()));
        issueNum.setText(comic.getIssueNum());
        principleCharacters.setText(String.join(" | ", comic.getPrincipleCharacters()));
        creators.setText(String.join(" | ", comic.getCreators()));
        description.setText(comic.getDescription());
        if(comic.getGrade()>0){
            grade.setText(Integer.toString(comic.getGrade()));
        }else{
            grade.setText("Ungraded");
        }
        slabbed.setText("No");
        if(comic.isMarkup()){
            MarkupOption markup = comic.getMarkup();
            boolean isGrade = markup.isGraded();
            boolean isSlab = markup.isSlabbed();
            if(isGrade){
                grade.setText(Integer.toString(markup.getGrade()));
                if(isSlab) {
                    slabbed.setText("Yes");
                } else {
                    slabbed.setText("No");
                }
            } else {
                grade.setText("Ungraded");
            }
        } else{
            slabbed.setText("No");
            grade.setText("Ungraded");
        }
        originalValue.setText(Float.toString(comic.getOriginalValue()));
        calculatedValue.setText(Float.toString(comic.getTotalValue()));
        userText.setText("signed in as " + CurrentUser.getUsername());
        storyTitle.setText(comic.getStoryTitle());

        if (CurrentUser.getInstance() instanceof GuestUser) {
            logoutButton.setText("Log In");
        }
        else {
            logoutButton.setText("Log Out");
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

        setEnabled(slabButton, true);
        if (! comic.isMarkup() || (comic.isMarkup() && comic.getMarkup().isSlabbed()) || (comic.isMarkup() && !comic.getMarkup().isGraded())) {
            setEnabled(slabButton, false);
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
