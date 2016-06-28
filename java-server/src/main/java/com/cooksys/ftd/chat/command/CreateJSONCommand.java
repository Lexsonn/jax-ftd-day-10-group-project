package com.cooksys.ftd.chat.command;

import com.cooksys.ftd.chat.server.ClientHandler;

public class CreateJSONCommand extends AbstractCommand {
	
	public CreateJSONCommand() {
		super ("/createjson",
			   "(object to send, formatted as {name: field, ...})",
			   "Broadcast an object to other clients");
	}
	
	@Override
	public void executeCommand(String message, String inputType, ClientHandler clientHandler) {
		
	}
}
