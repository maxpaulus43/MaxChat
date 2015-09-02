package com.max.server;

import java.io.IOException;
import java.net.Socket;

public class ConnectionThread implements Runnable {

	ChatServer server;
	String clientIP;
	
	private boolean listening = true;
	
	public ConnectionThread(ChatServer server) {
		this.server = server;	
	}
	
	@Override
	public void run() {
			try {
				while(listening) {
					Socket client = server.accept();
					System.out.println("ConnectionThread: Connection established with "
							+ client.getInetAddress());
					ChatServer.addClient(client);			
				}
			} catch (IOException e) {/* socket is closed */}
	}
}
