package com.max.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ServerThread implements Runnable{

	ChatServer server;
	Socket client;
	
	public ServerThread(ChatServer server, Socket client) {
		this.server = server;
		this.client = client;
	}
	
	@Override
	public void run() {
		try {
			Scanner in = new Scanner(client.getInputStream());
			PrintWriter out = new PrintWriter(client.getOutputStream(), true);
			
			out.println("Welcome to max chat!");
			
			String input;
			while((input = in.nextLine()) != null) {				
				server.sendToClients(input);		
			}
		} catch (IOException e) {
			System.out.println("ServerListener error");
		} catch (NoSuchElementException e) {
			System.out.println("Server: " + client.getInetAddress() + 
					" disconnected");
            ChatServer.removeClient(this.client);
		}
		
	}

}
