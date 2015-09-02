package com.max.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JOptionPane;

import com.max.client.Client;
import com.max.server.ChatServer;
import com.max.window.LoginWindow;

public class LoginListener implements ActionListener, KeyListener{

	private LoginWindow lw;
	
	public LoginListener(LoginWindow loginWindow) {
		this.lw = loginWindow;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		try {
			int portNum = Integer.parseInt(lw.getPort());
			if (portNum > 45151 && portNum < 60000) {
				if (!(lw.getHost().isEmpty() || lw.getPort().isEmpty() || lw.getUsername().isEmpty())) {
					try {
						if (ChatServer.create(lw.getHost(), portNum)) {
							LoginWindow.getFrame().setVisible(false);
							Client frame = new Client(lw.getHost(), lw.getPort(), lw.getUsername());
							frame.setVisible(true);
						}
						else {
							JOptionPane.showMessageDialog(null,
									"Could not create server! Try Again");
						}
						
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

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			actionPerformed(null);
		}
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
