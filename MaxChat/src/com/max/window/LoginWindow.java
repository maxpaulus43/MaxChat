package com.max.window;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.max.client.Client;

@SuppressWarnings("serial")
public class LoginWindow extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldHost;
	private JTextField textFieldPort;
	private static LoginWindow frame;
	private JTextField textFieldUsername;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = LoginWindow.getLoginWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Returns the singleton instance of the login window.
	 */
	public static LoginWindow getLoginWindow() {
		if (frame != null) {
			return frame;
		} else
			return new LoginWindow();
	}

	/**
	 * Create the frame.
	 */
	private LoginWindow() {
		setResizable(false);
		setTitle("Max Chat");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 277, 302);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblHostName = new JLabel("Host Name: ");
		lblHostName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblHostName.setBounds(89, 11, 86, 14);
		contentPane.add(lblHostName);

		JLabel lblPort = new JLabel("Port:");
		lblPort.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPort.setBounds(113, 67, 46, 14);
		contentPane.add(lblPort);

		JButton btnConnect = new JButton("Connect");
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try {
					int portNum = Integer.parseInt(textFieldPort.getText());
					if (portNum > 45151 && portNum < 60000) {
						if (!(textFieldHost.getText().isEmpty()
								|| textFieldPort.getText().isEmpty() 
								|| textFieldUsername.getText().isEmpty())) {
							try {
								
								LoginWindow.frame.setVisible(false);
								Client frame = new Client(textFieldHost.getText(), textFieldPort.getText(), textFieldUsername.getText());
								frame.setVisible(true);
								
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					} else JOptionPane.showMessageDialog(null,
							"Port must be with range 45151-60000");
					
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null,
							"Port field must be a number!");
				}

				

			}
		});
		btnConnect.setBounds(89, 217, 89, 23);
		contentPane.add(btnConnect);

		textFieldHost = new JTextField();
		textFieldHost.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textFieldHost.setBounds(56, 36, 158, 20);
		contentPane.add(textFieldHost);
		textFieldHost.setColumns(10);

		textFieldPort = new JTextField();
		textFieldPort.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textFieldPort.setColumns(10);
		textFieldPort.setBounds(56, 92, 158, 20);
		contentPane.add(textFieldPort);

		textFieldUsername = new JTextField();
		textFieldUsername.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textFieldUsername.setColumns(10);
		textFieldUsername.setBounds(56, 148, 158, 20);
		contentPane.add(textFieldUsername);

		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblUsername.setBounds(89, 123, 86, 14);
		contentPane.add(lblUsername);
	}
}
