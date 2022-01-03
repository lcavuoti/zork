package ch.bbw.zork.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RoomTest {
    private Room r;
    private Room north;
    private Room south;
    private Room east;
    private Room west;
    private Item i;

    @BeforeEach
    public void setUp() {
        r = new Room("Room 2.2");

        north = new Room("Floor");
        south = new Room("Room 2.1");
        east = new Room("Stairs");
        west = new Room("WC");

        i = new Item("Laptop", 1200, 0.7);

        r.setExits(north, east, south, west);
    }

    @Test
    public void testItemAdder() {
        r.addItemToRoom(i);

        assertEquals(i, r.getItems().get(r.getItems().size() - 1));
    }

    @Test
    public void testExitSetter() {
        assertEquals(north, r.nextRoom("north"));
        assertEquals(south, r.nextRoom("south"));
        assertEquals(east, r.nextRoom("east"));
        assertEquals(west, r.nextRoom("west"));
    }

    @Test
    public void testItemRemover() {
        r.addItemToRoom(i);
        r.removeItemFromRoom("Laptop");

        assertTrue(r.getItems().isEmpty());
    }

    @Test
    public void nextRoom() {
        assertEquals(west, r.nextRoom("west"));
    }
}