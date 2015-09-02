package com.max.server;

import java.io.IOException;
import java.io.PrintWriter;
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
            System.out.println("Closing Server at " + ss.getInetAddress());
	}
	
	public static void addClient(Socket client) {
		instance.clients.add(client);
		
		new Thread(new ServerThread(instance, client)).start();
	}
        
        public static void removeClient(Socket c) {
            System.out.println("Server: Removing " + c.getInetAddress());
            instance.clients.remove(c);

            if (instance.clients.size() < 1) {
                System.out.println("No clients left: closing server.");
                try {
                    instance.closeServer();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

}
