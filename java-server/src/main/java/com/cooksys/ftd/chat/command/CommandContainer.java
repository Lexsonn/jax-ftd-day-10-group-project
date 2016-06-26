package com.cooksys.ftd.chat.command;

import java.util.Map;
import java.util.TreeMap;

public class CommandContainer {
	public static Map<String, AbstractCommand> commandList = new TreeMap<>();
	
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
}
