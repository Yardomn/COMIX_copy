package core.comix.persistence.db;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import com.opencsv.*;
import com.opencsv.exceptions.CsvValidationException;

import core.comix.controller.CommandInputException;
import core.comix.model.collections.database.FullCollection;
import core.comix.model.comic.Comic;
import core.comix.model.comic.DBComic;

public class CustomCSVEditor implements IEditor {
    private String FILENAME;
    private CSVReader reader = null;
    private ICSVWriter writer = null;

    public CustomCSVEditor(String filename) {
        this.FILENAME = filename;
    }

    public void readComics(FullCollection collection) throws CommandInputException{
        // Create a CSV Reader
        collection.clear();
        try {
            this.reader = new CSVReaderBuilder(new FileReader(FILENAME))
                    .withCSVParser(new CSVParserBuilder()
                            .withQuoteChar('\"')
                            .withSeparator(',')
                            .build())
                    .build();
        } catch (FileNotFoundException e) {
            throw new CommandInputException("File Not Found: Failed to read from "+FILENAME);
        }

        String[] nextLine;
        try {
            reader.readNext(); // cut out header
            while (true) {
                nextLine = reader.readNext();
                if (nextLine == null) {
                    return;
                }
                ArrayList<String> characters = new ArrayList<String>(Arrays.asList(nextLine[3].split(" \\| ")));
                ArrayList<String> creators = new ArrayList<String>(Arrays.asList(nextLine[4].split(" \\| ")));
                float value;
                if (nextLine[6].equals("")){
                    value = 0;
                } else {
                    value = Float.parseFloat(nextLine[6]);
                }
                int volNum;
                if (nextLine[9].equals("")){
                    volNum = 0;
                } else {
                    volNum = Integer.parseInt(nextLine[9]);
                }
                DBComic newComic = new DBComic(nextLine[0], nextLine[1], nextLine[2], characters, creators, nextLine[5], value, nextLine[7], nextLine[8], volNum);
                collection.tryAddComic(newComic);
            }
        } catch (CsvValidationException e) {
            throw new CommandInputException("CSV Error: Failed to read from "+FILENAME);
        } catch (IOException e) {
            throw new CommandInputException("Error while attempting to read from "+FILENAME);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new CommandInputException("CSV Error: Failed to read from "+FILENAME);
        }
    }

    public void saveComics(FullCollection collection) throws CommandInputException {
        // Create a CSV Writer
        try {
            writer = new CSVWriterBuilder(new FileWriter(FILENAME))
                    .withQuoteChar('\"')
                    .withSeparator(',')
                    .build();
        } catch (FileNotFoundException e) {
            throw new CommandInputException("File Not Found: Failed to write to "+FILENAME);
        } catch (IOException e) {
            throw new CommandInputException("Error while attempting to write to "+FILENAME);
        }

        // Put back the header
        writer.writeNext(new String[] {"storyTitle", "issueNum", "publicationDate", "principleCharacters", "creators", "description", "value", "publisher", "series", "volumeNum", "grade", "slabbed", "signatures", "authenticated"});
        for (Comic comic : collection.getAllComics()){
            writer.writeNext(comic.getSerializedComic());
        }

        try {
            writer.close();
        } catch (IOException e) {
            throw new CommandInputException("Error while attempting to close "+FILENAME);
        }
    }
}
