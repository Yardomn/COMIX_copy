package core.comix.model.sort;

import core.comix.model.comic.Comic;

public class DefaultSort implements SortMethod {
        @Override
        public int compare(Comic comic1, Comic comic2) {
            // Compare by series name
            int seriesComparison = comic1.getSeries().compareTo(comic2.getSeries());
            if (seriesComparison != 0) {
                return seriesComparison;
            }

            // If series name is the same, compare by title
            int titleComparison = comic1.getStoryTitle().compareTo(comic2.getStoryTitle());
            if (titleComparison != 0) {
                return titleComparison;
            }

            // If title is the same, compare by volume number
            int volumeComparison = Integer.compare(comic1.getVolumeNum(), comic2.getVolumeNum());
            if (volumeComparison != 0) {
                return volumeComparison;
            }

            // If volume number is the same, compare by issue number
            return comic1.getIssueNum().compareTo(comic2.getIssueNum());
        }
}