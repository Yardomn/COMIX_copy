package core.comix.model.user;

import core.comix.model.collections.database.FullCollection;
import core.comix.model.collections.personal.PersonalCollection;
import core.comix.persistence.pc.ICollectionStorageManager;

public class CurrentUser {
    private static FullCollection DATABASE;
    private static User INSTANCE;
    private static ICollectionStorageManager STORAGE_MANAGER;

    public static User login(User asUser) {
        INSTANCE = asUser;
        return INSTANCE;
    }

    private static User getUser() {
        if (INSTANCE == null) {
            INSTANCE = new GuestUser(DATABASE, new PersonalCollection(null));
            // TODO set storageManager
        }
        return INSTANCE;
    }

    public static void setStaticDatabase(FullCollection database) {
        DATABASE = database;
    }

    public static void setStorageManager(ICollectionStorageManager storageManager) {
        STORAGE_MANAGER = storageManager;
    }

    public static FullCollection getDatabase() {
        return DATABASE;
    }

    public static PersonalCollection getPersonalCollection() {
        return getUser().getPersonalCollection();
    }

    public static boolean hasReadPermissions() {
        return getUser().hasReadPermissions();
    }

    public static boolean hasAddRemovePermissions() {
        return getUser().hasAddRemovePermissions();
    }

    public static boolean hasEditPermissions() {
        return getUser().hasEditPermissions();
    }

    public static boolean hasCreatePermissions() {
        return getUser().hasCreatePermissions();
    }

    public static String getUsername() {
        return getUser().getUsername();
    }

    public static User getInstance() {
        return INSTANCE;
    }
}
