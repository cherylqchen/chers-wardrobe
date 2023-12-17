package persistence;

import model.Clothing;
import model.Wardrobe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest extends JsonTest {
    private Wardrobe wd;

    private Clothing top1;
    private Clothing top2;
    private Clothing bottom1;
    private Clothing bottom2;
    private Clothing jacket1;
    private Clothing jacket2;
    private Clothing accessory1;
    private Clothing accessory2;

    @BeforeEach
    void setUp() {
        top1 = new Clothing("Y2K fairy blouse","top","green","tight",
                "whimsical","casual");
        top2 = new Clothing("Black buttoned long-sleeve","top","black","tight",
                "elegant","casual");
        bottom1 = new Clothing("Farrah jeans","bottom","blue","baggy",
                "elegant","business casual");
        bottom2 = new Clothing("Uniqlo floral skirt","bottom","blue","tight",
                "feminine","business casual");
        jacket1 = new Clothing("Mom's leather jacket","jacket","beige","tight",
                "cool","casual");
        jacket2 = new Clothing("Tweed blazer","jacket","brown","baggy",
                "academic","formal");
        accessory1 = new Clothing("Bejeweled purse","accessory","silver","comfy",
                "glam","black tie");
        accessory2 = new Clothing("Plaid beret","accessory","brown","comfy",
                "chic","cocktail");

        wd = new Wardrobe();
    }

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWardrobe() {
        try {
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyWardrobe.json");
            writer.open();
            writer.write(wd);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyWardrobe.json");
            wd = reader.read();
            List<Clothing> testClothes = wd.getAllClothes();
            assertEquals(0, testClothes.size());
        } catch (IOException e) {
            fail("Exception should not have been thrown.");
        }
    }

    @Test
    void testWriterFilledWardrobe() {
        try {
            wd.addClothing(top1);
            wd.addClothing(jacket2);
            JsonWriter writer = new JsonWriter("./data/testWriterFilledWardrobe.json");
            writer.open();
            writer.write(wd);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterFilledWardrobe.json");
            wd = reader.read();
            List<Clothing> testClothes = wd.getAllClothes();
            List<Clothing> testTops = wd.getTops();
            List<Clothing> testBottoms = wd.getBottoms();
            List<Clothing> testJackets = wd.getJackets();
            List<Clothing> testAccessories = wd.getAccessories();
            assertEquals(2, testClothes.size());
            checkClothing(testClothes.get(0), "Y2K fairy blouse","top","green",
                    "tight","whimsical","casual");
            checkClothing(testClothes.get(1), "Tweed blazer","jacket","brown",
                    "baggy","academic","formal");
            assertEquals(1,testTops.size());
            assertEquals(0,testBottoms.size());
            assertEquals(1,testJackets.size());
            assertEquals(0,testAccessories.size());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterFullerWardrobe() {
        try {
            wd.addClothing(bottom2);
            wd.addClothing(accessory1);
            wd.addClothing(jacket1);
            wd.addClothing(jacket2);
            wd.addClothing(top2);
            wd.addClothing(accessory2);
            wd.addClothing(bottom1);
            JsonWriter writer = new JsonWriter("./data/testWriterFilledWardrobe.json");
            writer.open();
            writer.write(wd);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterFilledWardrobe.json");
            wd = reader.read();
            List<Clothing> testClothes = wd.getAllClothes();
            List<Clothing> testTops = wd.getTops();
            List<Clothing> testBottoms = wd.getBottoms();
            List<Clothing> testJackets = wd.getJackets();
            List<Clothing> testAccessories = wd.getAccessories();
            assertEquals(7, testClothes.size());
            checkClothing(testClothes.get(0), "Uniqlo floral skirt","bottom",
                    "blue","tight","feminine","business casual");
            checkClothing(testClothes.get(1), "Bejeweled purse","accessory",
                    "silver","comfy", "glam","black tie");
            checkClothing(testClothes.get(2), "Mom's leather jacket","jacket",
                    "beige","tight","cool","casual");
            checkClothing(testClothes.get(3), "Tweed blazer","jacket","brown",
                    "baggy","academic","formal");
            checkClothing(testClothes.get(4),"Black buttoned long-sleeve","top",
                    "black","tight","elegant","casual");
            checkClothing(testClothes.get(5),"Plaid beret","accessory","brown","comfy",
                    "chic","cocktail");
            checkClothing(testClothes.get(6),"Farrah jeans","bottom","blue","baggy",
                    "elegant","business casual");
            assertEquals(1,testTops.size());
            assertEquals(2,testBottoms.size());
            assertEquals(2,testJackets.size());
            assertEquals(2,testAccessories.size());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}