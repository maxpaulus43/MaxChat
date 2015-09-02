/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.max.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.max.client.Client;


/**
 *
 * @author Max
 */
public class InputListener implements ActionListener, KeyListener{

	public Client c;
	
	public InputListener(Client c) {
		this.c = c;
	}
	
	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER){
			actionPerformed(null);
		}	
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (!(c.getInputTextField().getText().equals("Chat here...")) &&
				(c.getInputTextField().getText().length() > 0)) {
			c.send(c.getInputTextField().getText());
			c.getInputTextField().setText("");
		}
	}
    
}
