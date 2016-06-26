package com.cooksys.ftd.chat.command;

import com.cooksys.ftd.chat.server.ClientHandler;
import com.cooksys.ftd.chat.server.CommandParser;

public class MeCommand extends AbstractCommand {
	
	public MeCommand() {
		super ("/me",
			   "(message)",
			   "Displays a status message (Formatted as {timestamp - name} {message})");
	}
	
	@Override
	public void executeCommand(String message, String inputType, ClientHandler clientHandler) {
		CommandParser.getServer().broadcastMessage(message, clientHandler.getName(), true);
	}
}
