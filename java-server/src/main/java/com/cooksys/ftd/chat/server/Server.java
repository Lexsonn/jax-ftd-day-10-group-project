package com.cooksys.ftd.chat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cooksys.ftd.chat.command.AbstractCommand;
import com.cooksys.ftd.chat.command.CommandContainer;

public class Server implements Runnable {

	Logger log = LoggerFactory.getLogger(Server.class);

	int port;
	Map<ClientHandler, Thread> handlerThreads;

	public Server(int port) {
		super();
		this.port = port;
		this.handlerThreads = new ConcurrentHashMap<>();
		CommandParser.setServer(this);
	}

	@Override
	public void run() {
		log.info("Server started on port {}", this.port);
		try (ServerSocket server = new ServerSocket(this.port)) {
			while (true) {
				Socket client = server.accept();
				log.info("Client connected {}", client.getRemoteSocketAddress());
				ClientHandler clientHandler = new ClientHandler(this, client);
				Thread clientHandlerThread = new Thread(clientHandler);
				this.handlerThreads.put(clientHandler, clientHandlerThread);
				clientHandlerThread.start();
			}
		} catch (IOException e) {
			log.error("Server fail! oh noes :(", e);
			run();
		} finally {
			for (ClientHandler clientHandler : this.handlerThreads.keySet()) {
				try {
					clientHandler.close();
					this.handlerThreads.get(clientHandler).join();
					this.handlerThreads.remove(clientHandler);
				} catch (IOException | InterruptedException e) {
					log.warn("Failed to close handler :/", e);
				}
			}
		}
	}
	
	public synchronized void broadcastMessage(String message, String name, boolean isStatus) {
		String timestamp = getCurrentTime();
		String messagePrefix = (isStatus) ? "*blue**" : "";
		String messageDelim = (isStatus) ? " " : ": ";
		String broadcast = messagePrefix + timestamp + " - " + name + messageDelim + message;
		for (ClientHandler clientHandler : this.handlerThreads.keySet()) { // Broadcast message to erryone
			clientHandler.writeMessage(broadcast);
		}
	}
	
	public static String getCurrentTime() {
		LocalDateTime current = LocalDateTime.now();
		String hour = Integer.toString(current.getHour());
		hour = (hour.length() > 1) ? hour : "0" + hour;
		String min = Integer.toString(current.getMinute());
		min = (min.length() > 1) ? min : "0" + min;
		String sec = Integer.toString(current.getSecond());
		sec = (sec.length() > 1) ? sec : "0" + sec;
		return hour + ":" + min + ":" + sec;
	}

	public void close(ClientHandler clientHandler) {
		if (handlerThreads.get(clientHandler) == null)
			return;
		log.info("Client {}@{} has ended the connection.", 
				clientHandler, clientHandler.getSocket().getRemoteSocketAddress().toString().substring(1));
		handlerThreads.remove(clientHandler);
		try {
			clientHandler.close();
		} catch (IOException e) {
			log.error("Client socket close has been interrupted", e);
		}
	}

	public void listUsers(ClientHandler clientHandler) {
		log.info("Client {}@{} has requested a list of users.", 
				clientHandler, clientHandler.getSocket().getRemoteSocketAddress().toString().substring(1));
		
		clientHandler.writeMessage("*green*users*USER LIST:");
		for (ClientHandler client : this.handlerThreads.keySet()) {
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			String name = client.getName();
			String ip = client.getSocket().getRemoteSocketAddress().toString();
			ip = '@' + ip.substring(1);
			clientHandler.writeMessage("*green*users*" + name + ip);
		}
	}
	
	public void listCommands(ClientHandler clientHandler) {
		log.info("Client {}@{} has requested help.", 
				clientHandler, clientHandler.getSocket().getRemoteSocketAddress().toString().substring(1));
		
		clientHandler.writeMessage("*green*help*HELP COMMANDS:");
		for (AbstractCommand cmd : CommandContainer.commandList.values()) {
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			clientHandler.writeMessage("*green*help*" + cmd.getName() + "\t" + cmd.getArguments() + ":\t" + cmd.getDescription());
		}
	}
}
