package core.comix.persistence.pc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import core.comix.controller.CommandInputException;
import core.comix.model.collections.personal.IPopulableCollection;
import core.comix.model.comic.*;
import core.comix.model.user.User;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class XMLCollectionManager implements ICollectionStorageManager {
    private String FILENAME;

    public XMLCollectionManager(String rootDir, User user){
        // Set the FILENAME
        if (user == null){
            FILENAME = "src/main/resources/userCollections/emptyPC.xml";
        } else{
            String filehash = user.getFileHash();
            FILENAME = rootDir + filehash + ".xml";
        }
    }

    public XMLCollectionManager(String fullFilename){
        // Set the FILENAME
        FILENAME = fullFilename;
    }

    public void loadCollectionFromStorage(IPopulableCollection collection) throws CommandInputException{
        XmlMapper xmlMapper = new XmlMapper();
        File file = new File(FILENAME);
        collection.clearNoSave();
        try {
            List<String> xmlLines = Files.readAllLines(file.toPath());

            for (String xmlLine : xmlLines) {
                JsonNode comicNode = xmlMapper.readTree(xmlLine);

                String storyTitle = comicNode.path("storyTitle").asText("");
                String issueNum = comicNode.path("issueNum").asText("");
                String publicationDate = comicNode.path("publicationDate").asText("");
                ArrayList<String> principleCharacters = parseStringArray(comicNode.path("principleCharacters"));
                ArrayList<String> creators = parseStringArray(comicNode.path("creators"));
                String description = comicNode.path("description").asText("");
                float value = (float) comicNode.path("value").asDouble(0.0);
                String publisher = comicNode.path("publisher").asText("");
                String series = comicNode.path("series").asText("");
                int volumeNum = comicNode.path("volumeNum").asInt(0);

                // Create a new UserComic with the extracted values
                Comic currentComic =  new UserComic(storyTitle, issueNum, publicationDate, principleCharacters, creators, description, value, publisher, series, volumeNum);

                // Markups
                int graded = comicNode.path("graded").asInt(0);
                if (graded > 0) {
                    currentComic = new Graded(graded, currentComic);
                }

                if (comicNode.path("slabbed").asText("").length() > 0) {
                    currentComic = new Slabbed(currentComic);
                }

                String signatureString = comicNode.path("signatures").asText("");
                if (signatureString.length() > 0) {
                    ArrayList<String> signatures = new ArrayList<String>(Arrays.asList(signatureString.split(" \\| ")));
                    for (String sig : signatures){
                        currentComic = new Signed(sig, currentComic);
                    }
                }

                String authString = comicNode.path("authenticates").asText("");
                if (authString.length() > 0) {
                    ArrayList<String> auths = new ArrayList<String>(Arrays.asList(authString.split(" \\| ")));
                    for (String auth : auths){
                        currentComic = new Signed(auth, currentComic);
                    }
                }

                collection.addComic(currentComic);
            }
        } catch (IOException e){
            throw new CommandInputException("Error while reading from "+FILENAME);
        }
    }

    // Same as parsing from JSON
    private ArrayList<String> parseStringArray(JsonNode arrayNode) {
        if (arrayNode == null || arrayNode.isNull() || arrayNode.isEmpty()) {
            return new ArrayList<>();
        }

        ArrayList<String> result = new ArrayList<>();
        arrayNode.elements().forEachRemaining(element -> result.add(element.asText()));
        return result;
    }

    public void saveCollectionToStorage(IPopulableCollection collection) throws CommandInputException{
        XmlMapper objectMapper = new XmlMapper();
        try (FileWriter xmlWriter = new FileWriter(FILENAME)){
            for (Comic currentComic : collection.getAllComics()) {
                // Serialize the Comic object to XML and write to the file
                String xml = objectMapper.writeValueAsString(currentComic);
                xmlWriter.write(xml);
                // Add a new line for each comic
                xmlWriter.write(System.lineSeparator());
            }
            xmlWriter.flush();
        } catch (IOException e){
            throw new CommandInputException("Error while writing to "+FILENAME);
        }
    }
}
