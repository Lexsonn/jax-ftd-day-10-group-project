package com.cooksys.ftd.chat.command;

import com.cooksys.ftd.chat.server.ClientHandler;

public class HelpCommand extends AbstractCommand {

	public HelpCommand() {
		super("/help", 
			  "(no arguments)", 
			  "Displays a list of all available commands and their descriptions");
	}
	
	@Override
	public void executeCommand(String message, ClientHandler clientHandler) {
		for (AbstractCommand cmd : CommandContainer.commandList.values()) {
			String help = "*red**" + cmd.getName() + " " + cmd.getArguments() + ": " + cmd.getDescription();
			clientHandler.writeMessage(help);
		}
	}
	
}
