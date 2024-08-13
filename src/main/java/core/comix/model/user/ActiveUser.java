package core.comix.model.user;

import core.comix.model.collections.database.FullCollection;
import core.comix.model.collections.personal.PersonalCollection;

public class ActiveUser extends User {

    private String username;
    private String password;
    private String pcFileHash;

    public ActiveUser(FullCollection database, PersonalCollection personalCollection, String username, String password, String pcFileHash) {
        super(database, personalCollection);
        this.username = username;
        this.password = password;
        this.pcFileHash = pcFileHash;
    }
    
    public boolean hasReadPermissions() {
        return true;
    }

    public boolean hasAddRemovePermissions() {
        return true;
    }

    public boolean hasEditPermissions() {
        return true;
    }

    public boolean hasCreatePermissions() {
        return true;
    }

    public String getUsername() {
        return username;
    }

    public boolean checkPassword(String checkAgainst) {
        return password.equals(checkAgainst);
    }

    public String getFileHash() {
        return pcFileHash;
    }
}
