package core.comix.persistence.pc;
import core.comix.controller.CommandInputException;
import core.comix.model.collections.personal.IPopulableCollection;

public interface ICollectionStorageManager {
    public void loadCollectionFromStorage(IPopulableCollection collection) throws CommandInputException;
    public void saveCollectionToStorage(IPopulableCollection collection) throws CommandInputException;
}
