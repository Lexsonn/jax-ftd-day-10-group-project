package com.cooksys.ftd.chat.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cooksys.ftd.chat.server.ClientHandler;
import com.cooksys.ftd.chat.server.CommandParser;

public class EndCommand extends AbstractCommand {
	
	Logger log = LoggerFactory.getLogger(EndCommand.class);
	
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
