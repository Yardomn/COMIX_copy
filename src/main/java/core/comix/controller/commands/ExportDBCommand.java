package core.comix.controller.commands;

import core.comix.model.user.CurrentUser;
import core.comix.model.user.User;
import core.comix.controller.Command;
import core.comix.controller.CommandInputException;
import core.comix.persistence.db.CustomCSVEditor;
import core.comix.persistence.db.CustomJSONEditor;
import core.comix.persistence.db.CustomXMLEditor;
import core.comix.view.UI;

public class ExportDBCommand extends Command {

    public ExportDBCommand (UI ui){
        super(ui);
    }

    public void execute(String filetype, String filename) throws CommandInputException {
        if (filetype.equals("csv")){
            CustomCSVEditor csvEditor = new CustomCSVEditor(filename);
            csvEditor.saveComics(CurrentUser.getDatabase());
        } else if (filetype.equals("json")){
            CustomJSONEditor jsonEditor = new CustomJSONEditor(filename);
            jsonEditor.saveComics(CurrentUser.getDatabase());
        } else {
            CustomXMLEditor xmlEditor = new CustomXMLEditor(filename);
            xmlEditor.saveComics(CurrentUser.getDatabase());
        }
    }
}
