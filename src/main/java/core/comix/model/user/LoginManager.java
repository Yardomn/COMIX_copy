package core.comix.model.user;

import java.util.HashMap;
import java.util.Random;

import core.comix.controller.CommandInputException;
import core.comix.model.collections.database.FullCollection;
import core.comix.model.collections.personal.PersonalCollection;
import core.comix.persistence.db.CustomCSVEditor;
import core.comix.persistence.pc.CSVCollectionManager;
import core.comix.persistence.pc.ICollectionStorageManager;

public class LoginManager {
    private HashMap<String, ActiveUser> storedUsers;
    private ICollectionStorageManager storageManager;
    private UserJSONEditor userStorage;

    public LoginManager(FullCollection database, ICollectionStorageManager storageManager) {
        CurrentUser.setStaticDatabase(database);
        CurrentUser.setStorageManager(storageManager);
        this.storageManager = storageManager;
        this.storageManager = storageManager;

        userStorage = new UserJSONEditor();
        storedUsers = new HashMap<String, ActiveUser>();
        userStorage.readAllUsers(this);
    }

    public void addStoredUser(String username, String password, String pcFileHash) {
        ActiveUser loadedUser = new ActiveUser(CurrentUser.getDatabase(), new PersonalCollection(storageManager), username, password, pcFileHash);
        storageManager = new CSVCollectionManager("src/main/resources/userCollections/", loadedUser);
        try {
            storageManager.loadCollectionFromStorage(loadedUser.getPersonalCollection());
        } catch (CommandInputException e) {
            System.out.println("Error when loading the user "+loadedUser.getUsername()+"'s collection.");
        }
        storedUsers.put(username, loadedUser);
    }

    public boolean createActiveUser(String username, String password) {
        if (storedUsers.containsKey(username)) {
            return false;
        }
        String pcFileHash = generateFileHash();
        while (fileHashExists(pcFileHash)) {
            pcFileHash = generateFileHash();
        }
        ActiveUser newUser = new ActiveUser(CurrentUser.getDatabase(), new PersonalCollection(storageManager), username, password, pcFileHash);
        storedUsers.put(username, newUser);
        userStorage.saveSingleUser(username, password, pcFileHash);
        storageManager = new CSVCollectionManager("src/main/resources/userCollections/", newUser);
        try {
            storageManager.saveCollectionToStorage(newUser.getPersonalCollection());
        } catch (CommandInputException e) {
            System.out.println("Error when saving the user "+newUser.getUsername()+"'s collection.");
        }
        CurrentUser.login(newUser);
        return true;
    }

    private String generateFileHash() {
        int rand;
        String hash = "";
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            rand = random.nextInt(26)+97;
            hash += (char)rand;
        }
        return hash;
    }

    private boolean fileHashExists(String hash) {
        for (ActiveUser u : storedUsers.values()) {
            if (u.getFileHash().equals(hash)) {
                return true;
            }
        }
        return false;
    }

    public ActiveUser tryLogin(String username, String password) {
        if (!storedUsers.containsKey(username)) {
            // No username found
            return null;
        }
        if (storedUsers.get(username).checkPassword(password)) {
            // Both credentials found
            ActiveUser user = storedUsers.get(username);
            CurrentUser.login(user);
            FullCollection fc = new FullCollection();
            CustomCSVEditor dbreader = new CustomCSVEditor("src/main/resources/comics.csv");
            try {
                dbreader.readComics(fc);
            } catch (CommandInputException e) {
                System.out.println("failed");
                fc = CurrentUser.getDatabase();
            }
            CurrentUser.setStaticDatabase(fc);
            return storedUsers.get(username);
        }
        // No password found
        return null;
    }
}
