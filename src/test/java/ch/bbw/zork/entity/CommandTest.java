package ch.bbw.zork.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandTest {
    private Command com;
    private Command com2;
    private Command com3;

    @BeforeEach
    public void setUp() {
        com = new Command("pickup");
        com2 = new Command("go", "north");
        com3 = new Command(null);
    }

    @Test
    public void testConstructor() {
        assertEquals("pickup", com.getCommandWord());

        assertEquals("go", com2.getCommandWord());
        assertEquals("north", com2.getSecondWord());
    }

    @Test
    public void testSecondWord() {
        assertFalse(com.hasSecondWord());
        assertTrue(com2.hasSecondWord());
    }

    @Test
    public void validate() {
        assertFalse(com.isUnknown());
        assertFalse(com2.isUnknown());
        assertTrue(com3.isUnknown());
    }
}