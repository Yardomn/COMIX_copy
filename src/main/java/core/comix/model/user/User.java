package core.comix.model.user;

import core.comix.model.collections.database.FullCollection;
import core.comix.model.collections.personal.PersonalCollection;

public abstract class User {
    protected FullCollection database;
    protected PersonalCollection personalCollection;

    public User(FullCollection database, PersonalCollection personalCollection)
    {
        this.database = database;
        this.personalCollection = personalCollection;
    }

    public PersonalCollection getPersonalCollection()
    {
        return personalCollection;
    }

    public boolean hasReadPermissions() {
        return false;
    }

    public boolean hasAddRemovePermissions() {
        return false;
    }

    public boolean hasEditPermissions() {
        return false;
    }

    public boolean hasCreatePermissions() {
        return false;
    }

    public abstract String getUsername();

    public abstract String getFileHash();
}
