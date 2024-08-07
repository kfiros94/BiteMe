// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 
package client;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import client.*;
import common.ChatIF;
import entities.BiteOptions;


/**
 * This class constructs the UI for a chat client.  It implements the
 * chat interface in order to activate the display() method.
 * Warning: Some of the code here is cloned in ServerConsole 
 *
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Dr Timothy C. Lethbridge  
 * @author Dr Robert Lagani&egrave;re
 * @version July 2000
 */
public class ClientController implements ChatIF 
{
	
	
	//כלי עזר לשינוי צבע של הדפסות
    public static final String RESET = "\033[0m";  // Text Reset
    public static final String GREEN = "\033[0;32m";   // GREEN
    public static final String RED = "\033[0;31m";     // RED
    public static final String BLUE = "\033[0;34m";    // BLUE

	//כלי עזר לשינוי צבע של הדפסות
	
    public static String MyipHost;
	
  //Class variables *************************************************
  
  /**
   * The default port to connect on.
   */
   public static int DEFAULT_PORT ;
  
  //Instance variables **********************************************
  
  /**
   * The instance of the client that created this ConsoleChat.
   */
  ChatClient client;

  //Constructors ****************************************************

  /**
   * Constructs an instance of the ClientConsole UI.
   *
   * @param host The host to connect to.
   * @param port The port to connect on.
   */
  public ClientController(String host, int port) 
  {
    try 
    {
      client= new ChatClient(host, port, this);
    } 
    catch(IOException exception) 
    {
      System.out.println("Error: Can't setup connection!"+ " Terminating client.");
      System.exit(1);
    }
  }
  
  

  public void setMyhost(String Myhost) 
  {
	System.out.println("test3 CCCCChange my HHHOst");//TTTTTTTTTTTTTTTTTTTTTTTT
	  client.setHost(Myhost);

  }
  
  
  
  //Instance methods ************************************************
  
  /**
   * This method waits for input from the console.  Once it is 
   * received, it sends it to the client's message handler.
   */
  public void accept(Object object) 
  {
	System.out.println("test3: in "+GREEN+"Class ClientController"+RESET+BLUE+" func --accept--"+RESET+" Call to"+BLUE+" func --handleMessageFromClientUI--"+RESET);//TTTTTTTTTTTTTTTTTTTTTTTT
	client.handleMessageFromClientUI(object);
	 // client.setHost("DF");

  }
  
 
  /**
   * This method overrides the method in the ChatIF interface.  It
   * displays a message onto the screen.
   *
   * @param message The string to be displayed.
   */
  public void display(String message) 
  {
    System.out.println("> " + message);
  }
}
//End of ConsoleChat class
