package com.cooksys.ftd.chat.command;

import com.cooksys.ftd.chat.server.ClientHandler;
import com.cooksys.ftd.chat.server.CommandParser;

public class UsersCommand extends AbstractCommand {
	
	public UsersCommand() {
		super("/users",
			  "(no arguments)",
			  "Displays a list of all users connected to the server");
	}
	
	@Override
	public void executeCommand(String message, String inputType, ClientHandler clientHandler) {
		CommandParser.getServer().listUsers(clientHandler);
	}
}
