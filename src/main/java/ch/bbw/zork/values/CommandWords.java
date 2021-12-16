package ch.bbw.zork.values;/* * author:  Michael Kolling, Version: 1.0, Date: July 1999 * refactoring: Rinaldo Lanza, September 2020 */import java.util.Arrays;import java.util.List;public class CommandWords {	private final List<String> validCommands = Arrays.asList("go", "quit", "help", "back", "pickup", "deposit");	public boolean isCommand(String commandWord) {		return validCommands.stream().anyMatch(c -> c.equals(commandWord));	}	public String showAll() {		return String.join(" ", validCommands);	}}