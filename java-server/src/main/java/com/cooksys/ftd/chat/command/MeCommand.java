package com.cooksys.ftd.chat.command;

import com.cooksys.ftd.chat.server.ClientHandler;
import com.cooksys.ftd.chat.server.CommandParser;

public class MeCommand extends AbstractCommand {
	
	public MeCommand() {
		super ("/me",
			   "(no arguments)",
			   "Displays a status message (Formatted as {timestamp - name} {message}");
	}
	
	@Override
	public void executeCommand(String message, ClientHandler clientHandler) {
		CommandParser.getServer().addLine(message, clientHandler.getName(), true);
	}
}
