package com.cooksys.ftd.chat.command;

import java.io.FileReader;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cooksys.ftd.chat.server.ClientHandler;

public class ReadJSONCommand extends AbstractCommand {

	Logger log = LoggerFactory.getLogger(CreateJSONCommand.class);
	
	public ReadJSONCommand() {
		super ("/readjson",
			   "(file name)",
			   "read json object on server");
	}
	
	@Override
	public void executeCommand(String message, String inputType, ClientHandler clientHandler) {
		boolean service = inputType.equals("service");
		String msgColor = "*red*";
		if (service)
			msgColor = "*plain*";
		
		if (message.length() < 1) {
			clientHandler.writeMessage(msgColor + "EMISC*Invalid JSON file name entered.");
			return;
		}
		
		try {
			FileReader file = new FileReader("C:/code/TextFiles/" + message);
			String output = "";
			int i;
			while ((i = file.read()) > 0) {
				output += i;
			}
			log.info("OUTPUT: " + output);
			clientHandler.writeMessage(output);
			file.close();
		} catch (IOException e) {
			log.warn("File {} not found.", message);
			clientHandler.writeMessage(msgColor + "EMISCERR*Error reading from file");
		}
		
	}
}
