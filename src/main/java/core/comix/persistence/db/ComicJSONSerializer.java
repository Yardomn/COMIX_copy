package core.comix.persistence.db;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import core.comix.model.comic.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class ComicJSONSerializer extends JsonSerializer<Comic> {

    public ComicJSONSerializer(){

    }

    @Override
    public void serialize(Comic comic, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("storyTitle", comic.getStoryTitle());
        jsonGenerator.writeStringField("issueNum", comic.getIssueNum());
        jsonGenerator.writeStringField("publicationDate", comic.getPublicationDate());
        jsonGenerator.writeObjectField("principleCharacters", String.join(" ", comic.getPrincipleCharacters()));
        jsonGenerator.writeObjectField("creators", String.join(" ", comic.getCreators()));
        jsonGenerator.writeStringField("description", comic.getDescription());
        jsonGenerator.writeNumberField("value", comic.getTotalValue());
        jsonGenerator.writeStringField("publisher", comic.getPublisher());
        jsonGenerator.writeStringField("series", comic.getSeries());
        jsonGenerator.writeNumberField("volumeNum", comic.getVolumeNum());


            int grade = 0;
            String slabbed = "";
            ArrayList<String> signatures = new ArrayList<>();
            ArrayList<String> authenticates = new ArrayList<>();
            if(comic.isMarkup()) {
                MarkupOption markup = comic.getMarkup();

                if (markup.isGraded()) {
                    grade = markup.getGrade();
                }
                jsonGenerator.writeNumberField("graded", grade);

                if (markup.isSlabbed()) {
                    slabbed = "True";
                }
                jsonGenerator.writeStringField("slabbed", slabbed);

                if (markup.isSigned() && !(markup.isAuthenticated())) {
                    Signed signed = (Signed) markup;
                    signatures = signed.getSignatures(signatures);
                }
                jsonGenerator.writeStringField("signatures", String.join(" | ", signatures));

                if (markup.isAuthenticated()) {
                    Authenticated authenticated = (Authenticated) markup;
                    signatures = authenticated.getSignatures(signatures);
                    authenticates = authenticated.getAuthenticates(authenticates);
                }
                jsonGenerator.writeStringField("authenticates", String.join(" | ", authenticates));

        }
        jsonGenerator.writeEndObject();
    }
}