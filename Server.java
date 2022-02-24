import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Server {

	  private int port;
	  private List<User> clients;
	  private ServerSocket server;

	  public static void main(String[] args) throws IOException {
		serverGUI gui = new serverGUI();
	  }

	  public Server(int port) {
	    this.port = port;
	    this.clients = new ArrayList<User>();
	  }

	  public void run() throws IOException {
	    server = new ServerSocket(port) {
	      protected void finalize() throws IOException {
	        this.close();
	      }
	    };
	    System.out.println("Port # "+ port +  " is now open.");

	    while (true) {
	      // accepts a new client
	      Socket client = server.accept();

	      // get nickname of newUser
	      String nickname = (new Scanner ( client.getInputStream() )).nextLine();
	      nickname = nickname.replace(",", ""); 
	      nickname = nickname.replace(" ", "_");
	      System.out.println("New Client: \"" + nickname + "\"\n\t     Host:" + client.getInetAddress().getHostAddress());

	      // create new User
	      User newUser = new User(client, nickname);

	      // add newUser message to list
	      this.clients.add(newUser);

	      // Welcome msg
	      newUser.getOutStream().println(newUser.toString());

	      // create a new thread for newUser incoming messages handling
	      new Thread(new UserHandler(this, newUser)).start();
	    }
	  }

	  // delete from list
	  public void removeUser(User user){
	    this.clients.remove(user);
	  }

	  //send messages
	  public void broadcastMessages(String msg, User userSender) {
	    for (User client : this.clients) {
	      client.getOutStream().println(
	          userSender.toString() +  msg);
	    }
	  }

	  // send clients
	  public void broadcastAllUsers(){
	    for (User client : this.clients) {
	      client.getOutStream().println(this.clients);
	    }
	  }

	  // send private message
	  public void sendMessageToUser(String msg, User userSender, String user){
	    boolean find = false;
	    for (User client : this.clients) {
	      if (client.getNickname().equals(user) && client != userSender) {
	        find = true;
	        userSender.getOutStream().println(userSender.toString() + " -> " + client.toString() +": " + msg);
	        client.getOutStream().println(
	            "(<b>Private</b>)" + userSender.toString() +  msg);
	      }
	    }
	    if (!find) {
	      userSender.getOutStream().println(userSender.toString() + " -> (<b>no one!</b>): " + msg);
	    }
	  }
	  
	  //send groupchat message
	  public void multicast (User userSender,ArrayList<String>multi,String msg) {
	  ArrayList<String> validUsers= new ArrayList<String>();
	  StringBuilder sb = new StringBuilder();
	  int j = 0;
	  for (User client : this.clients) {
			for(int k=0;k < multi.size();k++) {
				  if (client.getNickname().equals(multi.get(k))) {
					  validUsers.add(j,client.getNickname());
					  sb.append(client.getNickname() + ',');
					  j++;
				  }
			} 
			System.out.println(sb.toString());
	  }
	  
	  if(validUsers.size() >= 1) {
		  userSender.getOutStream().println("<b>[Group Chat] (Message Sent) -> (" + sb.toString() + userSender.toString() + ")</b> :  " + msg );	   
		  for (User client : this.clients) { 
			  for(int users =0;users < validUsers.size();users++) {
				  if (client.getNickname().equals(validUsers.get(users))){
					  client.getOutStream().println("<b>[Group Chat] (Message Receieved) -> (" + sb.toString() + userSender.toString() + ")</b> :  " + msg);
				  }
			  }
		  }
	  }
	  else {
		  userSender.getOutStream().println(userSender.toString() + " -> (<b>No One to send Group message to :</b>): ");
	  }
	  
//	  GCGui gui = new GCGui();
	  }
}
