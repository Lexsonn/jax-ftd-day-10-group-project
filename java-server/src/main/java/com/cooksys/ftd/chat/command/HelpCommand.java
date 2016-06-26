package com.cooksys.ftd.chat.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cooksys.ftd.chat.server.ClientHandler;

public class HelpCommand extends AbstractCommand {
	
	Logger log = LoggerFactory.getLogger(HelpCommand.class);

	public HelpCommand() {
		super("/help", 
			  "(no arguments)", 
			  "Displays a list of all available commands and their descriptions");
	}
	
	@Override
	public void executeCommand(String message, ClientHandler clientHandler) {
		log.info("Client {}@{} has requested help.", clientHandler.getName(), 
				 clientHandler.getSocket().getRemoteSocketAddress().toString().substring(1));
		
		clientHandler.writeMessage("*bgMagenta*help*HELP COMMANDS:");
		for (AbstractCommand cmd : CommandContainer.commandList.values()) {
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				log.error("Error with sleeping Thread", e);
			}
			clientHandler.writeMessage("*magenta**" + cmd.getName() + "\t" + cmd.getArguments() + ":\t" + cmd.getDescription());
		}
	}
	
}
