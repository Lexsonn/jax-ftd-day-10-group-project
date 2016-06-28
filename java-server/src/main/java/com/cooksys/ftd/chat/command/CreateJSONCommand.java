package com.cooksys.ftd.chat.command;

import java.io.ByteArrayInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.eclipse.persistence.jaxb.dynamic.DynamicJAXBContext;
import org.eclipse.persistence.jaxb.dynamic.DynamicJAXBContextFactory;
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
		if (!message.substring(message.length() - 4).equals(".json"))
			message += ".json";
		
		FileWriter file;
		clientHandler.writeMessage("*bgBlue*cjo_ready*Please enter in a JSON object:");
		try {
			String input = clientHandler.getMessage();
			// InputStream input = clientHandler.getSocket().getInputStream();
			InputStream stream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
			DynamicJAXBContext jaxbContext = DynamicJAXBContextFactory.createContextFromXSD(stream, null, null, null);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			
			file = new FileWriter("C:/code/TextFiles/" + message);
			file.write(unmarshaller.toString());
			msgColor = "*green*";
			clientHandler.writeMessage(msgColor + "cjo_sucess*Server error on creating JSON file.");
			file.close();
		} catch (IOException | JAXBException e) {
			log.error("Error in handling client input for JSON", e);
			clientHandler.writeMessage(msgColor + "EMISCERR*Server error on creating JSON file.");
		}
		
	}
}
