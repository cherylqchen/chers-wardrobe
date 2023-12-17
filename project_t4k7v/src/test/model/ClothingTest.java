package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClothingTest {
    Clothing testClothing;

    @BeforeEach
    void setUp() {
        testClothing = new Clothing("Y2K fairy blouse","top","green","tight",
                            "whimsical","casual");
    }

    @Test
    void testConstructor() {
        assertEquals("Y2K fairy blouse",testClothing.getId());
        assertEquals("top",testClothing.getType());
        assertEquals("green",testClothing.getColour());
        assertEquals("tight",testClothing.getFit());
        assertEquals("whimsical",testClothing.getMood());
        assertEquals("casual",testClothing.getDressCode());
    }

}
