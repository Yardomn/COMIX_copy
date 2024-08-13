package core.comix.persistence.db;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import core.comix.controller.CommandInputException;
import core.comix.model.collections.database.FullCollection;
import core.comix.model.comic.Comic;
import core.comix.model.comic.DBComic;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class CustomXMLEditor implements IEditor {
    private String FILENAME;

    public CustomXMLEditor(String filename) {
        this.FILENAME = filename;
    }

    public void readComics(FullCollection collection) throws CommandInputException {
        XmlMapper xmlMapper = new XmlMapper();
        File file = new File(FILENAME);
        collection.clear();
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
                DBComic currentComic = new DBComic(storyTitle, issueNum, publicationDate, principleCharacters, creators, description, value, publisher, series, volumeNum);;

                collection.tryAddComic(currentComic);
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

    public void saveComics(FullCollection collection) throws CommandInputException {
        XmlMapper objectMapper = new XmlMapper();
        try {
            FileWriter xmlWriter = new FileWriter(FILENAME);
            for (Comic currentComic : collection.getAllComics()) {
                // Serialize the Comic object to XML and write to the file
                String xml = objectMapper.writeValueAsString(currentComic);
                xmlWriter.write(xml);
                // Add a new line for each comic
                xmlWriter.write(System.lineSeparator());
            }
            xmlWriter.flush();
            xmlWriter.close();
        } catch (IOException e){
            throw new CommandInputException("Error while writing to "+FILENAME);
        }
    }
}
