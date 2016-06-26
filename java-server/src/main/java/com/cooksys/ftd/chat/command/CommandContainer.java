package com.cooksys.ftd.chat.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandContainer {
	static Logger log = LoggerFactory.getLogger(ListCommand.class);

	public static Map<String, AbstractCommand> commandList = new TreeMap<>();
	private static List<String> helpCommands = new ArrayList<>();
	static List<String> commandsList = new ArrayList<>();
	
	public static void register(Class<? extends AbstractCommand> clazz) {
		AbstractCommand command;
		try {
			command = clazz.newInstance();
			commandList.put(command.getName(), command);
		} catch (InstantiationException | IllegalAccessException e) {
			System.err.println("You overrode the signature for 'getName' and set it private");
			e.printStackTrace();
		}
	}
	
	public static void ready() {
		generateDescriptors();
	}

	private static void generateDescriptors() { // commands aren't generated on the fly so these can be built at once
		for (AbstractCommand cmd : CommandContainer.commandList.values()) {
			commandsList.add(cmd.getName());
			helpCommands.add(cmd.getName() + "\t" + cmd.getArguments() + ":\t" + cmd.getDescription());
		}
	}

	public static String listCommands() {
		return commandsList.toString();
	}

	public static List<String> getHelpMessages() {
		return helpCommands;
	}
}
