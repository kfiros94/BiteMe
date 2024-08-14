package server;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import entities.BiteOptions;
import entities.ClientInfo;
import entities.Customer;
import entities.MenuItem;
import entities.MenuItems;
import entities.RestaurantOrders;
import entities.Restaurant;
import entities.User;
//import common.User;
import guiPro.ServerPortFrameControllerPro;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * The EchoServerPro class is responsible for handling client requests and server operations.
 * It extends AbstractServer and listens for incoming connections and messages from clients.
 * This class processes various requests related to user authentication, restaurant management, orders,
 * and reports, interacting with the database to perform these operations.
 * 
 * @author Kfir Amoyal
 * @author Israel Ohayon
 * @author Yaniv Shatil
 * @author Noam Furman
 * @author Omri Heit
 * @author Eitan Zerbel
 * 
 * @version August 2024
 */
public class EchoServerPro extends AbstractServer 
{

	public ArrayList<ClientInfo> client_info = new ArrayList<ClientInfo>();// List of client information
	private ServerPortFrameControllerPro Servercontroller;// Reference to the server's GUI controller

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
		BiteOptions answer= new BiteOptions();
		ArrayList<RestaurantOrders> costumer_all_orders = new ArrayList<RestaurantOrders>();
		ArrayList<RestaurantOrders> Restaurantall_orders_income = new ArrayList<RestaurantOrders>();

		User user;
		MenuItems menuItem;
		Restaurant restaurant;
		
		RestaurantOrders restaurantOrderNew;
		User userEmpty;
	    User UpdateUserByEmail;
	    
		Restaurant NewRestaurant;

		Customer NewCustomer;

		
		ArrayList<User> userEmptyArry = new ArrayList<User>();


		
		try
		{
			switch(request.getOption())
			{
			
			case LOGIN:		
				System.out.println("Eco-Test1: We entered the LOGIN case " );

				System.out.println("\ntest3333333: Message received: " + request.getData() + " from " + client);

				// Convert the data string to a User object
		        user = User.fromString(request.getData().toString());
		        System.out.println("Eco-Test1: Print a check to see that we were able to convert the string to a user objec: "+user);
		        System.out.println("Eco-Test1: Print audit Extract a field from the instance of the object: "+user.getUsername());


				ArrayList<User> users = DBController.showusers();// Retrieve all users from the database
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
								
								answer.setData(userToFind.toString());
								answer.setOption(BiteOptions.Option.LOGIN);
								client.sendToClient(answer);
								System.out.println("Eco-Test1- answer To Client in case LOGIN: " + answer);


								return;
							} 
							else 
							{								
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
						answer.setData("-1");
						answer.setOption(BiteOptions.Option.LOGIN);
						client.sendToClient(answer);
						
					}
					
				}
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
				    if ("ALL".equals(branch)) 
				    {
				        restaurants = DBController.showAllRestaurants();
				    } 
				    else 
				    {
				        restaurants = DBController.showRestaurants(branch);// Retrieve restaurants based on the branch

				    }
				    System.out.println("Restaurants fetched from DB: " + restaurants);
				    // Send the list of restaurants to the client
				    answer.setData(restaurants.toString());
				    answer.setOption(BiteOptions.Option.SELECT_RESTAURANT);
				    client.sendToClient(answer);
				    System.out.println("Server sent response: " + answer);
				    break;
				    
				    
				    
				    
			   case GET_SELECTED_REST_MENU:
				   
				    System.out.println("Server handling GET_SELECTED_REST_MENU request");
				    // Convert the data string to a Restaurant object
				    restaurant = Restaurant.fromString(request.getData().toString());
			        System.out.println("Eco-Test1: Print a check to see that we were able to convert the string to a user objec: "+restaurant);
			        System.out.println("Eco-Test1: Print audit Extract a field from the instance of the object: "+restaurant.getName());


					ArrayList<MenuItems> menuItems = DBController.getMenuItems();//Safely remove the item using the iterator
					System.out.println("MenuItems received from DB: " + menuItems.toString());
				   
					
					
