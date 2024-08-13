package core.comix.persistence.pc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import core.comix.controller.CommandInputException;
import core.comix.model.collections.personal.IPopulableCollection;
import core.comix.model.comic.*;
import core.comix.model.user.User;
import core.comix.persistence.db.ComicJSONSerializer;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JSONCollectionManager implements ICollectionStorageManager {
    private String FILENAME;

    public JSONCollectionManager(String rootDir, User user){
        // Set the FILENAME
        if (user == null){
            FILENAME = "src/main/resources/userCollections/emptyPC.json";
        } else {
            String filehash = user.getFileHash();
            FILENAME = rootDir + filehash + ".json";
        }
    }

    public JSONCollectionManager(String fullFilename){
        // Set the FILENAME
        FILENAME = fullFilename;
    }

    public void loadCollectionFromStorage(IPopulableCollection collection) throws CommandInputException{
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(FILENAME);
        collection.clearNoSave();
        try {
            // Read each line of the file into a list of lines
            List<String> lines = Files.readAllLines(file.toPath());

            for (String line : lines ) {
                // Read each line into a JsonNode
                JsonNode jsonNode = objectMapper.readTree(line);

                // Extract values from the JsonNode with default values for blank fields ("" for empty String, 0 for empty int)
                String storyTitle = jsonNode.path("storyTitle").asText("");
                String issueNum = jsonNode.path("issueNum").asText("");
                String publicationDate = jsonNode.path("publicationDate").asText("");
                // Call a helper function for parsing arrays
                ArrayList<String> principleCharacters = parseStringArray(jsonNode.path("principleCharacters"));
                ArrayList<String> creators = parseStringArray(jsonNode.path("creators"));
                String description = jsonNode.path("description").asText("");
                float value = (float) jsonNode.path("value").asDouble(0.0);
                String publisher = jsonNode.path("publisher").asText("");
                String series = jsonNode.path("series").asText("");
                int volumeNum = jsonNode.path("volumeNum").asInt(0);

                // Create a new UserComic with the extracted values
                Comic currentComic =  new UserComic(storyTitle, issueNum, publicationDate, principleCharacters, creators, description, value, publisher, series, volumeNum);

                // Markups
                int graded = jsonNode.path("graded").asInt(0);
                if (graded > 0) {
                    currentComic = new Graded(graded, currentComic);
                }

                if (jsonNode.path("slabbed").asText("").length() > 0) {
                    currentComic = new Slabbed(currentComic);
                }

                String signatureString = jsonNode.path("signatures").asText("");
                if (signatureString.length() > 0) {
                    ArrayList<String> signatures = new ArrayList<String>(Arrays.asList(signatureString.split(" \\| ")));
                    for (String sig : signatures){
                        currentComic = new Signed(sig, currentComic);
                    }
                }

                String authString = jsonNode.path("authenticates").asText("");
                if (authString.length() > 0) {
                    ArrayList<String> auths = new ArrayList<String>(Arrays.asList(authString.split(" \\| ")));
                    for (String auth : auths){
                        currentComic = new Signed(auth, currentComic);
                    }
                }

                collection.addComic(currentComic);
            }
        } catch (IOException e){
            throw new CommandInputException("Error while writing to "+FILENAME);
        }
    }

    private ArrayList<String> parseStringArray(JsonNode arrayNode) {
        // If the creators or characters is "" return an empty arraylist
        if (arrayNode == null || arrayNode.isNull() || arrayNode.isEmpty()) {
            return new ArrayList<>();
        }

        // Otherwise, add each element to the array list
        ArrayList<String> result = new ArrayList<>();
        arrayNode.elements().forEachRemaining(element -> result.add(element.asText()));
        return result;
    }

    public void saveCollectionToStorage(IPopulableCollection collection) throws CommandInputException{
        ObjectMapper objectMapper = new ObjectMapper();

        // Register the custom json serializer
        SimpleModule module = new SimpleModule();
        module.addSerializer(Comic.class, new ComicJSONSerializer());
        objectMapper.registerModule(module);

        try (FileWriter jsonWriter = new FileWriter(FILENAME)) {
            for (Comic currentComic : collection.getAllComics()) {
                // Serialize the Comic object to JSON and write to the file
                String json = objectMapper.writeValueAsString(currentComic);
                jsonWriter.write(json);
                // Add a new line for each comic
                jsonWriter.write(System.lineSeparator());
            }
            jsonWriter.flush();
        } catch (JsonProcessingException e) {
            throw new CommandInputException("Error while writing JSON to "+FILENAME);
        } catch (IOException e) {
            throw new CommandInputException("Error while writing to "+FILENAME);
        }
    }
}
