package com.cooksys.ftd.chat.command;

import java.io.IOException;

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
	public void executeCommand(String message, ClientHandler clientHandler) {
		try {
			CommandParser.getServer().close(clientHandler);
		} catch (InterruptedException e) {
			log.warn("Client has been forcibly disconnected");
		} catch (IOException e) {
			log.error("Client /end command has been interrupted", e);
		}
	}
}
