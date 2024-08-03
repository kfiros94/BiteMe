// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package client;

import ocsf.client.*;
import client.*;
import common.ChatIF;
import logic.ClientInfo;
import logic.Order;
import logic.User;

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
	
	//כלי עזר לשינוי צבע של הדפסות
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
  
  //משתנה שעוזר להבין אם חזרה הודעה מהשרת ללקוח
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
    super(host, port); //Call the superclass constructor
    this.clientUI = clientUI;
    //openConnection();
  }

  //Instance methods ************************************************
    
  /**
   * This method handles all data that comes in from the server.
   *
   * @param msg The message from the server.
   */

  ///////////מימוש של ישראל מגדיר הזמנה ולא לקוח
  //כאן מקבלים חזרה הודעה מהשרת
  //הופכים את הדגל לשקר בשביל לצאת מהמתנה פעילה
  //ואז מעדכנים את הנתונים במסך של ההזמנה של הלקוח
  public void handleMessageFromServer(Object msg) {
	    // Update the awaitResponse flag
	    awaitResponse = false;

	    // Printing a message with colors
	    System.out.println("-->test5: in " + GREEN + "Class ChatClient extends AbstractClient" + RESET + BLUE + " func --handleMessageFromServer--" + RESET + " The server sent a response to the client");

	    System.out.println("Message received from server: " + msg);

	    if (msg instanceof String) 
	    {
	        String message = (String) msg;
	        if (message.equals("order")) 
	        {
	            // Handle the "order" message
	            System.out.println("Received 'order' from server");

	            // Additional handling for 'order' message if needed
	            return; // Early return if we only handle the "order" message
	        }
	    } 
	    /*
	    if (msg instanceof ArrayList) {
	        // Handle the ArrayList message, assuming it's an ArrayList<Order>
	        ArrayList<Order> orders = (ArrayList<Order>) msg;
	        System.out.println("Received order list from server: " + orders);

	        // Additional handling for order list if needed
	        return; // Early return if we handle the order list
	    }*/
	    
	    if (msg.equals("-1")) {
            // User not found
            ChatClient.user1.setUsername("-1");
        } else if (msg.equals("-2")) {
            // Incorrect password
        	ChatClient.user1.setUsername("inf");// ערך זבל כדאי לקבל הודעת שגיאה על סיסמא ולא על שם משתמש
            ChatClient.user1.setPassword("-2");
	    
        }else if (msg instanceof ArrayList) {
	        // Handle the ArrayList message, assuming it's an ArrayList<Order>
	        ArrayList<User> users = (ArrayList<User>) msg;
	        System.out.println("Received order list from server: " + users);

	        // Additional handling for order list if needed
	        return; // Early return if we handle the order list
	    }
	    

	    // Convert the message to a string
	    String st = msg.toString();
	    System.out.println("-->test5: answer From Server: " + st);

	    // Remove the surrounding brackets and split by comma
	    //String trimmed = st.replaceAll("^\\[\\[|\\]\\]$", ""); // Remove the outer brackets
	    String trimmed = st.replaceAll("^\\[|\\]$", ""); // Remove the outer brackets
	    String[] result = trimmed.split(",\\s*"); // Split by comma and optional whitespace

	    System.out.println("-->test5: print resultList: " + Arrays.toString(result));

	    // Ensure the array has the expected number of elements before accessing them
	    /*
	    if (result.length >= 1) {
	        try {
	            // Save the first string from the result array to the restaurant field of s1
	          //  s1.setRestaurant(result[0].trim());

	            // For demonstration, let's print out the first string
	            System.out.println("-->test5: restaurant: " + result[0]);

	            // Continue updating other fields if needed
	             s1.setRestaurant(result[0].trim());
	             s1.setOrderNumber(Integer.parseInt(result[1].trim()));
	             s1.setTotalPrice(Float.parseFloat(result[2].trim()));
	             s1.setOrderListNumber(Integer.parseInt(result[3].trim()));
	             s1.setOrderAddress(result[4].trim());
	            
	            // s1.setClientInfo(new ClientInfo(result[3].trim(), result[4].trim(), result[5].trim()));
	        }
	        catch (NumberFormatException e) {
	            System.out.println("Error parsing number from server response: " + e.getMessage());
	        }
	        */
	    if (st.startsWith("User{") && st.endsWith("}")) {
	        try {
	            // Remove the surrounding "User{" and "}"
	            String userDetails = st.substring(5, st.length() - 1);
	            String[] userFields = userDetails.split(", ");

	            // Extract fields
	            int userId = Integer.parseInt(userFields[0].split("=")[1].trim());
	            String username = userFields[1].split("=")[1].trim().replace("'", "");
	            String password = userFields[2].split("=")[1].trim().replace("'", "");
	            String email = userFields[3].split("=")[1].trim().replace("'", "");
	            String phoneNumber = userFields[4].split("=")[1].trim().replace("'", "");
	            String permission = userFields[5].split("=")[1].trim().replace("'", "");
	            String branch = userFields[6].split("=")[1].trim().replace("'", "");
	            boolean hasDiscountCode = Boolean.parseBoolean(userFields[7].split("=")[1].trim());
	            int loggedIn = Integer.parseInt(userFields[8].split("=")[1].trim());

	            // Update the user object
	            ChatClient.user1.setUserId(userId);
	            ChatClient.user1.setUsername(username);
	            ChatClient.user1.setPassword(password);
	            ChatClient.user1.setEmail(email);
	            ChatClient.user1.setPhoneNumber(phoneNumber);
	            ChatClient.user1.setPermission(permission);
	            ChatClient.user1.setBranch(branch);
	            ChatClient.user1.setHasDiscountCode(hasDiscountCode);
	            ChatClient.user1.setLoggedIn(loggedIn);

	            // Print user details
	            System.out.println("-->test5: User Details: " + ChatClient.user1);

	        } catch (Exception e) {
	            System.out.println("Error parsing user from server response: " + e.getMessage());
	            e.printStackTrace();
	        }
	    } else {
	        System.out.println("Error: Unexpected server response format.");
	    }
	}
 
  /**
   * This method handles all data coming from the UI            
   *
   * @param id The message from the UI.    
   */
  
  public void handleMessageFromClientUI(List<String> list)  
  {
    try
    {
    	
    	System.out.println("test4: in "+GREEN+"Class ChatClient extends AbstractClient"+RESET+BLUE+" func --handleMessageFromClientUI--"+RESET+" Receives the message from the client's screen and manages the event against the server");//TTTTTTTTTTTTTTTTTTTTTTTT

    	//מתחבר לשרת
    	openConnection();//in order to send more than one message
       //משתנה סטטי שעוזר לנו להבין אם הזרת החזיר לנו תשובה
    	awaitResponse = true;
       	System.out.println("test4: try to send");
       	
        System.out.println("pring Size of Msg of Client:"+list.size());
        

       	
    	sendToServer(list);
       	System.out.println("test4: print list to msg server:"+list);

		// wait for response
       	//כל עוד השרת לא החזיר תשובה הלקוח בהמתנה פעילה
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
}
//End of ChatClient class
