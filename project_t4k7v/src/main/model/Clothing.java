package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents an item of clothing with id, type, colour, etc. (see below)
public class Clothing implements Writable {
    private String id;        // short description, e.g, "Silk slip dress"
    private String type;      // either "top","bottom","jacket", or "accessory"
    private String colour;    // one main colour of the clothing
    private String fit;       // either "tight", "comfy", or "baggy"
    private String mood;      // style of the clothing, e.g, "sporty", "chic"
    private String dressCode; // either "casual", "business casual",
                              // "formal", "cocktail", or "black tie"

    // REQUIRES: - type is either "top", "bottom", "jacket", or "accessory"
    //           - fit is either "tight, "comfy", or "baggy"
    //           - dressCode is either "casual", "business casual",
    //           "formal", "cocktail", or "black tie"
    // EFFECTS: constructs item of clothing with given "tags" (all string)
    public Clothing(String id, String type, String colour, String fit,
                    String mood, String dressCode) {
        this.id = id;
        this.type = type;
        this.colour = colour;
        this.fit = fit;
        this.mood = mood;
        this.dressCode = dressCode;
    }

    // EFFECTS: creates and returns this as JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("id", this.id);
        json.put("type", this.type);
        json.put("colour", this.colour);
        json.put("fit", this.fit);
        json.put("mood", this.mood);
        json.put("dressCode", this.dressCode);
        return json;
    }

    public String getId() {
        return this.id;
    }

    public String getType() {
        return this.type;
    }

    public String getColour() {
        return this.colour;
    }

    public String getFit() {
        return this.fit;
    }

    public String getMood() {
        return this.mood;
    }

    public String getDressCode() {
        return this.dressCode;
    }

}
