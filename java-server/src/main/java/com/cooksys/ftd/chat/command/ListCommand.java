package com.cooksys.ftd.chat.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cooksys.ftd.chat.server.ClientHandler;

public class ListCommand extends AbstractCommand {
	Logger log = LoggerFactory.getLogger(HelpCommand.class);

	public ListCommand() {
		super ("/list",
			   "(no arguments)",
			   "Displays a list of commands");
	}
	
	@Override
	public void executeCommand(String message, String inputType, ClientHandler clientHandler) {
		boolean silently = inputType.equals("service");
		log.info("Client {}@{} has requested list.", clientHandler.getName(), 
				 clientHandler.getSocket().getRemoteSocketAddress().toString().substring(1));

		String clientMessage = CommandContainer.listCommands();
		String header = (silently? "*plain*serviceCommands*":"*magenta*listCommands*");

		clientHandler.writeMessage(header + clientMessage);
	}
}
