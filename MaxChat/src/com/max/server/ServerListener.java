package com.max.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ServerListener implements Runnable{

	ChatServer server;
	Socket client;
	
	public ServerListener(ChatServer server, Socket client) {
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
				switch(input) {
					// TODO console commands go here
				}
	
				server.sendToClients(input);		
			}
		} catch (IOException e) {System.out.println("ServerListener error.");}
		
	}

}
