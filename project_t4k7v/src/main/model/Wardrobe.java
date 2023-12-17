package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

// Represents a digital wardrobe that contains arbitrary no. of clothing items
// in allClothes and four "sub-lists" for all items of a certain type
public class Wardrobe implements Writable {
    private List<Clothing> allClothes;   // all clothing added so far
    private List<Clothing> tops;         // all clothing of type "top" added
    private List<Clothing> bottoms;      // all clothing of type "bottom" added
    private List<Clothing> jackets;      // all clothing of type "jacket" added
    private List<Clothing> accessories;  // all clothing of type "accessory" added

    // EFFECTS: constructs a wardrobe and initializes every field as an
    //          empty list (of clothing)
    public Wardrobe() {
        this.allClothes = new ArrayList<>();
        this.tops = new ArrayList<>();
        this.bottoms = new ArrayList<>();
        this.jackets = new ArrayList<>();
        this.accessories = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds c to allClothes then the appropriate sub-list depending
    //          on its type
    public void addClothing(Clothing c) {
        this.allClothes.add(c);

        if (c.getType().equalsIgnoreCase("top")) {
            this.tops.add(c);
        } else if (c.getType().equalsIgnoreCase("bottom")) {
            this.bottoms.add(c);
        } else if (c.getType().equalsIgnoreCase("jacket")) {
            this.jackets.add(c);
        } else if (c.getType().equalsIgnoreCase("accessory")) {
            this.accessories.add(c);
        }

        EventLog.getInstance().logEvent(new Event("Clothing item added."));
    }

    // REQUIRES: this.allClothes is not empty
    // MODIFIES: this
    // EFFECTS: removes clothing item with ID "str" from list of all clothes
    //          and also removes item from appropriate sublist
    public void removeClothing(String str) {
        this.allClothes.removeIf(c -> (c.getId().equals(str)));
        this.tops.removeIf(c -> !(this.allClothes.contains(c)));
        this.bottoms.removeIf(c -> !(this.allClothes.contains(c)));
        this.jackets.removeIf(c -> !(this.allClothes.contains(c)));
        this.accessories.removeIf(c -> !(this.allClothes.contains(c)));

        EventLog.getInstance().logEvent(new Event("Clothing item removed."));
    }

    // REQUIRES: - category is either "colour", "fit", or "mood"
    //           - toFilter must be a Wardrobe field, e.g, this.bottoms
    // EFFECTS: filters given list of clothing, toFilter, by preference
    //          (e.g, "preppy") in category (e.g, mood), and returns the
    //          filtered list
    public List<Clothing> filterAll(String category, String preference,
                                    List<Clothing> toFilter) {
        Predicate<Clothing> filter;

        if (category.equals("colour")) {
            filter = c -> c.getColour().equals(preference);
        } else if (category.equals("fit")) {
            filter = c -> c.getFit().equals(preference);
        } else if (category.equals("mood")) {
            filter = c -> c.getMood().equals(preference);
        } else {
            filter = c -> c.getDressCode().equals(preference);
        }

        EventLog.getInstance().logEvent(new Event("Filtered wardrobe for search criteria."));
        return toFilter.stream().filter(filter).collect(Collectors.toList());
    }

    // EFFECTS: creates and returns this as JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("allClothes", clothingToJson(this.allClothes));
        json.put("tops", clothingToJson(this.tops));
        json.put("bottoms", clothingToJson(this.bottoms));
        json.put("jackets", clothingToJson(this.jackets));
        json.put("accessories", clothingToJson(this.accessories));
        return json;
    }

    // EFFECTS: returns things in this workroom as a JSON array
    private JSONArray clothingToJson(List<Clothing> field) {
        JSONArray jsonArray = new JSONArray();

        for (Clothing c : field) {
            jsonArray.put(c.toJson());
        }

        return jsonArray;
    }

    public List<Clothing> getAllClothes() {
        return this.allClothes;
    }

    public List<Clothing> getTops() {
        return this.tops;
    }

    public List<Clothing> getBottoms() {
        return this.bottoms;
    }

    public List<Clothing> getJackets() {
        return this.jackets;
    }

    public List<Clothing> getAccessories() {
        return this.accessories;
    }
}
