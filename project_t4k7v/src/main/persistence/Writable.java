package persistence;

import org.json.JSONObject;

// reference: JsonSerializationDemo, Writable.java interface
// an interface for any type that needs to be written so that
// subclasses MUST implement a method that converts the type (this)
// into a JSON object
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}

