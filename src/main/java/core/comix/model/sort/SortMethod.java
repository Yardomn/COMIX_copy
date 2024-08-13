package core.comix.model.sort;

import core.comix.model.comic.Comic;

import java.util.Comparator;

public interface SortMethod extends Comparator<Comic> {
    int compare(Comic comic1, Comic comic2);
}
