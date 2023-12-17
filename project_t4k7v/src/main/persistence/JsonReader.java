package persistence;

import model.Clothing;
import model.Wardrobe;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads workroom from JSON data stored in file
// reference: JsonSerializationDemo, JsonReader.java class
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    // reference: JsonSerializationDemo, JsonReader() constructor
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    //          throws IOException if an error occurs reading data from file
    // reference: JsonSerializationDemo, JsonReader.read() method
    public Wardrobe read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseWardrobe(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    // reference: JsonSerializationDemo, JsonReader.readFile() method
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }

    // EFFECTS: parses workroom from JSON object and returns it
    // reference: JsonSerializationDemo, JsonReader.parseWorkroom() method
    private Wardrobe parseWardrobe(JSONObject jsonObject) {
        Wardrobe wr = new Wardrobe();
        parseClothingItems(wr, jsonObject);
        return wr;
    }

    // MODIFIES: wr
    // EFFECTS: parses thingies from JSON object and adds them to workroom
    // reference: JsonSerializationDemo, JsonReader.addThingies() method
    private void parseClothingItems(Wardrobe wd, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("allClothes");
        for (Object json : jsonArray) {
            JSONObject nextItem = (JSONObject) json;
            parseClothing(wd, nextItem);
        }
    }

    // MODIFIES: wr
    // EFFECTS: parses thingy from JSON object and adds it to workroom
    // reference: JsonSerializationDemo, JsonReader.addThingy() method
    private void parseClothing(Wardrobe wd, JSONObject jsonObject) {
        String id = jsonObject.getString("id");
        String type = jsonObject.getString("type");
        String colour = jsonObject.getString("colour");
        String fit = jsonObject.getString("fit");
        String mood = jsonObject.getString("mood");
        String dressCode = jsonObject.getString("dressCode");
        Clothing newClothing = new Clothing(id,type,colour,fit,mood,dressCode);
        wd.addClothing(newClothing);
    }
}
