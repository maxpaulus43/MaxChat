package com.max.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.IOException;
import java.io.PrintWriter;
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

import com.max.event.InputListener;
import com.max.server.ChatServer;
import com.max.window.LoginWindow;

@SuppressWarnings("serial")
public class Client extends JFrame {
	
	private String host;

    public String getHost() {
        return host;
    }
	private int port;
	
	private String username;
	private Client frame;

	private Socket clientSocket;
	private PrintWriter out;
	
	private JTextField inputTextField;
	public JTextField getInputTextField() {
		return inputTextField;
	}
	private JTextArea screen;

	
	/**
	 * Create the frame.
         * 
         * @param hostName the ip address/ host name for the chat server
         * @param port the port number for the program
         * @param username the username that the user takes on
	 */
	public Client(String hostName, String port, String username) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 503, 421);
		this.setTitle("MaxChat | username: " + username + " | IP: " + hostName + " | Port: " + port);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmDisconnect = new JMenuItem("Disconnect");
		mntmDisconnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				// TODO more disconnect stuff
				out.println(username + " disconnected from server...");
                System.out.println("Client: Disconnecting from " + host);
                try {
					clientSocket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				dispose();
				LoginWindow.getLoginWindow().setVisible(true);
				
			}
		});
		
		mnFile.add(mntmDisconnect);
		
		JMenuItem mntmChangeUsername = new JMenuItem("Change Username");
		mnFile.add(mntmChangeUsername);
		
		getContentPane().setLayout(null);
		
		inputTextField = new JTextField();
		inputTextField.addKeyListener(new InputListener(this));
		inputTextField.setText("Chat here...");
		inputTextField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				if (inputTextField.getText().equals("Chat here..."));
				{
					inputTextField.setText("");
				}
			}
		});
		inputTextField.setBounds(10, 330, 368, 20);
		getContentPane().add(inputTextField);
		inputTextField.setColumns(10);
		
		JButton sendButton = new JButton("Send");
		sendButton.addActionListener(new InputListener(this));
		sendButton.setBounds(388, 329, 89, 23);
		getContentPane().add(sendButton);
		
		this.screen = new JTextArea();
		screen.setLineWrap(true);
		screen.setEditable(false);
		screen.setBounds(10, 11, 467, 308);
		getContentPane().add(screen);
		
		this.username = username;
		this.host = hostName;
		this.port = Integer.parseInt(port);
	
		connectToServer();
	}

	private void connectToServer() {
		
		try {
			clientSocket = new Socket(InetAddress.getByName(this.host), this.port);
			out = new PrintWriter(clientSocket.getOutputStream(), true);

			//create a new thread to listen to any incoming messages		
			new Thread(new ClientThread(this, clientSocket)).start();
			System.out.println("Client: Connected to "+ clientSocket.getInetAddress());
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	public void send(String message) {
		out.println(username + ": " + message);
	}

	public void writeToScreen(String input) {
		screen.setText(screen.getText() + "\n" + input);
	}
}
