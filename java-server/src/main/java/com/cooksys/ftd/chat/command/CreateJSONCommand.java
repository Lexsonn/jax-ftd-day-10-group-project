package com.cooksys.ftd.chat.command;

import java.io.FileWriter;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cooksys.ftd.chat.server.ClientHandler;

public class CreateJSONCommand extends AbstractCommand {
	
	Logger log = LoggerFactory.getLogger(CreateJSONCommand.class);
	
	public CreateJSONCommand() {
		super ("/createjson",
			   "(file name)",
			   "Prepare server for creating a json file");
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
		if (message.length() > 4) {
			if (!message.substring(message.length() - 4).equals(".json"))
				message += ".json";
		}
		else message += ".json";
		
		FileWriter file;
		clientHandler.writeMessage("*bgBlue*cjo_ready*Please enter in a JSON object:");
		try {
			String input = clientHandler.getMessage().substring(8);
			log.info("Input: " + input);
			file = new FileWriter("C:/code/TextFiles/" + message);
			file.write(input);
			msgColor = "*green*";
			clientHandler.writeMessage(msgColor + "cjo_sucess*Server sucessfully created JSON file.");
			file.close();
		} catch (IOException e) {
			log.error("Error in handling client input for JSON", e);
			clientHandler.writeMessage(msgColor + "EMISCERR*Server error on creating JSON file.");
		}
		
	}
}
