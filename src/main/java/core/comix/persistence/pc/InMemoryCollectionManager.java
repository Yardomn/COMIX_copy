package core.comix.persistence.pc;

import java.util.ArrayList;

import core.comix.model.collections.personal.IPopulableCollection;
import core.comix.model.comic.Comic;
import core.comix.model.comic.UserComic;

public class InMemoryCollectionManager implements ICollectionStorageManager {
    
    private ArrayList<Comic> storage;

    public InMemoryCollectionManager() {
        storage = new ArrayList<Comic>();
    }
    public void loadCollectionFromStorage(IPopulableCollection collection) {
        for (Comic uc : storage) {
            collection.addComic((UserComic)uc);
        }
    }
    public void saveCollectionToStorage(IPopulableCollection collection) {
        storage = collection.getAllComics();
    }
}
