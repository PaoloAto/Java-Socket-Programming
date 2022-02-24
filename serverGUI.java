import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.Panel;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

public class serverGUI extends JFrame{
	private JTextField textField;
	private int port;
	private Server server;
	
//	public static void main(String[] args) {
//		serverGUI server = new serverGUI ();
//	}
	
	public serverGUI() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(0, 50, 700, 550);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e) {
				 if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					Server server = new Server(port = getPort());
					JOptionPane.showMessageDialog(null, "Server Connected on port " + port);
					try {
						server.run();
					} catch (IOException f) {
						f.printStackTrace();
					}
				}
			}
		});

		JLabel lblEnterPortTo = new JLabel("Enter Port to Open:");
		lblEnterPortTo.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		JButton btnOpen = new JButton("OPEN");
		btnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {					
					Server server = new Server(port = getPort());
					JOptionPane.showMessageDialog(null, "Server Connected on port " + port);
					try {
						server.run();
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
		});
		btnOpen.setFont(new Font("Dialog", Font.PLAIN, 15));
		btnOpen.setBackground(Color.LIGHT_GRAY);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(212)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, 146, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblEnterPortTo, GroupLayout.PREFERRED_SIZE, 281, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnOpen, GroupLayout.PREFERRED_SIZE, 146, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(90, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(125)
					.addComponent(lblEnterPortTo, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
					.addGap(6)
					.addComponent(textField, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnOpen, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE))
		);
		getContentPane().setLayout(groupLayout);
		this.setVisible(true);
	}
	
	public int getPort () {
		return Integer.parseInt(textField.getText());
	}
}
