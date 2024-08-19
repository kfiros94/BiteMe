// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package client;

import ocsf.client.*;
import supplier.MainPageSupplierController;
import supplier.SupplierEditItamController;
import supplier.Supplier_OrderManagementController;
import client.*;
import entities.*;
import gui.MainPagesClientController;
import gui.RestaurantSelectionController;
import gui.StartOrderController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import common.ChatIF;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class overrides some of the methods defined in the abstract
 * superclass in order to give more functionality to the client.
 *
 * @author Kfir Amoyal
 * @author Noam Furman
 * @author Israel Ohayon
 * @author Eitan Zerbel
 * @author Yaniv Shatil
 * @author Omri Heit

 * @version August 2024
 */
public class ChatClient extends AbstractClient
{
	
	// Utility for changing print colours
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
  public static User user1 = new User(0,null,null,null,null,null,null,false,0,null);
  public static ArrayList<Restaurant> restaurants = new  ArrayList<Restaurant>();
  public static ArrayList<MenuItems> menuItems = new  ArrayList<MenuItems>();
  
  public static ArrayList<User> userEmptyArry = new ArrayList<User>();
  
  public static int discountCount=0;

  

public static ArrayList<RestaurantOrders> customer_all_orders1 = new ArrayList<>();

//Convert the ArrayList to an ObservableList
public static ObservableList<RestaurantOrders> observableOrdersList = FXCollections.observableArrayList(customer_all_orders1);


public static ArrayList<RestaurantOrders> Restaurantall_orders = new ArrayList<>();
public static ArrayList<RestaurantOrders> Restaurantall_Type_Food_orders = new ArrayList<>();
public static ArrayList<RestaurantOrders> Restaurantall_orders_per = new ArrayList<>();

public static int UserIdUpdate = 0;
  
  //Variable that helps understand if a message has returned from the server to the client
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

//Defines an order and not a client
//Here a message is received back from the server
//The flag is set to false in order to exit active waiting
//Then the client's order screen is updated
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
		        	ChatClient.user1.setUsername("inf");// // Placeholder value to receive a password error message instead of a username error
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

				
					
			   	case SELECT_RESTAURANT:
				   System.out.println("Client received SELECT_RESTAURANT response");
		            restaurants = Restaurant.fromStringArray(answer.getData().toString());
		            System.out.println("Number of restaurants loaded: " + restaurants.size());
		            for (Restaurant r : restaurants) 
		            {
		                System.out.println("Loaded restaurant: " + r.getName());
		            }
		            break;
		            
		            
		            
		            
			   	case GET_SELECTED_REST_MENU:
				   
				   System.out.println("Client received SELECT_RESTAURANT response");
				   menuItems = MenuItems.fromStringArray(answer.getData().toString());
		            System.out.println("Number of items of restaurant loaded: " + menuItems.size());
		            System.out.println("The items of restaurant loaded: " + menuItems);

				   
				   
		            break;
		            
			   	case BACK_HOME_CUSTOMER_PAGE:

				   System.out.println("Client received BACK_HOME_CUSTOMER_PAGE response");
				   user1 = User.fromString(answer.getData().toString());
		           System.out.println("LETS SEE WHAT IS THE ANSWER: " + user1);
		           MainPagesClientController mpc = new  MainPagesClientController();
		           mpc.loadUserClient(user1);
				   
		            break;


		            
		            
			   	case GET_USER_ORDERS:
				   System.out.println("Client received GET_USER_ORDERS response");

				   customer_all_orders1 = RestaurantOrders.fromStringArray(answer.getData().toString());
		           System.out.println("LETS SEE WHAT IS THE ANSWER: " + customer_all_orders1);
				   
				   //costumer_all_orders1
				   
		            break;
		            
			   	case LOGIN_RESTAURANT:
				   System.out.println("Client received GET_USER_ORDERS response");
			  
				   Restaurant restaurant = new Restaurant();
				   System.out.println("ChatClient: "+answer);
				   restaurant = Restaurant.fromString(answer.getData().toString());
			    	// Send the restaurant to the main page supplier controller
					if (MainPageSupplierController.controller != null) {
						MainPageSupplierController.controller.setRestaurant(restaurant);
					}
				  // customer_all_orders1 = RestaurantOrders.fromStringArray(answer.getData().toString());
		           System.out.println("LETS SEE WHAT IS THE ANSWER: " + answer.getData().toString());
				   
