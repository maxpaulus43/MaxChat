package com.max.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

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

		new Thread(new ConnectionListener(this)).start();
		
		instance = this;
		
	}
	
	public static void create(String host, int port){
		try {
			if (instance == null) {
				ChatServer.instance = new ChatServer(host, port);
			}
		} catch (UnknownHostException e) {
			System.out.println("Host doesn't exist. Exit and retry.");
		}
		catch (IOException e) {
			System.out.println("Server: found server");
		}
	}
	
	public ChatServer getInstance() {
		return ChatServer.instance;
	}
	
	public void sendToClients(String message) {
		for (Socket client : clients) {
			try {
				PrintWriter out;
				out = new PrintWriter(client.getOutputStream(), true);
				out.println(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public Socket accept() throws IOException {
		return ss.accept();
	}
	
	public void closeServer() throws IOException {
		if (clients.size() < 0) {
			ss.close();
		}
		else {
			System.out.println("Server: not closing; still have clients.");
		}
	}
	
	public void addClient(Socket client) {
		clients.add(client);
		
		new Thread(new ServerListener(this, client)).start();
	}
}
