import java.util.ArrayList;
import java.util.Scanner;

public class UserHandler implements Runnable{

		  private Server server;
		  private User user;
		  

		  public UserHandler(Server server, User user) {
		    this.server = server;
		    this.user = user;
		    this.server.broadcastAllUsers();
		  }

		  public void run() {
		    String message;
		    // when there is a new message, broadcast to all
		    Scanner sc = new Scanner(this.user.getInputStream());
		    while (sc.hasNextLine()) {
		      message = sc.nextLine();
		      if (message.charAt(0) == '@'){
		        if(message.contains(" ")){
		          System.out.println("(Whisper) " + message);
		          int firstSpace = message.indexOf(" ");
		          String userPrivate= message.substring(1, firstSpace);
		          server.sendMessageToUser(message.substring( firstSpace+1, message.length()), user, userPrivate);
		        }
		      }else if (message.charAt(0) == '#'){
		        this.server.broadcastAllUsers();
		      }
		      else if(message.charAt(0) == '*') {
		    	  ArrayList<String> multi= new ArrayList<String>();
		    	  String name = "",actual = "";
		  		  int i =1,j = 0;
		    	  if(message.contains(" ")){
		    		  System.out.println("Creating Group Chat");
		    		  while(i != message.length() && message.charAt(i) != ' ') {
		  				StringBuilder sb = new StringBuilder();
		  				while(message.charAt(i) != ',' && message.length()-1 != i) {
		  					sb.append(message.charAt(i));					
		  					i++;
		  				}
		  				System.out.println("User: " + name);		  				
		  				name = sb.toString();
		  				multi.add(j, name);
		  				i++;
		  				j++;
		  			}    
		  			i++;	
		  			System.out.println("i count: " + i);
		  			
		  			System.out.println("List Size: " + multi.size());
		  			
					while(i < message.length() && message.charAt(i) != '.') {
					StringBuilder str = new StringBuilder();
						while(message.charAt(i) != '.') {		
							str.append(message.charAt(i));
							i++;
						}		 
						 actual = str.toString();
						 System.out.println("Message:" + '[' + actual + ']');
					}
		    		  
		    	 server.multicast(user,multi,actual);
		    	  }
		      }
		      else{
		        // update 
		        server.broadcastMessages(message, user);
		      }
		    }
		    server.removeUser(user);
		    this.server.broadcastAllUsers();
		    sc.close();
		  }
}
