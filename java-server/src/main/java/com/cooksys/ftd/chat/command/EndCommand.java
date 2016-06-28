package com.cooksys.ftd.chat.command;

import com.cooksys.ftd.chat.server.ClientHandler;
import com.cooksys.ftd.chat.server.CommandParser;

public class EndCommand extends AbstractCommand {
	
	public EndCommand() {
		super("/end",
			  "(no arguments)",
			  "Gently disconnect from the server");
	}
	
	@Override
	public void executeCommand(String message, String inputType, ClientHandler clientHandler) {
		CommandParser.getServer().close(clientHandler);
	}
}
