package core.comix.persistence.db;

import core.comix.controller.CommandInputException;
import core.comix.model.collections.database.FullCollection;

public interface IEditor {
    public void readComics(FullCollection collection) throws CommandInputException;

    public void saveComics(FullCollection collection) throws CommandInputException;
}
