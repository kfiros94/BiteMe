// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package client;

import ocsf.client.*;
import client.*;
import entities.*;
import common.ChatIF;
import entities.ClientInfo;
import entities.Order;
import entities.User;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class overrides some of the methods defined in the abstract
 * superclass in order to give more functionality to the client.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;
 * @author Fran&ccedil;ois B&eacute;langer
 * @version July 2000
 */
public class ChatClient extends AbstractClient
{
	
	// Utility for changing print colors
    public static final String RESET = "\033[0m";  // Text Reset
    public static final String GREEN = "\033[0;32m";   // GREEN
    public static final String RED = "\033[0;31m";     // RED
    public static final String BLUE = "\033[0;34m";    // BLUE

	
  //Instance variables **********************************************
  
  /**
   * The interface type variable.  It allows the implementation of 
   * the display method in the client.
   */
  ChatIF clientUI; 
  public static Order  s1 = new Order("chackkk",0,0,0,null,new ClientInfo(null,null,null));
  public static User user1 = new User(0,null,null,null,null,null,null,false,0);
  
  // Variable that helps understand if a message has returned from the server to the client
  public static boolean awaitResponse = false;

  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the chat client.
   *
   * @param host The server to connect to.
   * @param port The port number to connect on.
   * @param clientUI The interface type variable.
   */
	 
  public ChatClient(String host, int port, ChatIF clientUI) 
    throws IOException 
  {
    super(host, port); // Call the superclass constructor
    this.clientUI = clientUI;
    //openConnection();
  }

  //Instance methods ************************************************
    
  /**
   * This method handles all data that comes in from the server.
   *
   * @param msg The message from the server.
   */

 // Defines an order and not a client
 // Here a message is received back from the server
 // The flag is set to false in order to exit active waiting
 // Then the client's order screen is updated
  public void handleMessageFromServer(Object msg) 
  {
	    // Update the awaitResponse flag
	    awaitResponse = false;

	    // Printing a message with colors
	    System.out.println("-->test5: in " + GREEN + "Class ChatClient extends AbstractClient" + RESET + BLUE + " func --handleMessageFromServer--" + RESET + " The server sent a response to the client");

	    System.out.println("Message received from server: " + msg);
	    
	    BiteOptions answer = (BiteOptions) msg;
	    
	    System.out.println("answe received from server: " + answer);
	    
		try
		{
			switch(answer.getOption())
			{
			
			case LOGIN:		
				System.out.println("Client-Test6: We entered the LOGIN case " );
			
				User user= new User();
				
		        // If a user object is returned from the server, only then save it locally on the client
				if(!answer.getData().toString().equals("-1"))
				{
			         user = User.fromString(answer.getData().toString());
					System.out.println("answer User From server:"+user);
				}				
				
				if(answer.getData().toString().equals("-1"))
				{
		            // User not found
		            ChatClient.user1.setUsername("-1");
				}				
				
				else if(user.getPassword().equals("-2"))
				{

		            // Incorrect password
		        	ChatClient.user1.setUsername("inf"); // Placeholder value to receive a password error message instead of a username error
		            ChatClient.user1.setPassword("-2");
					System.out.println("user1 with wrong Password:"+ChatClient.user1);

				}				
				
				// In case the username and password are correct, we want to load the fields of the user returned from the server into user1, which is used with the user's graphical windows				
				else if(!user.getPassword().equals("-2"))
				{
		            ChatClient.user1.setUserId(user.getUserId());
		            ChatClient.user1.setUsername(user.getUsername());
		            ChatClient.user1.setPassword(user.getPassword());
		            ChatClient.user1.setEmail(user.getEmail());
		            ChatClient.user1.setPhoneNumber(user.getPhoneNumber());
		            ChatClient.user1.setPermission(user.getPermission());
		            ChatClient.user1.setBranch(user.getBranch());
		            ChatClient.user1.setHasDiscountCode(user.isHasDiscountCode());
		            ChatClient.user1.setLoggedIn(user.getLoggedIn());

					System.out.println("user1 all Fileds we get from Server:"+ChatClient.user1); 
				}
			    else 
			    {
			        System.out.println("Error: Unexpected server response format.");
			    }				
				break;
				

				
			   case LOGOUT:
					System.out.println("Client-Test6: We entered the LOGOUT case " );
					break;	
			}
		}

		catch (Exception e) 
		{
			e.printStackTrace();
		}	    
	}
 
  /**
   * This method handles all data coming from the UI            
   *
   * @param id The message from the UI.    
   */
  public void handleMessageFromClientUI(Object list)  
  {
    try
    {	
    	//System.out.println("Option data + option" + (BiteOptions)list);
    	System.out.println("Option data + option" + list);

    	// Connects to the server
    	openConnection();// In order to send more than one message  
    	awaitResponse = true; // Static variable that helps us understand if the server has returned a response
       	System.out.println("test4: try to send");
       	
        //System.out.println("print Size of Msg of Client:"+list.size());
       	
    	sendToServer(list);
       	System.out.println("test4: print list to msg server:"+list);

		// wait for response
       	// As long as the server has not returned a response, the client is in active waiting
		while (awaitResponse)
		{
			try {
				Thread.sleep(100);
		       	System.out.println("test4: i wait for Response from server ");

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
    }
    catch(IOException e)
    {
    	e.printStackTrace();
      clientUI.display("Could not send message to server: Terminating client."+ e);
      quit();
    }
  }

  
  /**
   * This method terminates the client.
   */
  public void quit()
  {
    try
    {
      closeConnection();
    }
    catch(IOException e) {}
    System.exit(0);
  }
  
  
  @Override
  protected void connectionEstablished() 
  {
     	System.out.println("testAAA: The connection between the server and the client was successful ");

  }  
}