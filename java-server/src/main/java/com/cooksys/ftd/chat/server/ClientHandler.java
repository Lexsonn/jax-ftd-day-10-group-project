package com.cooksys.ftd.chat.server;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cooksys.ftd.chat.command.CommandContainer;

public class ClientHandler implements Runnable, Closeable {

	Logger log = LoggerFactory.getLogger(ClientHandler.class);

	private Server server;
	private String name;
	private Socket client;
	private PrintWriter writer;
	private BufferedReader reader;

	public ClientHandler(Server server, Socket client) throws IOException {
		super();
		this.server = server;
		this.client = client;
		this.reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
		this.writer = new PrintWriter(client.getOutputStream(), true);
		
		writeMessage("*bgBlue*authenticate*Please enter in a username");

		String input, type, message;
		String[] inputs;
		while(this.name == null) {
			input = reader.readLine();
			inputs = input.split("\\*", 3);
			type = inputs[1];
			message = inputs[2];	
			if(type.equals("client")){
				this.setName(message); 				
			} else {
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					log.error("Error with sleeping Thread", e);
				}
				CommandParser.parseCommand(input, this);
				log.info("User {}@{} Issued command {}", this.name, 
						this.client.getRemoteSocketAddress().toString().substring(1), input);
			}
		}
		log.info("{}: Name set to {}", this.client.getRemoteSocketAddress(), name);
		writeMessage("*bgBlue*username*Username set to: " + this.name);
		this.server.broadcastMessage("has logged in.", this.name, true);
	}

	@Override
	public void run() {
		try {
			log.info("handling client {}@{}", this.name, this.client.getRemoteSocketAddress().toString().substring(1));
			while (!this.client.isClosed()) {
				String echo = reader.readLine();
				String[] inputs = echo.split("\\*", 3);
				String message = inputs[2];	

				if (CommandParser.parseCommand(echo, this)) {
					log.info("User {}@{} Issued command {}", this.name, 
							this.client.getRemoteSocketAddress().toString().substring(1), echo);
				}
				else {
					log.info("received message [{}] from {}@{}, echoing...", echo,
							this.name, this.client.getRemoteSocketAddress().toString().substring(1));
					this.server.broadcastMessage(message, this.name, false);
				}
			}
			log.info("{}@{}: has disconnected.", this.name, this.client.getRemoteSocketAddress().toString().substring(1));
			this.server.close(this);
		} catch (IOException e) {
			log.warn("Client has been forcibly disconnected");
			this.server.close(this);
		}
	}

	@Override
	public void close() throws IOException {
		log.info("closing connection to client {}", this.client.getRemoteSocketAddress());
		this.server.broadcastMessage("has disconnected.", this.name, true);
		writeMessage("*bgBlue*disconnect*disconnected from server.");
		this.client.close();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Socket getSocket() {
		return this.client;
	}
	
	public void writeMessage(String message) {
		writer.print(message);
		writer.flush();
	}

}