					boolean MenuItemsnameFound = false;
					try
					{
						
						Iterator<MenuItems> iterator = menuItems.iterator();
						while (iterator.hasNext()) 
						{
						    MenuItems menuItemToFind = iterator.next();
						    if (menuItemToFind.getRestaurant_id() == restaurant.getRestaurantID()) 
						    {
						        MenuItemsnameFound = true;
						    } 
						    else 
						    {
						        iterator.remove();  
						    }
						}

						
						answer.setData(menuItems.toString());
						answer.setOption(BiteOptions.Option.GET_SELECTED_REST_MENU);
						client.sendToClient(answer);
						
						
						

						if (!MenuItemsnameFound) 
						{
							System.out.println("MenuItems not found For Restaurant: " + restaurant.getName());
							//client.sendToClient("-1");
							
							answer.setData("not found items");
							answer.setOption(BiteOptions.Option.GET_SELECTED_REST_MENU);
							client.sendToClient(answer);
							
						}
						
					}
					
					catch (IOException e) 
					{
						e.printStackTrace();
					}
										
					
				    
				   
				    break;
  
				    
			   case BACK_HOME_CUSTOMER_PAGE:
				    System.out.println("Eco-Test1: We entered the BACK_HOME_CUSTOMER_PAGE case ");
				    System.out.println("\ntest666666666:  BOB SFOGGGGGGG Message received: " + request.getData() + " from " + client);

				    // Retrieve the user by ID and send back to the client
				    userId = Integer.parseInt(request.getData().toString());
				    user = DBController.getUserById(userId);

				    if (user != null) {
				        answer.setData(user.toString());
				        answer.setOption(BiteOptions.Option.BACK_HOME_CUSTOMER_PAGE);
				        client.sendToClient(answer);
				        System.out.println("Eco-Test1- answer To Client in case BACK_HOME_CUSTOMER_PAGE: " + answer);
				    } else {
				        answer.setData("-1");
				        answer.setOption(BiteOptions.Option.BACK_HOME_CUSTOMER_PAGE);
				        client.sendToClient(answer);
				        System.out.println("User not found for ID: " + userId);
				    }
				    break;
 
				     
			   case CREATE_ORDER:    
				    System.out.println("Eco-Test1: We entered the CREATE_ORDER case ");
			      
				    restaurantOrderNew = RestaurantOrders.fromString(request.getData().toString());//Convert the data string to a RestaurantOrders object  
			        System.out.println("Eco-Test CREATE_ORDER: Print a check to see that we were able to convert the string to a user objec: "+restaurantOrderNew);
			        // Insert the new order into the database
			        String orderSaved= DBController.insertRestaurantOrder(restaurantOrderNew);
			        System.out.println("Eco-Test CREATE_ORDER: Print QQQQQQQQQQQQQ: "+orderSaved);

				    
				    
				    client.sendToClient("44");

				   
				   break;
				   
				   
				   
				   
			   case GET_USER_ORDERS:
				    System.out.println("Eco-Test1: We entered the GET_USER_ORDERS case ");
				    
				    // Retrieve orders by user ID and send to the client
				    costumer_all_orders= DBController.getOrdersByUserId((int)request.getData());
			        System.out.println("Eco-Test GET_USER_ORDERS: Print RRRRRR: "+costumer_all_orders);

				    
					answer.setData(costumer_all_orders.toString());
					answer.setOption(BiteOptions.Option.GET_USER_ORDERS);
					client.sendToClient(answer);
					

				   
				   break;
				   
				   
				   
			   case UPDATE_ORDER_STATUS_CUSTOMER:
				   
				    System.out.println("Eco-Test1: We entered the UPDATE_ORDER_STATUS_CUSTOMER case ");

				   
				    // Update the order status in the database
				    String result = DBController.updateOrderStatusToConfirmed((int)request.getData());
				    System.out.println(result);
				    
				   
				    client.sendToClient("66");

				   
				   break;


				    
				    
				   
			   case TEST_JSON:
				   

