package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WardrobeTest {
    private Wardrobe testWardrobe;

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

        testWardrobe = new Wardrobe();
    }

    @Test
    void testConstructor() {
        assertEquals(0,testWardrobe.getAllClothes().size());
    }

    @Test
    void testAddClothingOnce() {
        testWardrobe.addClothing(top1);
        List<Clothing> testAllClothes = testWardrobe.getAllClothes();
        List<Clothing> testTops = testWardrobe.getTops();

        assertEquals(1,testAllClothes.size());
        assertEquals(1,testTops.size());
        assertEquals(top1, testAllClothes.get(0));
        assertEquals(top1, testTops.get(0));

        assertEquals(0,testWardrobe.getBottoms().size());
        assertEquals(0,testWardrobe.getJackets().size());
        assertEquals(0,testWardrobe.getAccessories().size());
    }

    @Test
    void testAddClothingMultipleTimesSameType() {
        testWardrobe.addClothing(bottom1);
        testWardrobe.addClothing(bottom2);
        List<Clothing> testAllClothes = testWardrobe.getAllClothes();
        List<Clothing> testBottoms = testWardrobe.getBottoms();

        assertEquals(2,testAllClothes.size());
        assertEquals(2,testBottoms.size());
        assertEquals(bottom1, testAllClothes.get(0));
        assertEquals(bottom2, testAllClothes.get(1));
        assertEquals(bottom1, testBottoms.get(0));
        assertEquals(bottom2, testBottoms.get(1));

        assertEquals(0,testWardrobe.getTops().size());
        assertEquals(0,testWardrobe.getJackets().size());
        assertEquals(0,testWardrobe.getAccessories().size());
    }

    @Test
    void testAddClothingMultipleTimesDiffType() {
        testWardrobe.addClothing(jacket1);
        testWardrobe.addClothing(accessory1);
        List<Clothing> testAllClothes = testWardrobe.getAllClothes();
        List<Clothing> testJackets = testWardrobe.getJackets();
        List<Clothing> testAccessories = testWardrobe.getAccessories();

        assertEquals(2,testAllClothes.size());
        assertEquals(1,testJackets.size());
        assertEquals(1,testAccessories.size());
        assertEquals(jacket1, testAllClothes.get(0));
        assertEquals(accessory1, testAllClothes.get(1));
        assertEquals(jacket1, testJackets.get(0));
        assertEquals(accessory1, testAccessories.get(0));

        assertEquals(0,testWardrobe.getTops().size());
        assertEquals(0,testWardrobe.getBottoms().size());
    }

    @Test
    void testAddClothingMultipleDiffTypeDiffCapitalization() {
        Clothing topT = new Clothing("Embroidered quarter zip","Top",
                "blue","comfy", "wealthy","business casual");
        Clothing bottomB = new Clothing("Snow cargos","Bottom",
                "black","baggy", "military","casual");
        Clothing jacketJ = new Clothing("Puffy vest","Jacket",
                "black","baggy", "wealthy","casual");
        Clothing accessoryA = new Clothing("Hammered ring","Accessory",
                "gold","tight","feminine","formal");

        testWardrobe.addClothing(topT);
        testWardrobe.addClothing(bottomB);
        testWardrobe.addClothing(jacketJ);
        testWardrobe.addClothing(accessoryA);
        List<Clothing> testAllClothes = testWardrobe.getAllClothes();
        List<Clothing> testTops = testWardrobe.getTops();
        List<Clothing> testBottoms = testWardrobe.getBottoms();
        List<Clothing> testJackets = testWardrobe.getJackets();
        List<Clothing> testAccessories = testWardrobe.getAccessories();

        assertEquals(4,testAllClothes.size());
        assertEquals(1,testTops.size());
        assertEquals(1,testBottoms.size());
        assertEquals(1,testJackets.size());
        assertEquals(1,testAccessories.size());

        assertEquals(topT, testAllClothes.get(0));
        assertEquals(bottomB, testAllClothes.get(1));
        assertEquals(jacketJ, testAllClothes.get(2));
        assertEquals(accessoryA, testAllClothes.get(3));

        assertEquals(topT, testJackets.get(0));
        assertEquals(bottomB, testAccessories.get(0));
        assertEquals(jacketJ, testJackets.get(0));
        assertEquals(accessoryA, testAccessories.get(0));
    }

    @Test
    void testAddClothingFail() {
        Clothing poncho = new Clothing("Embroidered quarter zip","poncho",
                "blue","comfy", "wealthy","business casual");

        testWardrobe.addClothing(poncho);
        List<Clothing> testAllClothes = testWardrobe.getAllClothes();
        List<Clothing> testTops = testWardrobe.getTops();
        List<Clothing> testBottoms = testWardrobe.getBottoms();
        List<Clothing> testJackets = testWardrobe.getJackets();
        List<Clothing> testAccessories = testWardrobe.getAccessories();

        assertEquals(0,testAllClothes.size());
        assertEquals(0,testTops.size());
        assertEquals(0,testBottoms.size());
        assertEquals(0,testJackets.size());
        assertEquals(0,testAccessories.size());
    }

    @Test
    void testRemoveClothingOnceNowEmptyWardrobe() {
        testWardrobe.addClothing(jacket1);
        assertEquals(1,testWardrobe.getAllClothes().size());

        testWardrobe.removeClothing("Mom's leather jacket");
        assertEquals(0,testWardrobe.getAllClothes().size());
        assertEquals(0,testWardrobe.getTops().size());
        assertEquals(0,testWardrobe.getBottoms().size());
        assertEquals(0,testWardrobe.getJackets().size());
        assertEquals(0,testWardrobe.getAccessories().size());
    }

    @Test
    void testRemoveClothingOnceFromFullerWardrobe() {
        testWardrobe.addClothing(top2);
        testWardrobe.addClothing(bottom1);
        testWardrobe.addClothing(jacket2);
        testWardrobe.addClothing(accessory2);
        List<Clothing> testAllClothes = testWardrobe.getAllClothes();
        assertEquals(4,testAllClothes.size());

        testWardrobe.removeClothing("Tweed blazer");
        assertEquals(3,testAllClothes.size());
        assertEquals(top2,testAllClothes.get(0));
        assertEquals(bottom1,testAllClothes.get(1));
        assertEquals(accessory2,testAllClothes.get(2));
        assertEquals(1,testWardrobe.getTops().size());
        assertEquals(1,testWardrobe.getBottoms().size());
        assertEquals(0,testWardrobe.getJackets().size());
        assertEquals(1,testWardrobe.getAccessories().size());
    }

    @Test
    void testRemoveClothingMultipleTimesNowEmptyWardrobe() {
        testWardrobe.addClothing(top1);
        testWardrobe.addClothing(bottom2);
        testWardrobe.addClothing(jacket1);
        testWardrobe.addClothing(accessory2);
        assertEquals(4,testWardrobe.getAllClothes().size());

        testWardrobe.removeClothing("Plaid beret");
        testWardrobe.removeClothing("Mom's leather jacket");
        testWardrobe.removeClothing("Uniqlo floral skirt");
        testWardrobe.removeClothing("Y2K fairy blouse");
        assertEquals(0,testWardrobe.getAllClothes().size());
        assertEquals(0,testWardrobe.getTops().size());
        assertEquals(0,testWardrobe.getBottoms().size());
        assertEquals(0,testWardrobe.getJackets().size());
        assertEquals(0,testWardrobe.getAccessories().size());
    }

    @Test
    void testRemoveClothingMultipleTimesFromFullerWardrobe() {
        testWardrobe.addClothing(accessory1);
        testWardrobe.addClothing(jacket2);
        testWardrobe.addClothing(bottom1);
        testWardrobe.addClothing(top2);
        assertEquals(4,testWardrobe.getAllClothes().size());

        testWardrobe.removeClothing("Black buttoned long-sleeve");
        testWardrobe.removeClothing("Bejeweled purse");
        assertEquals(2,testWardrobe.getAllClothes().size());
        assertEquals(0,testWardrobe.getTops().size());
        assertEquals(1,testWardrobe.getBottoms().size());
        assertEquals(1,testWardrobe.getJackets().size());
        assertEquals(0,testWardrobe.getAccessories().size());
    }

    @Test
    void testRemoveNotThere() {
        testWardrobe.addClothing(accessory1);
        testWardrobe.addClothing(jacket2);
        testWardrobe.addClothing(bottom1);
        testWardrobe.addClothing(top2);
        assertEquals(4,testWardrobe.getAllClothes().size());

        testWardrobe.removeClothing("Fuzzy sweater");
        assertEquals(4,testWardrobe.getAllClothes().size());
    }

    @Test
    void testFilterAllColour() {
        testWardrobe.addClothing(jacket2);
        testWardrobe.addClothing(bottom1);
        testWardrobe.addClothing(accessory2);

        List<Clothing> testList = testWardrobe.filterAll("colour","brown",
                testWardrobe.getAllClothes());
        assertEquals(2,testList.size());
        assertEquals(jacket2,testList.get(0));
        assertEquals(accessory2,testList.get(1));
    }

    @Test
    void testFilterAllFit() {
        testWardrobe.addClothing(top1);
        testWardrobe.addClothing(jacket2);
        testWardrobe.addClothing(accessory2);
        testWardrobe.addClothing(top2);
        testWardrobe.addClothing(bottom2);

        List<Clothing> testList = testWardrobe.filterAll("fit","tight",
                testWardrobe.getAllClothes());
        assertEquals(3,testList.size());
        assertEquals(top1,testList.get(0));
        assertEquals(top2,testList.get(1));
        assertEquals(bottom2,testList.get(2));
    }

    @Test
    void testFilterAllMood() {
        testWardrobe.addClothing(bottom1);
        testWardrobe.addClothing(jacket1);
        testWardrobe.addClothing(top2);
        testWardrobe.addClothing(top1);

        List<Clothing> testList = testWardrobe.filterAll("mood","elegant",
                testWardrobe.getAllClothes());
        assertEquals(2,testList.size());
        assertEquals(bottom1,testList.get(0));
        assertEquals(top2,testList.get(1));
    }

    @Test
    void testFilterAllDressCode() {
        testWardrobe.addClothing(accessory1);
        testWardrobe.addClothing(jacket2);
        testWardrobe.addClothing(top1);
        testWardrobe.addClothing(bottom2);
        testWardrobe.addClothing(accessory2);
        testWardrobe.addClothing(jacket1);


        List<Clothing> testList = testWardrobe.filterAll("dress code","cocktail",
                testWardrobe.getAllClothes());
        assertEquals(1,testList.size());
        assertEquals(accessory2,testList.get(0));
    }

    @Test
    void testFilterTopsDressCode() {
        Clothing c1 = new Clothing("Mykonos billowy shirt","top","white","tight",
                "mediterranean","cocktail");
        Clothing c2 = new Clothing("Peasant top","top","brown","tight",
                "medieval","casual");

        testWardrobe.addClothing(accessory1);
        testWardrobe.addClothing(c1);
        testWardrobe.addClothing(c2);
        testWardrobe.addClothing(jacket2);
        testWardrobe.addClothing(top1);
        testWardrobe.addClothing(bottom2);
        testWardrobe.addClothing(top2);
        testWardrobe.addClothing(jacket1);

        List<Clothing> testList = testWardrobe.filterAll("dress code","casual",
                testWardrobe.getTops());
        assertEquals(3,testList.size());
        assertEquals(c2,testList.get(0));
        assertEquals(top1,testList.get(1));
        assertEquals(top2,testList.get(2));
    }

    @Test
    void testFilterBottomsFit() {
        Clothing c1 = new Clothing("Levi's jeans","bottom","grey","comfy",
                "corporate","business casual");
        Clothing c2 = new Clothing("Cargo pants","bottom","beige","comfy",
                "techy","casual");

        testWardrobe.addClothing(bottom2);
        testWardrobe.addClothing(jacket1);
        testWardrobe.addClothing(top1);
        testWardrobe.addClothing(c1);
        testWardrobe.addClothing(bottom1);
        testWardrobe.addClothing(jacket2);
        testWardrobe.addClothing(accessory2);
        testWardrobe.addClothing(c2);

        List<Clothing> testList = testWardrobe.filterAll("fit","baggy",
                testWardrobe.getBottoms());
        assertEquals(1,testList.size());
        assertEquals(bottom1,testList.get(0));
    }

    @Test
    void testFilterJacketsColour() {
        Clothing c1 = new Clothing("Moto jacket","jacket","red","comfy",
                "cool","casual");
        Clothing c2 = new Clothing("Vintage leather","jacket","red","tight",
                "vintage","cocktail");

        testWardrobe.addClothing(jacket1);
        testWardrobe.addClothing(c2);
        testWardrobe.addClothing(accessory2);
        testWardrobe.addClothing(bottom2);
        testWardrobe.addClothing(accessory1);
        testWardrobe.addClothing(jacket2);
        testWardrobe.addClothing(c1);
        testWardrobe.addClothing(top1);

        List<Clothing> testList = testWardrobe.filterAll("colour","red",
                testWardrobe.getJackets());
        assertEquals(2,testList.size());
        assertEquals(c2,testList.get(0));
        assertEquals(c1,testList.get(1));
    }

    @Test
    void testFilterAccessoriesMood() {
        Clothing c1 = new Clothing("NYU gem hoops","accessory","gold","tight",
                "glam","casual");
        Clothing c2 = new Clothing("Pear pendant","accessory","white","baggy",
                "elegant","cocktail");

        testWardrobe.addClothing(accessory2);
        testWardrobe.addClothing(bottom1);
        testWardrobe.addClothing(accessory2);
        testWardrobe.addClothing(jacket2);
        testWardrobe.addClothing(accessory1);
        testWardrobe.addClothing(top2);
        testWardrobe.addClothing(c1);
        testWardrobe.addClothing(c2);

        List<Clothing> testList = testWardrobe.filterAll("mood","glam",
                testWardrobe.getAccessories());
        assertEquals(2,testList.size());
        assertEquals(accessory1,testList.get(0));
        assertEquals(c1,testList.get(1));
    }
}