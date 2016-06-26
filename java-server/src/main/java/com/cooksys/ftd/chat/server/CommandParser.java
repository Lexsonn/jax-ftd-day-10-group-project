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
		String[] inputs = input.split("\\*", 3);
		String inputType = inputs[1];
		String inputMessage = inputs[2];
				
		if (inputMessage.charAt(0) != '/')
			return false;
		char delimiter = ' ';
		int delim = inputMessage.indexOf(delimiter);
		
		String command = inputMessage;
		String message = "";
		if (delim != -1) {
			command = inputMessage.substring(0, delim);
			message = inputMessage.substring(delim + 1);
		}
		
		AbstractCommand cmd = CommandContainer.commandList.get(command);
		
		if (cmd == null)
			return false;
		cmd.executeCommand(message, inputType, clientHandler);
		return true;
	}

	public static Server getServer() {
		return server;
	}

	public static void setServer(Server server) {
		CommandParser.server = server;
	}
}