		            break;
		            
			   	case SHOW_MENU_RESTAURANT:        
	                  if (answer.getData() != null) {
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
	                  if (answer.getData() != null) {
	                      System.out.println("ChatClient: we entered DELETE_ITEM_MENU: " + answer.getData());
	                      if (answer.getData().equals("success")) {
	                          System.out.println("-->: Successful delete");
	                          SupplierEditItamController.GetMessageFromServer("Successful delete from DB");
	                      } else {
	                          System.out.println("-->test5: SupplierEditItamController instance is null");
	                      }
	                  }
	                  break;
	                  
	              case UPDATE_MENU:
	                  System.out.println("ChatClient: we entered UPDATE_MENU: " + answer.getData());
	                  if (answer.getData() == null) {
	                      System.out.println("-->: Unsuccessful menu update");
	                      SupplierEditItamController.GetMessageFromServer("Unsuccessful menu update");
	                  } else {
	                      System.out.println("-->test: Successful menu update");
	                      SupplierEditItamController.GetMessageFromServer("Successful menu update");
	                      String dataString = answer.getData().toString();
	                      MenuItem receivedMenuItem = MenuItem.fromString(dataString);
	                      SupplierEditItamController.getController().MenuItemAddOrupdatedFromChat(receivedMenuItem);
	                  }
	                  break;
	                  
	                  
	              case GET_RESTAURANT_ORDERS:  
                      System.out.println("-->test:in charclient case GET_RESTAURANT_ORDERS ");
                      if (Supplier_OrderManagementController.instance != null) {
                          System.out.println("-->test5: Setting orders in Supplier_OrderManagementController");
                          Supplier_OrderManagementController.instance.loadRestaurantOrders(answer);
                      } else {
                          System.out.println("-->test5: Supplier_OrderManagementController instance is null");
                      }        	   
			            break;
			         
	              case GET_USER_FOR_NOTIFICATION:
                      System.out.println("-->test:in charclient case GET_RESTAURANT_ORDERS ");
                      if (Supplier_OrderManagementController.instance != null) {
                          System.out.println("-->test5: Setting orders in Supplier_OrderManagementController");
                          User sendusernoam =User.fromString(answer.getData().toString());
                          Supplier_OrderManagementController.instance.settUserDitales(sendusernoam);
                      } else {
                          System.out.println("-->test5: Supplier_OrderManagementController instance is null");
                      }        	   
			            break;

	         			
	              case FETCH_INCOME_REPORTS:
	                     
	  	                System.out.println("ChatClient: we entered FETCH_INCOME_REPORTS: ");
	  	               
	  	                Restaurantall_orders = RestaurantOrders.fromStringArray(answer.getData().toString());
	  			        System.out.println("LETS SEE WHAT IS THE ANSWER: " + Restaurantall_orders);
	                     
	                     

	                      break;
	                      
	                      
	                      
	           		case FETCH_ORDER_REPORTS:
	           				
		  	                System.out.println("ChatClient: we entered FETCH_ORDER_REPORTS: ");
		  	              //Restaurantall_Type_Food_orders
	           				
		  	              Restaurantall_Type_Food_orders = RestaurantOrders.fromStringArray(answer.getData().toString());
		  			        System.out.println("LETS SEE WHAT IS THE ANSWER: " + Restaurantall_Type_Food_orders);
		  	                
	           				
		                      break;
		                      
		                      
		                      
		                      
	           			case FETCH_PERFORMENCE_REPORTS:
		                     
		  	                System.out.println("ChatClient: we entered FETCH_PERFORMENCE_REPORTS: ");
		  	               
		  	              Restaurantall_orders_per = RestaurantOrders.fromStringArray(answer.getData().toString());
		  			        System.out.println("LETS SEE WHAT IS THE ANSWER: " + Restaurantall_orders);

	                      break;

	                      
	                      
	           			case CREATE_USER:
		  	                System.out.println("ChatClient: we entered CREATE_USER: ");

		  	              userEmptyArry = User.fromStringArray(answer.getData().toString());
			  			  System.out.println("LETS SEE WHAT IS THE ANSWER CREATE_USER: " + userEmptyArry);
	           				
		                  break;
		                  
	                  
	           			case CLASIFY_UPDATE_USER_BY_EMAIL:
		  	                System.out.println("ChatClient: we entered CLASIFY_UPDATE_USER_BY_EMAIL: ");

				  			System.out.println("LETS SEE WHAT IS THE ANSWER CLASIFY_UPDATE_USER_BY_EMAIL: " + answer.getData().toString());

				  			UserIdUpdate= (int)answer.getData();
				  			
				  			System.out.println("LETS SEE WHAT IS THE ANSWER CLASIFY_UPDATE_USER_BY_EMAIL INT: " + UserIdUpdate);

	           				
			                  break;
			                  
			                  
	           			case UPDATE_CUSTOMER:
	           				
		  	                System.out.println("ChatClient: we entered UPDATE_CUSTOMER: ");

				  			System.out.println("LETS SEE WHAT IS THE ANSWER UPDATE_CUSTOMER: " + answer.getData().toString());


			                  break;

	                  
			                  
	           			case UPDATE_RESTAURANT:
	           				
		  	                System.out.println("ChatClient: we entered UPDATE_CUSTOMER: ");

				  			System.out.println("LETS SEE WHAT IS THE ANSWER UPDATE_CUSTOMER: " + answer.getData().toString());
	           				
	           				
	           				
			                  break;
			              
	           		  case GET_DISCOUNT_COUNT:
	                      int discountCountanwser = Integer.parseInt(answer.getData().toString());
	                      System.out.println("User has " + discountCount + " discount code(s) available.");
	                      this.discountCount=discountCountanwser;
	                      
	                      break;
	                      
	           		case UPDATE_DISCOUNT_COUNT:
	                      int newdiscountCount = Integer.parseInt(answer.getData().toString());
	                      System.out.println("User has " + discountCount + " discount code(s) available.");
	                      if (newdiscountCount!=-1) {
		                      this.discountCount=newdiscountCount;

	                      }
	                      
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
   * @param list id The message from the UI.    
   */
  
  public void handleMessageFromClientUI(Object list)  
  {
	  try {
	    	// Connects to the server
	        System.out.println("Sending message to server: " + list);
	        openConnection();// In order to send more than one message 
	        awaitResponse = true;// Static variable that helps us understand if the server has returned a response
	        sendToServer(list);
	        
	        // Add a timeout mechanism
	        long startTime = System.currentTimeMillis();
	        long timeout = 10000; // 10 seconds timeout
	        // wait for response
	       	// As long as the server has not returned a response, the client is in active waiting
	        while (awaitResponse) 
	        {
                System.out.println("Test4: I am waiting for a reply from the server");

	            if (System.currentTimeMillis() - startTime > timeout) 
	            {
	                System.out.println("Server response timed out");
	                awaitResponse = false;
	                break;
	            }
	            try 
	            {
	                Thread.sleep(100);
	            } 
	            catch (InterruptedException e) 
	            {
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
  
  
  @Override
  protected void connectionEstablished() 
  {
     	System.out.println("testAAA: The connection between the server and the client was successful ");

  }
  
  
  /**
   * This method updates the user in the MainPagesClientController.
   * It ensures the update is performed on the JavaFX Application Thread.
   *
   * @param user The user object to update.
   */
  public static void updateUserInMainPagesController(User user) {
      Platform.runLater(() -> {
          try {
              FXMLLoader loader = new FXMLLoader(ChatClient.class.getResource("/gui/MainPagesClient.fxml"));
              Parent root = loader.load();
              MainPagesClientController controller = loader.getController();
              controller.loadUserClient(user);
              
              // Update the existing stage
              Stage stage = (Stage) root.getScene().getWindow();
              if (stage != null) {
                  stage.getScene().setRoot(root);
              } else {
                  System.err.println("Cannot find the MainPagesClient stage");
              }
          } catch (IOException e) {
              e.printStackTrace();
          }
      });
  }
}
//End of ChatClient class
