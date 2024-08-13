package core.comix.model.user;

import core.comix.model.collections.database.FullCollection;
import core.comix.model.collections.personal.PersonalCollection;

public class GuestUser extends User {

    public GuestUser(FullCollection database, PersonalCollection personalCollection) {
        super(database, personalCollection);
    }
    
    public boolean hasReadPermissions() {
        return true;
    }

    public String getUsername() {
        return "GUEST USER";
    }

    public String getFileHash() {
        return "emptyPC";
    }
}
