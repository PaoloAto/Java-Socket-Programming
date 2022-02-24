import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.JButton;

public class ChatroomView {

	private JFrame frame;
	private JTextField txtChatRooms;

	public ChatroomView() {
		frame = new JFrame();
		frame.setBounds(100, 100, 685, 456);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JTextPane textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setBounds(31, 37, 606, 313);
		frame.getContentPane().add(textPane);
		
		txtChatRooms = new JTextField();
		txtChatRooms.setEditable(false);
		txtChatRooms.setText("Chat Rooms:");
		txtChatRooms.setBounds(31, 6, 606, 20);
		
		frame.getContentPane().add(txtChatRooms);
		txtChatRooms.setColumns(10);
		
		JButton btnNewButton = new JButton("Join");
		btnNewButton.setBounds(509, 361, 128, 45);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Create");
		btnNewButton_1.setBounds(31, 361, 128, 45);
		frame.getContentPane().add(btnNewButton_1);
		
		
	}
}
