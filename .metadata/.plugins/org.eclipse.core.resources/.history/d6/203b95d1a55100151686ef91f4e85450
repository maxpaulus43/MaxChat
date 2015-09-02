package com.max.server;

import java.io.IOException;
import java.net.Socket;

public class ConnectionThread implements Runnable {

	ChatServer server;
	String clientIP;
	
	private static boolean listening = true;
	
	public ConnectionThread(ChatServer server) {
		this.server = server;	
	}
	
	@Override
	public void run() {
			try {
				while(listening) {
					Socket client = server.accept();
					System.out.println("ConnectionThread: Connection established with " + client.getInetAddress());
					ChatServer.addClient(client);			
				}
			} catch (IOException e) { //socket is closed 
				System.out.println("ConnectionThread Error");
			}
	}
	
	public static void stopListening() {
		listening = false;
		System.out.println("ConnectionThread: stopped listening");
	}

}
