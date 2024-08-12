// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package client;

import ocsf.client.*;
import supplier.MainPageSupplierController;
import supplier.SupplierEditItamController;
import client.*;
import entities.*;
import gui.RestaurantSelectionController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import common.ChatIF;

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
  public static RestaurantOrders s1 = new RestaurantOrders("chackkk",0,0,null, null, 0, 0, null, null, null, null, null, null, null, null);
  public static User user1 = new User(0,null,null,null,null,null,null,false,0,null);
  public static ArrayList<Restaurant> restaurants = new  ArrayList<Restaurant>();
  public static ArrayList<RestaurantOrders> receivedOrders = new ArrayList<RestaurantOrders>();
  
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
  public void handleMessageFromServer(Object msg) 
  {
	    // Printing a message with colors
	    System.out.println("-->test5: in " + GREEN + "Class ChatClient extends AbstractClient" + RESET + BLUE + " func --handleMessageFromServer--" + RESET + " The server sent a response to the client");

	    System.out.println("Message received from server: " + msg);
	   
	    // Update the awaitResponse flag
	    awaitResponse = false;
	    
	    BiteOptions answer = (BiteOptions) msg;
	    
	    System.out.println("answe received from server: " + answer);

		try
		{
			switch(answer.getOption())
			{
			case LOGIN:		
				System.out.println("Client-Test6: We entered the LOGIN case " );
			
				
				User user= new User();
				
				if(!answer.getData().toString().equals("-1"))//אם חזר אובייקט משתמש מהשרת רק אז נשמור את זה לוקלית בלקוח
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
		        	ChatClient.user1.setUsername("inf");// ערך זבל כדאי לקבל הודעת שגיאה על סיסמא ולא על שם משתמש
		            ChatClient.user1.setPassword("-2");
					System.out.println("user1 with wrong Password:"+ChatClient.user1);
				}
				
				//במקרה והשם משתמש וסיסמה נכונים, אז אנחנו רוצים לטעון את השדות של המשתמש שחזר מהשרת לתוך המשתמש1 שעובד עם החלונות הגרפים של המשתמש
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
		            ChatClient.user1.setaccountStatus(user.getaccountStatus());
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
				
			   case LOGIN_RESTAURANT:
				   Restaurant restaurant = new Restaurant();
				   System.out.println("ChatClient: "+answer);
				   restaurant = Restaurant.fromString(answer.getData().toString());
			    	// Send the restaurant to the main page supplier controller
					if (MainPageSupplierController.controller != null) {
						MainPageSupplierController.controller.setRestaurant(restaurant);
					}
					
				case SHOW_MENU_RESTAURANT:
					
					if (answer.getData()!=null) {
			           // List<MenuItem> menuItems = (List<MenuItem>) list;
			            System.out.println("-->test5: Received list of menu items from server: " + answer);
			            if (SupplierEditItamController.instance != null) {
		                    System.out.println("-->test5: Setting menu items list in SupplierEditItamController");
		                    SupplierEditItamController.setMenuItemsList(answer);
		                } else {
		                    System.out.println("-->test5: SupplierEditItamController instance is null");
		                }
					}
					break;
					
				case DELETE_ITEM_MENU:
					if (answer.getData()!=null) {
				           // List<MenuItem> menuItems = (List<MenuItem>) list;
				            System.out.println("ChatClient: we entered UDELETE_ITEM_MENU: " + answer.getData());
				            if (answer.getData().equals("success")) {
			                    System.out.println("-->: successful delete");
			                    SupplierEditItamController.GetMessageFromServer("successful delete from DB");
			                } else {
			                    System.out.println("-->test5: SupplierEditItamController instance is null");
			                }
						}
					break;
					
				case UPDATE_MENU:
					System.out.println("ChatClient: we entered UPDATE_MENU: "+answer.getData());
					if (answer.getData()==null) {
	                    System.out.println("-->: unsuccessful menu update");
	                    SupplierEditItamController.GetMessageFromServer("unsuccessful menu update");
	                } else {
	                    System.out.println("-->test: successful menu update");
	                    SupplierEditItamController.GetMessageFromServer("successful menu update");
	                    String dataString = answer.getData().toString();
	                    MenuItem receivedMenuItem = MenuItem.fromString(dataString);
	                    SupplierEditItamController.getController().MenuItemAddOrupdatedFromChat(receivedMenuItem);

	                }	
					
			   case RETRIEVE_MANAGE_ORDER_LIST:
				   System.out.println("Client received RETRIEVE_MANAGE_ORDER_LIST response");
				   receivedOrders = RestaurantOrders.fromStringArray(answer.getData().toString());
				   System.out.println("Number of Orders loaded: " + receivedOrders.size());
				   break;
					
			   case SELECT_RESTAURANT:
				    System.out.println("Client received SELECT_RESTAURANT response");
		            restaurants = Restaurant.fromStringArray(answer.getData().toString());
		            System.out.println("Number of restaurants loaded: " + restaurants.size());
		            for (Restaurant r : restaurants) {
		                System.out.println("Loaded restaurant: " + r.getName());
		            }
		            break;
				
			}
		}
		//זה שייך לסוויץ-קייס הראשי
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
	    try {
	        System.out.println("Sending message to server: " + list);
	        openConnection();
	        awaitResponse = true;
	        sendToServer(list);
	        
	        // Add a timeout mechanism
	        long startTime = System.currentTimeMillis();
	        long timeout = 10000; // 10 seconds timeout
	        while (awaitResponse) {
	            if (System.currentTimeMillis() - startTime > timeout) {
	                System.out.println("Server response timed out");
	                awaitResponse = false;
	                break;
	            }
	            try {
	                Thread.sleep(100);
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    } catch(IOException e) {
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
  
  
  //aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
  @Override
  protected void connectionEstablished() 
  {
     	System.out.println("testAAA: The connection between the server and the client was successful ");

  }
  
  
}
//End of ChatClient class