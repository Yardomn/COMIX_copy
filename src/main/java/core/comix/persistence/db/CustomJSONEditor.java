package core.comix.persistence.db;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import core.comix.controller.CommandInputException;
import core.comix.model.collections.database.FullCollection;
import core.comix.model.comic.Comic;
import core.comix.model.comic.DBComic;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class CustomJSONEditor implements IEditor {
    private String FILENAME;

    public CustomJSONEditor(String filename) {
        this.FILENAME = filename;
    }

    public void readComics(FullCollection collection) throws CommandInputException{
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(FILENAME);
        collection.clear();
        try {
            // Read each line of the file into a list of lines
            System.out.println(file);
            List<String> lines = Files.readAllLines(file.toPath());

            for (String line : lines) {
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

                // Create a new DBComic with the extracted values
                DBComic currentComic =  new DBComic(storyTitle, issueNum, publicationDate, principleCharacters, creators, description, value, publisher, series, volumeNum);

                collection.tryAddComic(currentComic);
            }
        } catch (IOException e){
            e.printStackTrace();
            throw new CommandInputException("Error while attempting to read from "+FILENAME);
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

    public void saveComics(FullCollection collection) throws CommandInputException {
        ObjectMapper objectMapper = new ObjectMapper();

        // Register the custom json serializer
        SimpleModule module = new SimpleModule();
        module.addSerializer(Comic.class, new ComicJSONSerializer());
        objectMapper.registerModule(module);

        try {
            FileWriter jsonWriter = new FileWriter(FILENAME);
            boolean first = true;
            for (Comic currentComic : collection.getAllComics()) {
                
                // Add a new line for each comic
                if (!first) {
                    jsonWriter.write(System.lineSeparator());
                }
                else {
                    first = false;
                }
                // Serialize the Comic object to JSON and write to the file
                String json = objectMapper.writeValueAsString(currentComic);
                jsonWriter.write(json);
            }
            jsonWriter.flush();
            jsonWriter.close();
        } catch (JsonProcessingException e){
            throw new CommandInputException("JSON Processing Exception: Failed to write to "+FILENAME);
        } catch (IOException e){
            throw new CommandInputException("Error while writing to "+FILENAME);
        }
    }
}
