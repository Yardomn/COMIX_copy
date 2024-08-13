package core.comix.model.search;

import java.util.ArrayList;
import core.comix.model.collections.database.FullCollection;
import core.comix.model.collections.personal.PublisherCollection;
import core.comix.model.collections.personal.SeriesCollection;
import core.comix.model.collections.personal.VolumeCollection;
import core.comix.model.comic.Comic;

public class MarkedUpSearch implements SearchCollector{
        @Override
    public void visitFullCollection(FullCollection fullCollection, String searchPhrase) {
        SearchResults.Instance().clearSearchResults();
        ArrayList<Comic> list = fullCollection.getAllComics();
        for (Comic c : list) {
            if (c.isMarkup())             {
                SearchResults.Instance().tryAddComic(c);
            }
        }

    }

    @Override
    public void visitPublisherCollection(PublisherCollection publisherCollection,
            String searchPhrase) {}

    @Override
    public void visitSeriesCollection(SeriesCollection seriesCollection, String searchPhrase) {}

    @Override
    public void visitVolumeCollection(VolumeCollection volumeCollection, String searchPhrase) {}

    @Override
    public void visitIssue(Comic comic, String searchPhrase) {
        if (comic.isMarkup())
        {
            SearchResults.Instance().tryAddComic(comic);
        }
    }

}
