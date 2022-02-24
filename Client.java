import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
	  private String host;
	  private int port;

	  public Client(String host, int port) {
	    this.host = host;
	    this.port = port;
	  }
	
	  public void run() throws UnknownHostException, IOException {
	    // connect client to server
	    Socket client = new Socket(host, port);
	    System.out.println("Client successfully connected to server!");

	    // Get Socket output stream (where the client send her mesg)
	    PrintStream output = new PrintStream(client.getOutputStream());

	    Scanner sc = new Scanner(System.in);
	    System.out.print("Enter a nickname: ");
	    String nickname = sc.nextLine();

	    // send nickname to server
	    output.println(nickname);

	    // new thread
	    new Thread(new MessageHandler(client.getInputStream())).start();

	    // read messages from keyboard and send to server
	    System.out.println("Messages: \n");

	    // while new messages
	    while (sc.hasNextLine()) {
	      output.println(sc.nextLine());
	    }

	    output.close();
	    sc.close();
	    client.close();
	  }
}
