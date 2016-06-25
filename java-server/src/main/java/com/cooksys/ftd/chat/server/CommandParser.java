package com.cooksys.ftd.chat.server;

import com.cooksys.ftd.chat.command.AbstractCommand;
import com.cooksys.ftd.chat.command.CommandContainer;

public class CommandParser {
	private static Server server;
	
	public CommandParser(Server server) {
		super();
		CommandParser.setServer(server);
	}
	
	public static boolean parseCommand(String input, ClientHandler clientHandler) {
		char delimiter = ' ';
		int delim = input.indexOf(delimiter);
		
		String command = input;
		String message = "";
		if (delim != -1) {
			command = input.substring(0, delim);
			message = input.substring(delim + 1);
		}
		
		AbstractCommand cmd = CommandContainer.commandList.get(command);
		
		if (cmd == null)
			return false;
		cmd.executeCommand(message, clientHandler);
		return true;
	}

	public static Server getServer() {
		return server;
	}

	public static void setServer(Server server) {
		CommandParser.server = server;
	}
}
