package com.cooksys.ftd.chat.command;

import com.cooksys.ftd.chat.server.ClientHandler;
import com.cooksys.ftd.chat.server.CommandParser;

public class HelpCommand extends AbstractCommand {

	public HelpCommand() {
		super("/help", 
			  "(no arguments)", 
			  "Displays a list of all available commands and their descriptions");
	}
	
	@Override
	public void executeCommand(String message, ClientHandler clientHandler) {
		CommandParser.getServer().listCommands(clientHandler);
	}
	
}
