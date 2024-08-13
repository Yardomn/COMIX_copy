package core.comix.model.collections.personal;

import java.util.*;

import core.comix.model.comic.Comic;

public interface IPopulableCollection {
    public Comic addComic(Comic comic);
    public Comic removeComic(Comic comic);
    public ArrayList<Comic> getAllComics();
    public void clear();
    public void clearNoSave();
}
