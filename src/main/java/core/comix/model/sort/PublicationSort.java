package core.comix.model.sort;

import core.comix.model.comic.Comic;
import java.util.HashMap;

public class PublicationSort implements SortMethod {
    public static HashMap<String, String> calendarMap;
    static {
        calendarMap = new HashMap<>();
        calendarMap.put("Jan", "1");
        calendarMap.put("Feb", "2");
        calendarMap.put("Mar", "3");
        calendarMap.put("Apr", "4");
        calendarMap.put("May", "5");
        calendarMap.put("Jun", "6");
        calendarMap.put("Jul", "7");
        calendarMap.put("Aug", "8");
        calendarMap.put("Sep", "9");
        calendarMap.put("Oct", "10");
        calendarMap.put("Nov", "11");
        calendarMap.put("Dec", "12");
    }

    @Override
    public int compare(Comic comic1, Comic comic2) {

        
        // Splits publication date on space
        String[] splitPubDate1 = comic1.getPublicationDate().split(" ");
        String[] splitPubDate2 = comic2.getPublicationDate().split(" ");

        // No matter what, first entry is the month abbreviation
        String pubMonth1 = "Jan";
        String pubMonth2 = "Jan";

        String pubDay1 = "1";
        String pubDay2 = "1";

        String pubYear1 = "1";
        String pubYear2 = "1";
        if (splitPubDate1.length == 1){
            pubYear1 = splitPubDate1[0];
        }
        // If length is 2, it's in month year format with no commas
         else if (splitPubDate1.length == 2){
            pubMonth1 = splitPubDate1[0];
            // Set day to 1
            pubDay1 = "1";
            // Set year to the second entry in array
            pubYear1 = splitPubDate1[1];
        } else {
            pubMonth1 = splitPubDate1[0];
            // If length is 3, it's in month day, year format, need to take comma out of the day
            pubDay1 = splitPubDate1[1].substring(0, splitPubDate1[1].length() - 1);
            // Then the third entry is the year
            pubYear1 = splitPubDate1[2];
        }
        if (splitPubDate2.length == 1){
            pubYear2 = splitPubDate2[0];
        }
        // If length is 2, it's in month year format with no commas
         else if (splitPubDate2.length == 2){
            pubMonth2 = splitPubDate2[0];
            // Set year to the second entry in array
            pubYear2 = splitPubDate2[1];
        } else {
            pubMonth2 = splitPubDate2[0];
            // If length is 3, it's in month day, year format, need to take comma out of the day
            pubDay2 = splitPubDate2[1].substring(0, splitPubDate2[1].length() - 1);
            // Then the third entry is the year
            pubYear2 = splitPubDate2[2];
        }

        // First, compare by year
        int yearComparison = pubYear1.compareTo(pubYear2);
        if (yearComparison != 0) {
            return yearComparison;
        }

        // If year is the same, compare by month
        int monthComparison = calendarMap.get(pubMonth1).compareTo(calendarMap.get(pubMonth2));
        if (monthComparison != 0) {
            return monthComparison;
        }

        // If month is the same, compare by day
        return pubDay1.compareTo(pubDay2);
    }
}
