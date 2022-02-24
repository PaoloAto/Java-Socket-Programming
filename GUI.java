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

public class GUI extends Thread{

  final JTextPane jtextFilDiscu = new JTextPane();
  final JTextPane jtextListUsers = new JTextPane();
  final JTextField jtextInputChat = new JTextField();
  private String space = "";
  private Thread read;
  private String serverName;
  private int PORT;
  private String name;
  BufferedReader input;
  PrintWriter output;
  Socket server;
  private JTextField userLabel;

  public static void main(String[] args) throws Exception {
	    GUI client = new GUI();
	  }
  
  public GUI() {
    this.serverName = serverName;
    this.PORT = PORT; //use port opened in the server
    this.name = name;

    String fontfamily = "Times New Roman, Arial Narrow";
    Font font = new Font(fontfamily, Font.PLAIN, 15);

    final JFrame jfr = new JFrame("Messaging");
    jfr.getContentPane().setBackground(Color.WHITE);
    jfr.getContentPane().setLayout(null);
    jfr.setSize(700, 550);
    jfr.setResizable(false);
    jfr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    jtextFilDiscu.setBounds(25, 25, 490, 320);
    jtextFilDiscu.setFont(font);
    jtextFilDiscu.setMargin(new Insets(6, 6, 6, 6));
    jtextFilDiscu.setEditable(false);
    JScrollPane jtextFilDiscuSP = new JScrollPane(jtextFilDiscu);
    jtextFilDiscuSP.setBounds(25, 25, 490, 320);

    jtextFilDiscu.setContentType("text/html");
    jtextFilDiscu.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, true);


    jtextListUsers.setBounds(520, 25, 156, 320);
    jtextListUsers.setEditable(true);
    jtextListUsers.setFont(font);
    jtextListUsers.setMargin(new Insets(6, 6, 6, 6));
    jtextListUsers.setEditable(false);
    JScrollPane jsplistuser = new JScrollPane(jtextListUsers);
    jsplistuser.setBounds(520, 25, 156, 320);

