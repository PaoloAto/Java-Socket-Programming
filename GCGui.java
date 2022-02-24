import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.*;
import java.io.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.html.*;

import java.util.ArrayList;
import java.util.Arrays;

public class GCGui {

	private JFrame frame;
	private JTextField txtChatName;
	private JTextField textField;
	
	  public static void main(String[] args) throws IOException {
		  GCGui gui = new GCGui();
	  }
	
	public GCGui() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(700, 550);
		
		
		JTextPane chatPane = new JTextPane();
		chatPane.setBounds(30, 50, 486, 365);
		final JScrollPane chatpaneSP = new JScrollPane();
		chatpaneSP.setBounds(25, 47, 490, 388);
		
		frame.getContentPane().add(chatpaneSP);
		
		JTextPane userPane = new JTextPane();
		userPane.setBounds(525, 47, 136, 325);
		final JScrollPane userpaneSP = new JScrollPane();
		userpaneSP.setBounds(525, 47, 133, 388);
		frame.getContentPane().add(userpaneSP);
		
		txtChatName = new JTextField();
		txtChatName.setText("Chat name: ");
		txtChatName.setBounds(25, 16, 490, 20);
		frame.getContentPane().add(txtChatName);
		txtChatName.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Users in chat:");
		lblNewLabel.setBounds(525, 16, 133, 20);
		frame.getContentPane().add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(23, 446, 492, 42);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Send");
		btnNewButton.setBounds(525, 446, 57, 42);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("File");
		btnNewButton_1.setBounds(601, 446, 57, 42);
		frame.getContentPane().add(btnNewButton_1);
		
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
	}
}
