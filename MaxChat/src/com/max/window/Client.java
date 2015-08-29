package com.max.window;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.max.server.ChatServer;

@SuppressWarnings("serial")
public class Client extends JFrame {
	
	private String host;
	private int port;
	
	private String username;
	private Client frame;

	private Socket clientSocket;
	
	private JTextField inputTextField;

	
	/**
	 * Create the frame.
	 */
	public Client(String ip, String port, String username) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 503, 421);
		this.setTitle("MaxChat | username: " + username + " | IP: " + ip + " | Port: " + port);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmDisconnect = new JMenuItem("Disconnect");
		mntmDisconnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				frame.dispose();
				LoginWindow.getLoginWindow().setVisible(true);
				
			}
		});
		
		mnFile.add(mntmDisconnect);
		
		JMenuItem mntmChangeUsername = new JMenuItem("Change Username");
		mnFile.add(mntmChangeUsername);
		
		
		
		this.host = ip;
		this.port = Integer.parseInt(port);
		
		try {
			ChatServer.create(this.host, this.port);
		} catch (IOException e) { 
			System.out.println("Client: This host and port are running");
		}
		
		connectToServer();
		
		frame = this;
		getContentPane().setLayout(null);
		
		inputTextField = new JTextField();
		inputTextField.setBounds(10, 330, 368, 20);
		getContentPane().add(inputTextField);
		inputTextField.setColumns(10);
		
		JButton sendButton = new JButton("Send");
		sendButton.setBounds(388, 329, 89, 23);
		getContentPane().add(sendButton);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(10, 11, 467, 308);
		getContentPane().add(textArea);
	}


	private void connectToServer() {
		
		try {
			clientSocket = new Socket(InetAddress.getByName(this.host), this.port);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Client: Connected to "+ clientSocket.getInetAddress());	
	}
}
