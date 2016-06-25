package com.cooksys.ftd.chat.command;

import com.cooksys.ftd.chat.server.ClientHandler;

public class AbstractCommand {
	protected String name;
	protected String arguments;
	protected String description;
	
	public AbstractCommand(String name, String arguments, String description) {
		this.name = name;
		this.arguments = arguments;
		this.description = description;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getArguments() {
		return this.arguments;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public void executeCommand(String message, ClientHandler clientHandler) {
		
	}
}