    jtextListUsers.setContentType("text/html");
    jtextListUsers.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, true);

    //user input
    jtextInputChat.setBounds(0, 350, 400, 80);
    jtextInputChat.setFont(font);
    jtextInputChat.setMargin(new Insets(6, 6, 6, 6));
    final JScrollPane jtextInputChatSP = new JScrollPane(jtextInputChat);
    jtextInputChatSP.setBounds(25, 350, 650, 70);

    // send button
    final JButton jsbtn = new JButton("Enter");
    jsbtn.setFont(font);
    jsbtn.setBounds(575, 420, 90, 90);

    // disconnect
    final JButton jsbtndeco = new JButton("Logout");
    jsbtndeco.setFont(font);
    jsbtndeco.setBounds(25, 420, 90, 90);

    jtextInputChat.addKeyListener(new KeyAdapter() {
      // send message on send
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
          sendMessage();
        }

        // Get last message that was entered
        if (e.getKeyCode() == KeyEvent.VK_UP) {
          String currentMessage = jtextInputChat.getText().trim();
          jtextInputChat.setText(space);
          space = currentMessage;
        }

        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
          String currentMessage = jtextInputChat.getText().trim();
          jtextInputChat.setText(space);
          space = currentMessage;
        }
      }
    });

    // Click on send button
    jsbtn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        sendMessage();
      }
    });

    // Connection view
    final JTextField jtfName = new JTextField("");
    final JTextField jtfport = new JTextField("");
    final JTextField jtfAddr = new JTextField(this.serverName);
    final JButton jcbtn = new JButton("Log-In");
    jcbtn.setBackground(Color.LIGHT_GRAY);

    jtfName.getDocument().addDocumentListener(new TextListener(jtfName, jtfport, jtfAddr, jcbtn));
    jtfport.getDocument().addDocumentListener(new TextListener(jtfName, jtfport, jtfAddr, jcbtn));
    jtfAddr.getDocument().addDocumentListener(new TextListener(jtfName, jtfport, jtfAddr, jcbtn));

    jcbtn.setFont(font);
    jtfAddr.setBounds(300, 392, 146, 40);
    jtfName.setBounds(79, 392, 135, 40);
    jtfport.setBounds(520, 392, 135, 40);
    jcbtn.setBounds(278, 438, 146, 64);

    jtextFilDiscu.setBackground(Color.LIGHT_GRAY);
    jtextListUsers.setBackground(Color.LIGHT_GRAY);

    jfr.getContentPane().add(jcbtn);
    jfr.getContentPane().add(jtextFilDiscuSP);
    jfr.getContentPane().add(jsplistuser);
    jfr.getContentPane().add(jtfName);
    jfr.getContentPane().add(jtfport);
    jfr.getContentPane().add(jtfAddr);
    
    JLabel lblUsername = new JLabel("Username: ");
    lblUsername.setBounds(12, 405, 135, 14);
    jfr.getContentPane().add(lblUsername);
    
    JLabel label_1 = new JLabel("IP Address:");
    label_1.setBounds(226, 405, 147, 14);
    jfr.getContentPane().add(label_1);
    
    JLabel lblPort = new JLabel("Port #:");
    lblPort.setBounds(471, 405, 46, 14);
    jfr.getContentPane().add(lblPort);
    
    JLabel lblChat = new JLabel("Chat");
    lblChat.setFont(new Font("Tahoma", Font.BOLD, 15));
    lblChat.setBounds(39, -2, 39, 27);
    jfr.getContentPane().add(lblChat);
    
    JLabel lblUsers = new JLabel("Users");
    lblUsers.setFont(new Font("Tahoma", Font.BOLD, 15));
    lblUsers.setBounds(520, -2, 84, 27);
    jfr.getContentPane().add(lblUsers);
    
    userLabel = new JTextField();
    userLabel.setEditable(false);
    userLabel.setBounds(79, 3, 439, 20);
    jfr.getContentPane().add(userLabel);
    userLabel.setColumns(10);
    jfr.setVisible(true);
    
    

    // On connect
    jcbtn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        try {
          name = jtfName.getText();
    	  userLabel.setText("Welcome to the chat, "+ name + "!");
          String port = jtfport.getText();
          serverName = jtfAddr.getText();
          PORT = Integer.parseInt(port);

          appendToPane(jtextFilDiscu, "<span>Connecting to " + serverName + " on port " + PORT + "...</span>");
          server = new Socket(serverName, PORT);

          appendToPane(jtextFilDiscu, "<span>Connected to " +
              server.getRemoteSocketAddress()+"</span>");

          input = new BufferedReader(new InputStreamReader(server.getInputStream()));
          output = new PrintWriter(server.getOutputStream(), true);

          output.println(name);

          read = new Read();
          read.start();
          jfr.remove(jtfName);
          jfr.remove(jtfport);
          jfr.remove(jtfAddr);
          jfr.remove(jcbtn);
          jfr.remove(lblUsername);
          jfr.remove(label_1);
          jfr.remove(lblPort);
          jfr.getContentPane().add(jsbtn);
          jfr.getContentPane().add(jtextInputChatSP);
          jfr.getContentPane().add(jsbtndeco);
          jfr.revalidate();
          jfr.repaint();
          jtextFilDiscu.setBackground(Color.WHITE);
          jtextListUsers.setBackground(Color.WHITE);
        } catch (Exception ex) {
          appendToPane(jtextFilDiscu, "Could not connect to Server");
          JOptionPane.showMessageDialog(jfr, ex.getMessage());
        }
      }
    });

    jsbtndeco.addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent ae) {
        jfr.getContentPane().add(jtfName);
        jfr.getContentPane().add(jtfport);
        jfr.getContentPane().add(jtfAddr);
        jfr.getContentPane().add(jcbtn);
        jfr.getContentPane().add(lblUsername);
        jfr.getContentPane().add(label_1);
        jfr.getContentPane().add(lblPort);
        jfr.remove(jsbtn);
        jfr.remove(jtextInputChatSP);
        jfr.remove(jsbtndeco);
        jfr.revalidate();
        jfr.repaint();
        read.interrupt();
        jtextListUsers.setText(null);
        jtextFilDiscu.setBackground(Color.LIGHT_GRAY);
        jtextListUsers.setBackground(Color.LIGHT_GRAY);
        appendToPane(jtextFilDiscu, "Connection terminated.");
        output.close();
      }
    });
  }

  // check if if all fields are not empty
  public class TextListener implements DocumentListener{
    JTextField jtf1;
    JTextField jtf2;
    JTextField jtf3;
    JButton jcbtn;

    public TextListener(JTextField jtf1, JTextField jtf2, JTextField jtf3, JButton jcbtn){
      this.jtf1 = jtf1;
      this.jtf2 = jtf2;
      this.jtf3 = jtf3;
      this.jcbtn = jcbtn;
    }

    public void changedUpdate(DocumentEvent e) {}

    public void removeUpdate(DocumentEvent e) {
      if(jtf1.getText().trim().equals("") ||
          jtf2.getText().trim().equals("") ||
          jtf3.getText().trim().equals("")
          ){
        jcbtn.setEnabled(false);
      }else{
        jcbtn.setEnabled(true);
      }
    }
    
    public void insertUpdate(DocumentEvent e) {
      if(jtf1.getText().trim().equals("") ||
          jtf2.getText().trim().equals("") ||
          jtf3.getText().trim().equals("")
          ){
        jcbtn.setEnabled(false);
      }else{
        jcbtn.setEnabled(true);
      }
    }

  }

  public void sendMessage() {
    try {
      String message = jtextInputChat.getText().trim();
      if (message.equals("")) { //no message or spaces
        return;
      }
      this.space = message;
      output.println(message);
      jtextInputChat.requestFocus();
      jtextInputChat.setText(null);
    } catch (Exception ex) {
      JOptionPane.showMessageDialog(null, ex.getMessage());
      System.exit(0);
    }
  }

  class Read extends Thread {
    public void run() {
      String message;
      while(!Thread.currentThread().isInterrupted()){
        try {
          message = input.readLine();
          if(message != null){
            if (message.charAt(0) == '[') {
              message = message.substring(1, message.length()-1);
              ArrayList<String> ListUser = new ArrayList<String>(
                  Arrays.asList(message.split(", "))
                  );
              jtextListUsers.setText(null);
              for (String user : ListUser) {
                appendToPane(jtextListUsers, " - " + user);
              }
            }else{
              appendToPane(jtextFilDiscu, message);
            }
          }
        }
        catch (IOException ex) {
          System.err.println("exit");
        }
      }
    }
  }


  private void appendToPane(JTextPane tp, String msg){
    HTMLDocument doc = (HTMLDocument)tp.getDocument();
    HTMLEditorKit editorKit = (HTMLEditorKit)tp.getEditorKit();
    try {
      editorKit.insertHTML(doc, doc.getLength(), msg, 0, 0, null);
      tp.setCaretPosition(doc.getLength());
    } catch(Exception e){
      e.printStackTrace();
    }
  }
}
