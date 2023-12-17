package persistence;

import model.Clothing;
import model.Wardrobe;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Wardrobe wd = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWardrobe() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyWardrobe.json");
        try {
            Wardrobe wd = reader.read();
            List<Clothing> testClothes = wd.getAllClothes();
            assertEquals(0, testClothes.size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderFilledWardrobe() {
        JsonReader reader = new JsonReader("./data/testReaderFilledWardrobe.json");
        try {
            Wardrobe wd = reader.read();
            List<Clothing> testClothes = wd.getAllClothes();
            List<Clothing> testTops = wd.getTops();
            List<Clothing> testBottoms = wd.getBottoms();
            List<Clothing> testJackets = wd.getJackets();
            List<Clothing> testAccessories = wd.getAccessories();
            assertEquals(9, testClothes.size());
            checkClothing(testClothes.get(0), "UBC sweater","top",
                    "blue","comfy","sporty","casual");
            checkClothing(testClothes.get(1), "Hetian jade necklace","accessory",
                    "green","comfy", "traditional","business casual");
            checkClothing(testClothes.get(2), "Cider ruched dress","top",
                    "green","tight","flirty","cocktail");
            checkClothing(testClothes.get(3), "Knitted cardigan","jacket","green",
                    "baggy","fairy","casual");
            checkClothing(testClothes.get(4),"Black cargos","bottom",
                    "black","baggy","sporty","casual");
            checkClothing(testClothes.get(5),"Cider white strappy dress","top",
                    "white","tight", "elegant","white tie");
            checkClothing(testClothes.get(6),"Cargo jacket","jacket","beige",
                    "comfy", "military","business casual");
            checkClothing(testClothes.get(7),"Hammered ring","accessory","gold",
                    "tight","elegant","formal");
            checkClothing(testClothes.get(8),"Twisted rope ring","accessory","gold",
                    "tight","elegant","formal");
            assertEquals(3,testTops.size());
            assertEquals(1,testBottoms.size());
            assertEquals(2,testJackets.size());
            assertEquals(3,testAccessories.size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}