				   int orderId = 1; // Replace with the order ID you want to retrieve
		            JSONArray orderJsonArray = DBController.getOrderWithJsonField(orderId);
		            if (orderJsonArray != null) 
		            {
		                System.out.println("Order JSON Array: " + orderJsonArray.toString(4));
		                
		                for (int i = 0; i < orderJsonArray.length(); i++) 
		                {
		                    JSONObject item = orderJsonArray.getJSONObject(i);
		                    int itemId = item.getInt("item_id");
		                    int quantity = item.getInt("quantity");
		                    JSONArray changes = item.getJSONArray("changes");
		                    System.out.println("Item ID: " + itemId);
		                    System.out.println("Quantity: " + quantity);
		                    System.out.println("Changes: " + changes.toString());
				   
		                }
		            }

				   
				    break;
				    
				    
			   case LOGIN_RESTAURANT:
					 Restaurant restaurantSupplierLogin = new Restaurant();
					 restaurantSupplierLogin = Restaurant.fromString(request.getData().toString());

		            System.out.println("Eco-Test: we enterd LOGIN_RESTAURANT" );
		            try
		            {
		            	BiteOptions DBanser= new BiteOptions();
		            	DBanser=DBController.getRestaurantBySupplierId(restaurantSupplierLogin.getSupplierID());
		                if (DBanser != null) {
		                	System.out.println("DB returend: "+DBanser);

		                    client.sendToClient(DBanser);
		                } 
		                else 
		                {
		                    System.out.println("No restaurant found for supplier ID: " + restaurantSupplierLogin.getSupplierID());
		                    client.sendToClient(null);
		                }
		            }
		            catch (IOException e) {
		                e.printStackTrace();
		            }
		            break;
		            
