package ch.bbw.zork;import ch.bbw.zork.entity.Command;import ch.bbw.zork.entity.Item;import ch.bbw.zork.entity.Room;import ch.bbw.zork.util.Parser;import ch.bbw.zork.values.Constants;import java.util.ArrayList;import java.util.List;import java.util.Random;import java.util.Scanner;/** * Class Game - the main class of the "Zork" game. * <p> * Author:  Michael Kolling, 1.1, March 2000 * refactoring: Rinaldo Lanza, September 2020 */public class Game {    private final Parser parser;    private Room currentRoom;    private final Room outside;    private final Room lab;    private final Room tavern;    private final Room gblock;    private final Room office;    private Room roomToGoBack;    private final List<Item> pickedUpItems;    private int totalWeight;    public Game() {        pickedUpItems = new ArrayList<>();        totalWeight = 0;        parser = new Parser(System.in);        outside = new Room("outside G block on Peninsula campus");        lab = new Room("lab, a lecture theatre in A block");        tavern = new Room("the Seahorse Tavern (the campus pub)");        gblock = new Room("the G Block");        office = new Room("the computing admin office");        outside.setExits(null, lab, gblock, tavern);        lab.setExits(null, null, null, outside);        tavern.setExits(null, outside, null, null);        gblock.setExits(outside, office, null, null);        office.setExits(null, null, null, gblock);        currentRoom = outside; // start game outside        roomToGoBack = outside;    }    /**     * Main play routine.  Loops until end of play.     */    public void play() {        generateItems();        printWelcome();        // Enter the main command loop.  Here we repeatedly read commands and        // execute them until the game is over.        boolean finished = false;        while (!finished) {            Command command = parser.getCommand();            finished = processCommand(command);        }        System.out.println("Thank you for playing.  Good bye.");    }    private void printWelcome() {        System.out.println();        System.out.println("Welcome to Zork!");        System.out.println("Zork is a simple adventure game.");        System.out.println("Type 'help' if you need help.");        System.out.println();        System.out.println(currentRoom.longDescription());    }    private boolean processCommand(Command command) {        if (command.isUnknown()) {            System.out.println("I don't know what you mean...");            return false;        }        String commandWord = command.getCommandWord();        switch (commandWord) {            case "help" -> printHelp();            case "go" -> goRoom(command);            case "quit" -> quitGame();            case "back" -> {                currentRoom = roomToGoBack;                System.out.println(currentRoom.longDescription());            }            case "pickup" -> pickUpItem();            case "drop" -> dropItem();        }        return false;    }    private void printHelp() {        System.out.println("You are lost. You are alone. You wander");        System.out.println("around at Monash Uni, Peninsula Campus.");        System.out.println();        System.out.println("Your command words are:");        System.out.println(parser.showCommands());    }    private void goRoom(Command command) {        if (!command.hasSecondWord()) {            System.out.println("Go where?");        } else {            String direction = command.getSecondWord();            // Try to leave current room.            Room nextRoom = currentRoom.nextRoom(direction);            if (nextRoom == null)                System.out.println("There is no door!");            else {                roomToGoBack = currentRoom;                currentRoom = nextRoom;                System.out.println(currentRoom.longDescription());            }        }    }    private void generateItems() {        List<Item> items = new ArrayList<>();        items.add(new Item("Beer", 5, 1));        items.add(new Item("Baseball bat", 30, 5));        items.add(new Item("Wine", 20, 3));        items.add(new Item("Lithium", 40, 10));        items.add(new Item("Phosphor", 60, 15));        //stands for Crystal Meth, should be secret        items.add(new Item("CM", 200, 2));        items.add(new Item("Pen", 30, 1));        items.add(new Item("Monitor", 30, 7));        items.add(new Item("Keyboard", 20, 1));        items.add(new Item("Basketball", 50, 3));        items.add(new Item("Shoe", 100, 1));        items.add(new Item("Stick", 1, 1));        items.add(new Item("T-shirt", 400, 1));        items.add(new Item("Phone", 20, 2));        items.add(new Item("Rock", 1000, 20));        addItemsToRooms(items);    }    public void addItemsToRooms(List<Item> items) {        Random rand = new Random();        for (Item item : items) {            int randNum = rand.nextInt(6);            switch (randNum) {                case 1 -> outside.addItemToRoom(item);                case 2 -> lab.addItemToRoom(item);                case 3 -> tavern.addItemToRoom(item);                case 4 -> gblock.addItemToRoom(item);                case 5 -> office.addItemToRoom(item);            }        }    }    private void dropItem() {        boolean found = false;        Item itemToRemove = null;        Scanner keyBoard = new Scanner(System.in);        System.out.println("Inventory ");        for (Item item : pickedUpItems) {            System.out.println("Item: " + item.getName() + " Weight: " + item.getWeight());        }        System.out.println("Enter the name of item to drop: ");        String itemName = keyBoard.nextLine();        for (Item item : pickedUpItems) {            if (item.getName().equalsIgnoreCase(itemName)) {                currentRoom.addItemToRoom(item);                totalWeight -= item.getWeight();                System.out.println("Item dropped");                itemToRemove = item;                found = true;            }        }        if (found) {            pickedUpItems.remove(itemToRemove);            System.out.println(currentRoom.longDescription());        } else {            System.out.println("Couldn't find item. Please retry with drop command");        }    }    private void pickUpItem() {        boolean found = false;        Scanner keyboard = new Scanner(System.in);        System.out.println("Which item? ");        System.out.println("If you don't want to pickup an item then enter: continue");        while (!found) {            String itemName = keyboard.nextLine();            if (itemName.equalsIgnoreCase("continue")) {                found = true;                System.out.println(currentRoom.longDescription());            }            List<Item> items = currentRoom.getItems();            for (Item item : items) {                if (item.getName().equalsIgnoreCase(itemName)) {                    if (item.getWeight() + totalWeight <= Constants.MAX_WEIGHT) {                        pickedUpItems.add(item);                        totalWeight += item.getWeight();                        currentRoom.removeItemFromRoom(itemName);                        System.out.println(itemName + " added to inventory");                    } else {                        System.out.println("You cannot pickup this item. It's exceeding your total weight");                        System.out.println("If you want to pickup this item you first have to drop another item");                    }                    found = true;                    System.out.println(currentRoom.longDescription());                }                if (found) {                    break;                }            }            if (!found) {                System.out.println("Could not find this item. Please reenter the name of it or continue");            }        }    }    private void quitGame() {        int sum = 0;        for (Item item : pickedUpItems) {            sum += item.getValue();        }        System.out.println("Your total money: " + sum);        if (sum > 1000) {            System.out.println("You successfully completed the challenge! Congrats");        } else {            System.out.println("Sadly, you did not have enough money together to complete the challenge");        }        System.out.println("Thanks for playing!");        System.exit(0);    }    public Room getOutside() {        return outside;    }    public Room getLab() {        return lab;    }    public Room getTavern() {        return tavern;    }    public Room getGblock() {        return gblock;    }    public Room getOffice() {        return office;    }}