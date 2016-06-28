package com.cooksys.ftd.chat.command;

import com.cooksys.ftd.chat.server.ClientHandler;

public class SendCommand extends AbstractCommand {
	
	public SendCommand() {
		super ("/send",
			   "(object to send, formatted as {name: field, ...})",
			   "Broadcast an object to other clients");
	}
	
	@Override
	public void executeCommand(String message, String inputType, ClientHandler clientHandler) {
		
	}
}