			   case SHOW_MENU_RESTAURANT:
                   System.out.println("Eco-Test: we entered SHOW_MENU_RESTAURANT");
                   MenuItem menuItemRequest = MenuItem.fromString(request.getData().toString());
                   try {
                       BiteOptions menuItemsOption = DBController.getMenuItemsByRestaurantId(menuItemRequest.getRestaurantItamId());
                       System.out.println("-->: Fetched menu items from database: " + menuItemsOption);
                       client.sendToClient(menuItemsOption);
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
                   MenuItem itemDeleteRequest = MenuItem.fromString(request.getData().toString());
                   System.out.println("Eco-Test: we entered DELETE_ITEM_MENU: " + itemDeleteRequest);

                   BiteOptions itemDeleted = DBController.removeMenuItem(itemDeleteRequest);
                   System.out.println("-->: Fetched menu items from database: " + itemDeleted);
                   client.sendToClient(itemDeleted);
                   break;

               case UPDATE_MENU:
                   System.out.println("Eco-Test: we entered UPDATE_MENU: " + request.getData());
                   BiteOptions newOrUpdatedItem = DBController.saveOrUpdateMenuItem(request);
                   System.out.println("-->: Fetched menu items from database: " + newOrUpdatedItem);
                   client.sendToClient(newOrUpdatedItem);
                   break;                   
                   
                   
               case GET_RESTAURANT_ORDERS:          	   
            	   RestaurantOrders ResOrdNoam = new RestaurantOrders();// Convert the data string to a RestaurantOrders object
            	   ResOrdNoam = RestaurantOrders.fromString(request.getData().toString());
					System.out.println("Eco-Test: We entered GET_RESTAURANT_ORDERS: " + ResOrdNoam );
					BiteOptions returendOrdersNoam = DBController.getOrdersByRestaurantId(ResOrdNoam);// Fetch orders for the restaurant from the database and send to the client
					if (returendOrdersNoam.getData()!=null) {
						System.out.println("Eco-Test: We recived from DB " + returendOrdersNoam );
						client.sendToClient(returendOrdersNoam);

					}
		            break;
               case CHANGE_ORDER_STATUS:
            	   RestaurantOrders ResstatNoam = new RestaurantOrders();
            	   ResstatNoam = RestaurantOrders.fromString(request.getData().toString());
					System.out.println("Eco-Test: We entered CHANGE_ORDER_STATUS: " + ResstatNoam );
					String returensts = DBController.updateOrderStatus(ResstatNoam);
					System.out.println("Eco-Test: returend from DB: " + returensts );
			
					client.sendToClient("88");

		            break;
               case GET_USER_FOR_NOTIFICATION:
            	   User getusernoam =new User();
            	   getusernoam=User.fromString(request.getData().toString());
            	   int getusernoamid =getusernoam.getUserId();
					System.out.println("Eco-Test: We entered GET_USER_FOR_NOTIFICATION: " + getusernoamid );

	                User returenusernoam =DBController.getUserById(getusernoamid);
					System.out.println("Eco-Test: returend from DB: " + returenusernoam );
					
					BiteOptions biteusernoam= new BiteOptions(returenusernoam.toString(),BiteOptions.Option.GET_USER_FOR_NOTIFICATION);
					client.sendToClient(biteusernoam);
		            break;
            	   

       			
       			case FETCH_INCOME_REPORTS:
                   
                    System.out.println("Eco-Test: we entered FETCH_INCOME_REPORTS: " + request.getData().toString());
       				
       				
                    Restaurantall_orders_income= DBController.getOrdersByBranch(request.getData().toString());
			        System.out.println("Eco-Test FETCH_INCOME_REPORTS: Print RRRRRR: "+Restaurantall_orders_income);

				    
					answer.setData(Restaurantall_orders_income.toString());
					answer.setOption(BiteOptions.Option.FETCH_INCOME_REPORTS);
					client.sendToClient(answer);
                   
                   

                    break;
                    
                    
                    
       			case FETCH_ORDER_REPORTS:
                    System.out.println("Eco-Test: we entered FETCH_ORDER_REPORTS: " + request.getData().toString());

                    
                    Restaurantall_orders_income= DBController.getOrdersByBranch(request.getData().toString());
			        System.out.println("Eco-Test FETCH_ORDER_REPORTS: Print KYYYYYYY: "+Restaurantall_orders_income);

				    
					answer.setData(Restaurantall_orders_income.toString());
					answer.setOption(BiteOptions.Option.FETCH_ORDER_REPORTS);
					client.sendToClient(answer);
                    
       				

       				
                    break;

       				
                    
       			case FETCH_PERFORMENCE_REPORTS:

                    System.out.println("Eco-Test: we entered FETCH_PERFORMENCE_REPORTS: " + request.getData().toString());

                    
                    Restaurantall_orders_income= DBController.getOrdersByBranch(request.getData().toString());
			        System.out.println("Eco-Test FETCH_ORDER_REPORTS: Print tRRRRRR: "+Restaurantall_orders_income);

				    
					answer.setData(Restaurantall_orders_income.toString());
					answer.setOption(BiteOptions.Option.FETCH_PERFORMENCE_REPORTS);
					client.sendToClient(answer);
                    
       				

       				
                    break;
                    
                    
       			case CREATE_USER:
                    System.out.println("Eco-Test: we entered CREATE_USER: " + request.getData().toString());

                    userEmpty = User.fromString(request.getData().toString());//ממיר את המחרוזת למופע של לקוח 
    		        System.out.println("Eco-Test CREATE_USER: Print a check to see that we were able to convert the string to a user objec: "+userEmpty);
    		        System.out.println("Eco-Test CREATE_USER: Print audit Extract a field from the instance of the object: "+userEmpty.getUsername());

    		        
                    userEmptyArry = DBController.getUsersByNegativeValues();
					System.out.println("Eco-Test CREATE_USER: returend from DB: " + userEmptyArry.toString() );

                    
					answer.setData(userEmptyArry.toString());
					answer.setOption(BiteOptions.Option.CREATE_USER);
					client.sendToClient(answer);
					


                    break;

                    
                    
       			case CLASIFY_UPDATE_USER_BY_EMAIL:
                    System.out.println("Eco-Test: we entered CLASIFY_UPDATE_USER_BY_EMAIL: " + request.getData().toString());

       				
                    UpdateUserByEmail = User.fromString(request.getData().toString());//ממיר את המחרוזת למופע של לקוח 
    		        System.out.println("Eco-Test CREATE_USER: Print a check to see that we were able to convert the string to a user objec: "+UpdateUserByEmail);
                    
                    
    		        
                    int UpdateNewUsersuc = DBController.updateUserByEmailWithNegativeUsernamePasswordReturnId(UpdateUserByEmail);
                    
                    
					answer.setData(UpdateNewUsersuc);
					answer.setOption(BiteOptions.Option.CLASIFY_UPDATE_USER_BY_EMAIL);
					client.sendToClient(answer);
       				
       				
                    break;

                    
                    
                    
       			case UPDATE_CUSTOMER:
                    System.out.println("Eco-Test: we entered UPDATE_CUSTOMER: " + request.getData().toString());

       				
                    NewCustomer = Customer.fromString(request.getData().toString());//ממיר את המחרוזת למופע של לקוח 
    		        System.out.println("Eco-Test UPDATE_CUSTOMER: Print a check to see that we were able to convert the string to a user objec: "+NewCustomer);
                    
                    
    		        
    		        String UpdateNewCustomersuc=DBController.insertCustomer(NewCustomer);
    		        
    		        
					answer.setData(UpdateNewCustomersuc);
					answer.setOption(BiteOptions.Option.UPDATE_CUSTOMER);
					client.sendToClient(answer);
       				

                    break;

                    
       				
       			case UPDATE_RESTAURANT:
       				
                    System.out.println("Eco-Test: we entered UPDATE_RESTAURANT: " + request.getData().toString());

       				
                    NewRestaurant = Restaurant.fromString(request.getData().toString());//ממיר את המחרוזת למופע של לקוח 
    		        System.out.println("Eco-Test UPDATE_RESTAURANT: Print a check to see that we were able to convert the string to a user objec: "+NewRestaurant);
                    
    		        
    		        String UpdateNewRestsuc=DBController.insertRestaurant(NewRestaurant);
                    
					answer.setData(UpdateNewRestsuc);
					answer.setOption(BiteOptions.Option.UPDATE_RESTAURANT);
					client.sendToClient(answer);
       				 				
       				
       				
       				
                    break;

                    
                    
                   
                   
           }

		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	
	}
	
	
	
	
	
	
	

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
	}

    /**
     * This method overrides the one in the superclass. 
     * Called when the server stops listening for connections.
     */
	@Override
	protected void clientConnected(ConnectionToClient client) {
		super.clientConnected(client);
		String clientIP = client.getInetAddress().getHostAddress();
		String status = "Connected";
		Servercontroller.clientConnected(clientIP, status);
	}

    /**
     * Called when a client disconnects from the server.
     * 
     * @param client The connection to the client that disconnected.
     */
	@Override
	synchronized protected void clientDisconnected(ConnectionToClient client) {
		System.out.print(client.toString());
		ClientInfo temp = new ClientInfo(client.getInetAddress().getHostName(),
				client.getInetAddress().getHostAddress(), "connected");
		client_info.remove(temp);
		System.out.print(temp);
	}
    /**
     * Returns the list of client information.
     * 
     * @return The list of client information.
     */
	public ArrayList<ClientInfo> getClientInfo() {
		return this.client_info;
	}

    /**
     * Connects to the database with the given password.
     * 
     * @param password The password to connect to the database.
     * @return True if the connection was successful, false otherwise.
     */
	public boolean connectToDataBase(String password) {
		System.out.print("Successfully connected to database ");

		return DBController.connectToDB(password);

	}

}