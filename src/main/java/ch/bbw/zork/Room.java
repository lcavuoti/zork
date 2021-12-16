package ch.bbw.zork;import java.util.ArrayList;import java.util.HashMap;import java.util.List;/** * Author:  Michael Kolling, Version: 1.1, Date: August 2000 * refactoring: Rinaldo Lanza, September 2020 */public class Room {    private final String description;    private final HashMap<String, Room> exits;    private final List<Item> items;    public Room(String description) {        this.description = description;        this.exits = new HashMap<>();        items = new ArrayList<>();    }    public void addItemToRoom(Item item) {        items.add(item);    }    public void setExits(Room north, Room east, Room south, Room west) {        exits.put("north", north);        exits.put("east", east);        exits.put("south", south);        exits.put("west", west);    }    public List<Item> getItems() {        return items;    }    public String shortDescription() {        return description;    }    public String longDescription() {        return "You are in " + description + ".\n" + exitString();    }    private String exitString() {        return "Exits:" + String.join(" ", exits.keySet());    }    public Room nextRoom(String direction) {        return exits.get(direction);    }}