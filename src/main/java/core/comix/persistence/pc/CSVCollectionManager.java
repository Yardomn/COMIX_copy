package core.comix.persistence.pc;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import com.opencsv.*;
import com.opencsv.exceptions.CsvValidationException;

import core.comix.controller.CommandInputException;
import core.comix.model.collections.personal.IPopulableCollection;
import core.comix.model.comic.*;
import core.comix.model.user.ActiveUser;
import core.comix.model.user.GuestUser;
import core.comix.model.user.User;

public class CSVCollectionManager implements ICollectionStorageManager {
    private String FILENAME;
    private CSVReader reader = null;
    private ICSVWriter writer = null;


    public CSVCollectionManager(String rootDir, User user){
        // Set the FILENAME
        if (user == null){
            FILENAME = "src/main/resources/userCollections/emptyPC.csv";
        } else {
            String filehash = user.getFileHash();
            FILENAME = rootDir + filehash + ".csv";
        }
    }

    public CSVCollectionManager(String fullFilename){
        // Set the FILENAME
        FILENAME = fullFilename;
    }

    /*
     * NOTE: Does not clear collection before loading.
     */
    public void loadCollectionFromStorage(IPopulableCollection collection) throws CommandInputException{
        // Create a CSV Reader
        try {
            this.reader = new CSVReaderBuilder(new FileReader(FILENAME))
                    .withCSVParser(new CSVParserBuilder()
                            .withQuoteChar('\"')
                            .withSeparator(',')
                            .build())
                    .build();
        } catch (FileNotFoundException e) {
            throw new CommandInputException("File Not Found: Error while reading from "+FILENAME);
        }

        collection.clearNoSave();

        String[] nextLine;
        try {
            reader.readNext(); // cut out top row
            while (true) {
                nextLine = reader.readNext();
                if (nextLine == null) {
                    break;
                }
                ArrayList<String> characters = new ArrayList<String>(Arrays.asList(nextLine[3].split(" \\| ")));
                ArrayList<String> creators = new ArrayList<String>(Arrays.asList(nextLine[4].split(" \\| ")));
                Comic newComic = new UserComic(nextLine[0], nextLine[1], nextLine[2], characters, creators, nextLine[5], Float.parseFloat(nextLine[6]), nextLine[7], nextLine[8], Integer.parseInt(nextLine[9]));
                if (nextLine[10].length() > 0) {
                    newComic = new Graded(Integer.parseInt(nextLine[10]), newComic);
                }
                if (nextLine[11].length() > 0) {
                    newComic = new Slabbed(newComic);
                }
                if (nextLine[12].length() > 0){
                    ArrayList<String> signatures = new ArrayList<String>(Arrays.asList(nextLine[12].split(" \\| ")));
                    for (String sig : signatures){
                        newComic = new Signed(sig, newComic);
                    }
                }
                if (nextLine[13].length() > 0){
                    ArrayList<String> auths = new ArrayList<String>(Arrays.asList(nextLine[13].split(" \\| ")));
                    for (String auth : auths){
                        newComic = new Authenticated(auth, newComic);
                    }
                }

                collection.addComic(newComic);
            }
            reader.close();
        } catch (CsvValidationException e) {
            throw new CommandInputException("Error while parsing CSV from "+FILENAME);
        } catch (IOException e) {
            throw new CommandInputException("Error while reading from "+FILENAME);
        }
    }

    public void saveCollectionToStorage(IPopulableCollection collection) throws CommandInputException {
        // Create a CSV Writer
        try {
            this.writer = new CSVWriterBuilder(new FileWriter(FILENAME))
                    .withQuoteChar('\"')
                    .withSeparator(',')
                    .build();
        } catch (FileNotFoundException e) {
            throw new CommandInputException("File Not Found: Error while writing to "+FILENAME);
        } catch (IOException e) {
            throw new CommandInputException("Error while writing to "+FILENAME);
        }

        writer.writeNext(new String[] {"storyTitle", "issueNum", "publicationDate", "principleCharacters", "creators", "description", "value", "publisher", "series", "volumeNum", "grade", "isSlabbed", "signatures", "authenticated"});
        for (Comic c : collection.getAllComics()) {
            String[] test = c.getSerializedComic();
            writer.writeNext(c.getSerializedComic());
        }
        try {
            writer.close();
        } catch (IOException e) {
            throw new CommandInputException("Error while writing to "+FILENAME);
        }
    }
}
