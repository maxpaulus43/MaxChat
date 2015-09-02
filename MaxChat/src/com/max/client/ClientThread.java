package com.max.client;

import java.io.IOException;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ClientThread implements Runnable {

	private Client client;
	private Socket s;

	public ClientThread(Client client, Socket s) {
		this.client = client;
		this.s = s;
	}


	@Override
	public void run() {
		try {
			Scanner in = new Scanner(s.getInputStream());
			
			String input;
			while(((input = in.nextLine()) != null)) {
				client.writeToScreen(input);
			}
			
		} catch (IOException e) {
			System.out.println("Client Thread error.");
		} catch (NoSuchElementException e) {
			System.out.println("ClientThread: client closed");
			try {
				s.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
}
