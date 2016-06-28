package com.cooksys.ftd.chat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cooksys.ftd.chat.command.CommandContainer;
import com.cooksys.ftd.chat.command.CreateJSONCommand;
import com.cooksys.ftd.chat.command.DeleteJSONCommand;
import com.cooksys.ftd.chat.command.EndCommand;
import com.cooksys.ftd.chat.command.HelpCommand;
import com.cooksys.ftd.chat.command.ListCommand;
import com.cooksys.ftd.chat.command.MeCommand;
import com.cooksys.ftd.chat.command.ReadJSONCommand;
import com.cooksys.ftd.chat.command.UpdateJSONCommand;
import com.cooksys.ftd.chat.command.UsersCommand;
import com.cooksys.ftd.chat.server.Server;

public class Main {
	
	static Logger log = LoggerFactory.getLogger(Main.class);
	
	public static void main(String[] args) {
		CommandContainer.register(HelpCommand.class);
		CommandContainer.register(MeCommand.class);
		CommandContainer.register(EndCommand.class);
		CommandContainer.register(UsersCommand.class);
		CommandContainer.register(ListCommand.class);
		/*
		CommandContainer.register(CreateJSONCommand.class);
		CommandContainer.register(ReadJSONCommand.class);
		CommandContainer.register(UpdateJSONCommand.class);
		CommandContainer.register(DeleteJSONCommand.class);
		*/
		CommandContainer.ready();
		
		Server server = new Server(667);
		Thread serverThread = new Thread(server);
		serverThread.start();
		
		try {
			serverThread.join();
			System.exit(0);
		} catch (InterruptedException e) {
			log.error("Server thread interrupted :(", e);
			System.exit(-1);
		}
	}

}
