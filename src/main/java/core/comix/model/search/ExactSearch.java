package core.comix.model.search;

import java.util.ArrayList;
import core.comix.model.collections.database.FullCollection;
import core.comix.model.collections.personal.PublisherCollection;
import core.comix.model.collections.personal.SeriesCollection;
import core.comix.model.collections.personal.VolumeCollection;
import core.comix.model.comic.Comic;

public class ExactSearch implements SearchCollector {


    public ExactSearch() {
        SearchResults.Instance();
    }


    @Override
    public void visitFullCollection(FullCollection fullCollection, String searchPhrase) {
        ArrayList<Comic> list = fullCollection.getAllComics();
        for (Comic c : list) {
            if (c.getStoryTitle().equals(searchPhrase) || c.getDescription().equals(searchPhrase)) {
                SearchResults.Instance().tryAddComic(c);
            }

            for (String characters : c.getPrincipleCharacters()) {
                if (characters.equals(searchPhrase)) {
                    SearchResults.Instance().tryAddComic(c);
                }
            }

            for (String creator : c.getCreators()) {
                if (creator.equals(searchPhrase)) {
                    SearchResults.Instance().tryAddComic(c);
                }
            }
        }
    }

    @Override
    public void visitPublisherCollection(PublisherCollection publisherCollection,
            String searchPhrase) {
        if (publisherCollection.getPublisherName().equals(searchPhrase)) {
            ArrayList<Comic> list = publisherCollection.getAllComics();
            for (Comic c : list) {
                SearchResults.Instance().tryAddComic(c);
            }
        }
    }

    @Override
    public void visitSeriesCollection(SeriesCollection seriesCollection, String searchPhrase) {
        if (seriesCollection.getSeriesName().equals(searchPhrase)) {
            ArrayList<Comic> list = seriesCollection.getAllComics();
            for (Comic c : list) {
                SearchResults.Instance().tryAddComic(c);
            }
        }

    }

    @Override
    public void visitVolumeCollection(VolumeCollection volumeCollection, String searchPhrase) {
        if (volumeCollection.getVolumeNumber() == Integer.valueOf(searchPhrase)) {
            ArrayList<Comic> list = volumeCollection.getAllComics();
            for (Comic c : list) {
                SearchResults.Instance().tryAddComic(c);
            }
        }
    }

    @Override
    public void visitIssue(Comic comic, String searchPhrase) {
        if (comic instanceof Comic) {
            Comic c = (Comic) comic;
            if (c.getStoryTitle().equals(searchPhrase) || c.getDescription().equals(searchPhrase)) {
                SearchResults.Instance().tryAddComic(c);
            }

            for (String characters : c.getPrincipleCharacters()) {
                if (characters.equals(searchPhrase)) {
                    SearchResults.Instance().tryAddComic(c);
                }
            }

            for (String creator : c.getCreators()) {
                if (creator.equals(searchPhrase)) {
                    SearchResults.Instance().tryAddComic(c);
                }
            }

        }
    }

}
