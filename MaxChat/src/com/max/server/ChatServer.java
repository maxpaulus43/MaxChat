package com.max.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.BindException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import com.max.client.Client;

public class ChatServer {
	
	private ArrayList<Socket> clients;
	private ServerSocket ss;
	private static ChatServer instance;	
	private String host;
	private int port;
	
	private ChatServer(String host, int port) throws IOException{
		
		clients = new ArrayList<Socket>();
		
		this.host = host;
		this.port = port;
		
		ss = new ServerSocket();
		ss.bind(new InetSocketAddress(this.host, this.port));
		
		System.out.println("Server: Created on " + ss.getInetAddress());

		new Thread(new ConnectionThread(this)).start();
		
		instance = this;		
	}
	
	/**
	 * Creates a server on the specifid host and port
	 * @param host the ip address of the host
	 * @param port the port over which to communicate
	 * @return True if successful creation/discovery of a server
	 */
	public static boolean create(String host, int port){
		try {
			if (instance == null) {
				ChatServer.instance = new ChatServer(host, port);
			}
		} catch (UnknownHostException e) {
			System.out.println("Server: host doesn't exist.");
			return false;
		} catch (BindException e) {
			//TODO still have to fix wrong-hostname stuff
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return true;
	}
	
	public static ChatServer getInstance() {
		return ChatServer.instance;
	}
	
	public void sendToClients(String message) {
		for (Socket client : clients) {
			try {
				PrintWriter out;
				out = new PrintWriter(client.getOutputStream(), true);
				out.println(message);
			} catch (Exception e) {
				System.out.println("Server: problem sending to all clients");
			}
		}
		
	}
	
	public Socket accept() throws IOException {
		return ss.accept();
	}
	
	private void closeServer() throws IOException {
        ss.close();
        System.out.println("Server: Closing Server at " + ss.getInetAddress());
	}
	
	public static void addClient(Socket client) {
		instance.clients.add(client);
		
		new Thread(new ServerThread(instance, client)).start();
	}
        
    public static void removeClient(Socket c) {
        System.out.println("Server: Removing " + c.getInetAddress());
        instance.clients.remove(c);
        
        try {
			c.close();
			if (instance.clients.size() < 1) {
			    instance.closeServer();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

}
