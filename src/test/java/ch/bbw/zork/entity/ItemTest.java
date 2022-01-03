package ch.bbw.zork.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {
    private Item item;

    @BeforeEach
    public void setUp() {
        item = new Item("Ben", 0, 77);
    }

    @Test
    public void testConstructor() {
        assertEquals("Ben", item.getName());
        assertEquals(0, item.getValue());
        assertEquals(77, item.getWeight());
    }

    @Test
    public void testSetter() {
        item.setName("Lore");
        item.setValue(100000);
        item.setWeight(1919191);

        assertEquals("Lore", item.getName());
        assertEquals(100000, item.getValue());
        assertEquals(1919191, item.getWeight());
    }
}