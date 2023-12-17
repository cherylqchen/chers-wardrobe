package persistence;

import model.Wardrobe;
import org.json.JSONObject;
import java.io.*;

// Represents a writer that writes JSON representation of workroom to file
// reference: JsonSerializationDemo, JsonWriter.java class
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    // reference: JsonSerializationDemo, JsonWriter() constructor
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    //          be opened for writing
    // reference: JsonSerializationDemo, JsonWriter.open() method
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of workroom to file
    // reference: JsonSerializationDemo, JsonWriter.write() method
    public void write(Wardrobe wd) {
        JSONObject json = wd.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    // reference: JsonSerializationDemo, JsonWriter.close() method
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    // reference: JsonSerializationDemo, JsonWriter.saveToFile() method
    private void saveToFile(String json) {
        writer.print(json);
    }
}