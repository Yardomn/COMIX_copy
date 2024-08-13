package core.comix.model.search;

import java.util.ArrayList;
import java.util.Collections;
import core.comix.model.collections.database.FullCollection;
import core.comix.model.collections.personal.PublisherCollection;
import core.comix.model.collections.personal.SeriesCollection;
import core.comix.model.collections.personal.VolumeCollection;
import core.comix.model.comic.Comic;

public class GapsSearch implements SearchCollector {
    @Override
    public void visitFullCollection(FullCollection fullCollection, String searchPhrase) {
        SearchResults.Instance().clearSearchResults();
        // TODO ask if u need to be able to search this
    }

    @Override
    public void visitPublisherCollection(PublisherCollection publisherCollection,
            String searchPhrase) {}

    @Override
    public void visitSeriesCollection(SeriesCollection seriesCollection, String searchPhrase) {
        ArrayList<Comic> list = seriesCollection.getAllComics();

        Collections.sort(list);

        int runsCounter = 1;
        int gapsCounter = 0;
        for (int i = 1; i < list.size(); i++) {
            int diff = Integer.parseInt(list.get(i).getIssueNum())
                    - Integer.parseInt(list.get(i - 1).getIssueNum());

            if (diff == 1) {
                runsCounter++;
            } else if (diff > 1) {
                if (gapsCounter + (diff - 1) <= 2) {
                    gapsCounter += (diff - 1);
                    runsCounter++;
                } else if (runsCounter >= 12) {
                    for (int j = i - runsCounter; j < i; j++) {
                        SearchResults.Instance().tryAddComic(list.get(j));

                    }

                    runsCounter = 1;
                    gapsCounter = 0;

                } else {
                    runsCounter = 1;
                    gapsCounter = 0;

                }
            }


        }
    }

    @Override
    public void visitVolumeCollection(VolumeCollection volumeCollection, String searchPhrase) {}

    @Override
    public void visitIssue(Comic comic, String searchPhrase) {}
}
