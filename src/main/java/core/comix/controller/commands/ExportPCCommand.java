package core.comix.controller.commands;

import core.comix.controller.Command;
import core.comix.controller.CommandInputException;
import core.comix.model.user.CurrentUser;
import core.comix.persistence.db.CustomCSVEditor;
import core.comix.persistence.db.CustomJSONEditor;
import core.comix.persistence.db.CustomXMLEditor;
import core.comix.persistence.pc.CSVCollectionManager;
import core.comix.persistence.pc.JSONCollectionManager;
import core.comix.persistence.pc.XMLCollectionManager;
import core.comix.view.UI;

public class ExportPCCommand extends Command {

    public ExportPCCommand (UI ui){
        super(ui);
    }

    public void execute(String filetype, String filename) throws CommandInputException {
        if (filetype.equals("csv")){
            CSVCollectionManager csvEditor = new CSVCollectionManager(filename);
            csvEditor.saveCollectionToStorage(CurrentUser.getPersonalCollection());
        } else if (filetype.equals("json")){
            JSONCollectionManager jsonEditor = new JSONCollectionManager(filename);
            jsonEditor.saveCollectionToStorage(CurrentUser.getPersonalCollection());
        } else {
            XMLCollectionManager xmlEditor = new XMLCollectionManager(filename);
            xmlEditor.saveCollectionToStorage(CurrentUser.getPersonalCollection());
        }
    }
}
