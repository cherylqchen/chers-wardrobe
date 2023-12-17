package persistence;

import model.Clothing;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkClothing(Clothing c, String id, String type, String colour,
                                 String fit, String mood, String dressCode) {
        assertEquals(id, c.getId());
        assertEquals(type, c.getType());
        assertEquals(colour, c.getColour());
        assertEquals(fit, c.getFit());
        assertEquals(mood, c.getMood());
        assertEquals(dressCode, c.getDressCode());
    }
}