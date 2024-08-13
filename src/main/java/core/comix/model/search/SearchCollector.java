package core.comix.model.search;

import core.comix.model.collections.database.FullCollection;
import core.comix.model.collections.personal.PublisherCollection;
import core.comix.model.collections.personal.SeriesCollection;
import core.comix.model.collections.personal.VolumeCollection;
import core.comix.model.comic.Comic;

public interface SearchCollector 
{
    public void visitFullCollection(FullCollection fullCollection, String searchPhrase);

    public void visitPublisherCollection(PublisherCollection publisherCollection, String searchPhrase);

    public void visitSeriesCollection(SeriesCollection seriesCollection, String searchPhrase);

    public void visitVolumeCollection(VolumeCollection volumeCollection, String searchPhrase);

    public void visitIssue(Comic comic, String searchPhrase);
    

}
