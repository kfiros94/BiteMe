
package server;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.BiteOptions;
import entities.ClientInfo;
import entities.MenuItem;
import entities.Restaurant;
import entities.RestaurantOrders;
import entities.User;
//import common.User;
import guiPro.ServerPortFrameControllerPro;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

public class EchoServerPro extends AbstractServer 
{

	public ArrayList<ClientInfo> client_info = new ArrayList<ClientInfo>();
	private ServerPortFrameControllerPro Servercontroller;

	/**
	 * The default port to listen on.
	 */
	final public static int DEFAULT_PORT = 5555;

	/**
	 * Constructor of EchoServerPro
	 *
	 * @param port       the port number to connect on
	 * @param controller the controller for the server GUI
	 */
	public EchoServerPro(int port, ServerPortFrameControllerPro controller) 
	{
		super(port);
		this.Servercontroller = controller;
	}

	/**
	 * This method handles any message received from the client
	 *
	 * @param msg    The message received from the client
	 * @param client The connection from which the message originated
	 */


	
	public void handleMessageFromClient(Object msg, ConnectionToClient client) 
	{
		System.out.println("\ntest2: Message received: " + msg + " from " + client);

		BiteOptions request = (BiteOptions) msg;
		BiteOptions answer = new BiteOptions();
		ArrayList<RestaurantOrders> costumer_all_orders = new ArrayList<RestaurantOrders>();
		User user;
		Restaurant restaurant;
		try
		{
			switch(request.getOption())
			{
			
			case LOGIN:		
				System.out.println("Eco-Test1: We entered the LOGIN case " );

				System.out.println("\ntest3333333: Message received: " + request.getData() + " from " + client);

				//כאן אנחנו לוקחים את המחרוזת בשדה ה-דאטה של מחלקת ביט-אופציות וממירים למופע חדש של המחלקה המתאימה לה לפי שדה הפקודה לוג-אין שבה איתה יחד
		        user = User.fromString(request.getData().toString());
		        System.out.println("Eco-Test1: Print a check to see that we were able to convert the string to a user objec: "+user);
		        System.out.println("Eco-Test1: Print audit Extract a field from the instance of the object: "+user.getUsername());


				ArrayList<User> users = DBController.showusers();//מושך את כל המשתמשים מהמסד נתונים למערך משתמשים
				System.out.println("Users received from DB: " + users.toString());

				String usernameToCompare = user.getUsername();
				String passwordToCompare = user.getPassword();
				
				boolean usernameFound = false;
				try
				{
					
					for (User userToFind : users) 
					{

						if (userToFind.getUsername().equals(usernameToCompare)) 
						{
							usernameFound = true;
							if (userToFind.getPassword().equals(passwordToCompare)) 
							{
								System.out.println("SQLUserFound: " + userToFind.getUsername());
								DBController.updateUserLoginStatus(userToFind.getUserId(), 1); // Update login status to 1
								//client.sendToClient(userToFind.toString());
								
								answer.setData(userToFind.toString());
								answer.setOption(BiteOptions.Option.LOGIN);
								client.sendToClient(answer);
								System.out.println("Eco-Test1- answer To Client in case LOGIN: " + answer);


								return;
							} 
							else 
							{
								//client.sendToClient("-2");
								
								userToFind.setPassword("-2");
								answer.setData(userToFind.toString());
								answer.setOption(BiteOptions.Option.LOGIN);
								client.sendToClient(answer);
								
								
								System.out.println("Incorrect password for username: " + usernameToCompare);
								return;
							}
						}
					}

					if (!usernameFound) 
					{
						System.out.println("Username not found: " + usernameToCompare);
						//client.sendToClient("-1");
						
						answer.setData("-1");
						answer.setOption(BiteOptions.Option.LOGIN);
						client.sendToClient(answer);
						
					}
					
				}
				//זה שייך למקרה של לוג-אין
				catch (IOException e) 
				{
					e.printStackTrace();
				}
				break;


			case LOGOUT:
				System.out.println("Eco-Test1: We entered the LOGOUT case " );
		        user = User.fromString(request.getData().toString());//ממיר את המחרוזת למופע של לקוח 
		        System.out.println("Eco-Test2: Print a check to see that we were able to convert the string to a user objec: "+user);
		        System.out.println("Eco-Test2: Print audit Extract a field from the instance of the object: "+user.getUsername());

		        int userId = user.getUserId();
		        handleLogoutRequest(userId, client);
		        
				break;

			   case SELECT_RESTAURANT:
				    System.out.println("Server handling SELECT_RESTAURANT request");
				    String branch = (String) request.getData();
				    System.out.println("PPPPPPPPPPPPPPPPPPPPPPPPPPPPP" + branch.toString() );

				    ArrayList<Restaurant> restaurants;
				    if ("ALL".equals(branch)) {
				        restaurants = DBController.showAllRestaurants();
				    } else {
				        restaurants = DBController.showRestaurants(branch);
				    }
				    System.out.println("Restaurants fetched from DB: " + restaurants);
				    answer.setData(restaurants.toString());
				    answer.setOption(BiteOptions.Option.SELECT_RESTAURANT);
				    client.sendToClient(answer);
				    System.out.println("Server sent response: " + answer);
				    break;
				    
				/////////////////NOAM////////////////////////////////////////////////////
			   case LOGIN_RESTAURANT:
					//Restaurant receivedRestaurant = (Restaurant) request;
					restaurant = new Restaurant();
					restaurant = Restaurant.fromString(request.getData().toString());
					//System.out.println(ANSI_BROWN + "Eco-Test:" + ANSI_RESET + "We entered the " + ANSI_BOLD + "LOGIN_RESTAURANT" + ANSI_RESET);

		            System.out.println("Eco-Test: we enterd LOGIN_RESTAURANT" );
		            try
		            {
		            	BiteOptions DBanser= new BiteOptions();
		            	DBanser=DBController.getRestaurantBySupplierId(restaurant.getSupplierID());
		                if (DBanser != null) {
		                	System.out.println("DB returend: "+DBanser);
		                	//restaurantFromDB=(Restaurant) Dbandwer.getData().fromString();
		                   // System.out.println("Restaurant found: " + restaurantFromDB);
		                    client.sendToClient(DBanser);
		                } 
		                else 
		                {
		                    System.out.println("No restaurant found for supplier ID: " + restaurant.getSupplierID());
		                    client.sendToClient(null);
		                }
		            }
		            catch (IOException e) {
		                e.printStackTrace();
		            }
		            break;
		            
				case SHOW_MENU_RESTAURANT:
					  System.out.println("Eco-Test: we enterd SHOW_MENU_RESTAURANT" );
					  MenuItem menuItemRequest=MenuItem.fromString(request.getData().toString());
					  ////
					  //MenuItem menuItemRequest = (MenuItem) msg;
			            try {
			                //List<MenuItem> menuItems = DBController.getMenuItemsByRestaurantId(menuItemRequest.getRestaurantItamId());
			                BiteOptions menuItems= DBController.getMenuItemsByRestaurantId(menuItemRequest.getRestaurantItamId());
			                System.out.println("-->: Fetched menu items from database: " + menuItems);
			                client.sendToClient(menuItems);
			                System.out.println("-->: Sent menu items to client");
			            } catch (SQLException e) {
			                e.printStackTrace();
			                System.out.println("-->: Failed to fetch menu items from database");
			            } catch (IOException e) {
			                e.printStackTrace();
			                System.out.println("-->: Failed to send menu items to client");
			            }
		                break;

				case DELETE_ITEM_MENU:
					  MenuItem ItemDeleteRequest=MenuItem.fromString(request.getData().toString());
						System.out.println("Eco-Test: we entered DELETE_ITEM_MENU: "+ItemDeleteRequest);
						/*
					if (request.getData() != null) {
						System.out.println(request.getData());
	                    MenuItem ItemDeleteRequest = MenuItem.fromString(request.getData().toString());
	                    int iddelte=ItemDeleteRequest.getItemId();
	                    */
	                    //System.out.println(iddelte);
	                    BiteOptions ItemDeleted = DBController.removeMenuItem(ItemDeleteRequest);
		                System.out.println("-->: Fetched menu items from database: " + ItemDeleted);
		                client.sendToClient(ItemDeleted);
	                   
		                
	                    /*if (result) {
	                        System.out.println("Menu item removed successfully.");
	                    } else {
	                        System.out.println("Failed to remove menu item.");
	                    }
	                } else {
	                    System.out.println("Error: Received null data for DELETE_ITEM_MENU.");
	                }*/
					
	                break;
	                
				case UPDATE_MENU:
					System.out.println("Eco-Test: we entered UPDATE_MENU: "+request.getData());
					BiteOptions NewOrUpdatedItem =DBController.saveOrUpdateMenuItem(request);
					System.out.println("-->: Fetched menu items from database: " + NewOrUpdatedItem);
	                client.sendToClient(NewOrUpdatedItem);
				
	            ////////////////////NOAM/////////////////////////////////////////////////////////////////
	                
			   case RETRIEVE_MANAGE_ORDER_LIST:
				/*
		         * BiteOptions request = (BiteOptions) msg;
				 * BiteOptions answer = new BiteOptions();
				 */
				System.out.println("Server received RETRIEVE_MANAGE_ORDER_LIST resquest");
				RestaurantOrders order = RestaurantOrders.fromString(request.getData().toString());
				BiteOptions orders = DBController.getOrderManagementInfo(order);
				System.out.println("Order Management fetched from DB");
				answer.setData(orders);
				answer.setOption(BiteOptions.Option.RETRIEVE_MANAGE_ORDER_LIST);
				//Send back to client:
				client.sendToClient(answer);
				System.out.println("Server sent response: " + answer);
				break;
			}
			
			
		}
		//זה שייך לסוויץ-קייס הראשי
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	//////////////////
    /**
     * Handles user logout by updating the logged_in status in the database.
     * 
     * @param userId The ID of the user to log out.
     * @param client The connection to the client that sent the request.
     */
    private void handleLogoutRequest(int userId, ConnectionToClient client) 
    {
    	System.out.println("handleLogoutRequest method for "+userId);
        String result = DBController.updateUserLoginStatus(userId, 0);
        try 
        {
            client.sendToClient(result);
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }
    ////////////////

    
    //1aaaaaaaaaaaaaaaaaaa
    //אין שימוש למתודה הזאת אבל היא תבנית עיצוב להמרה לאובייקטים אולי לעתיד נשתמש בה
	private ArrayList<RestaurantOrders> parsingTheData(ArrayList<?> data) 
	{
		ArrayList<RestaurantOrders> users = new ArrayList<>();
		for (Object obj : data) 
		{
			if (obj instanceof RestaurantOrders) 
			{
				users.add((RestaurantOrders) obj);
			}
		}
		return users;
	}
	//1aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
	
	
	// bbbbbbbbbbbbbbb2

	/**
	 * This method overrides the one in the superclass. Called when the server
	 * starts listening for connections.
	 */
	@Override
	protected void serverStarted() {
		System.out.println("Server listening for connections on port " + getPort());
	}

	/**
	 * This method overrides the one in the superclass. Called when the server stops
	 * listening for connections.
	 */
	@Override
	protected void serverStopped() {
		System.out.println("Server has stopped listening for connections.");
		client_info = new ArrayList<>();
		// Servercontroller.refreshTableView(); // ברגע שהשרת מפסיק לעבוד, צריך לשלוח
		// הודעה לכל הלקוחות שלו
	}

	@Override
	protected void clientConnected(ConnectionToClient client) {
		super.clientConnected(client);
		String clientIP = client.getInetAddress().getHostAddress();
		String status = "Connected";
		Servercontroller.clientConnected(clientIP, status);
	}

	@Override
	synchronized protected void clientDisconnected(ConnectionToClient client) {
		System.out.print(client.toString());
		ClientInfo temp = new ClientInfo(client.getInetAddress().getHostName(),
				client.getInetAddress().getHostAddress(), "connected");
		client_info.remove(temp);
		System.out.print(temp);
		// Servercontroller.refreshTableView(); // ברגע שלקוח מתנתק צריך להוריד אותו
		// מהרשימת לקוחות מחוברים
	}

	public ArrayList<ClientInfo> getClientInfo() {
		return this.client_info;
	}

	// הורדנו את התנאי לא רלוונטי
	// שינתי את המתודה להיות בולאני
	public boolean connectToDataBase(String password) {
		System.out.print("Successfully connected to database ");// הוספתי רווח

		// אם נכשלה ההתחברות אני רוצה לעדכן בקונטרולר של החלון התחבורת בשרת
		return DBController.connectToDB(password);

	}

}
