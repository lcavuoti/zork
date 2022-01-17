package ch.bbw.zork;

import ch.bbw.zork.entity.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

class GameTest {
    private Game game;
    private Item i;

    @BeforeEach
    public void setUpEach() {
        game = new Game();

        i = new Item("Test", 120, 0.2);
    }

    @Test
    public void testItemAdder() {
        List<Item> items = new ArrayList<>();
        items.add(i);

        game.addItemsToRooms(items);

        assertTrue(checkRooms());
    }

    private boolean checkRooms() {
        return game.getGblock().getItems().size() != 0 || game.getLab().getItems().size() != 0 || game.getOffice().getItems().size() != 0 || game.getOutside().getItems().size() != 0 || game.getTavern().getItems().size() != 0;
    }
}