package server;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import entities.BiteOptions;
import entities.ClientInfo;
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
		BiteOptions answer= new BiteOptions();
		ArrayList<RestaurantOrders> costumer_all_orders = new ArrayList<RestaurantOrders>();
		ArrayList<RestaurantOrders> Restaurantall_orders_income = new ArrayList<RestaurantOrders>();

		User user;
		MenuItems menuItem;
		Restaurant restaurant;
		
		RestaurantOrders restaurantOrderNew;
		
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
				    if ("ALL".equals(branch)) 
				    {
				        restaurants = DBController.showAllRestaurants();
				    } 
				    else 
				    {
				        restaurants = DBController.showRestaurants(branch);//זה מביא מסעדות לפי מיקום סניף ספציפי
				    }
				    System.out.println("Restaurants fetched from DB: " + restaurants);
				    answer.setData(restaurants.toString());
				    answer.setOption(BiteOptions.Option.SELECT_RESTAURANT);
				    client.sendToClient(answer);
				    System.out.println("Server sent response: " + answer);
				    break;
				    
				    
				    
				    
			   case GET_SELECTED_REST_MENU:
				   
				    System.out.println("Server handling GET_SELECTED_REST_MENU request");

				   
				    restaurant = Restaurant.fromString(request.getData().toString());
			        System.out.println("Eco-Test1: Print a check to see that we were able to convert the string to a user objec: "+restaurant);
			        System.out.println("Eco-Test1: Print audit Extract a field from the instance of the object: "+restaurant.getName());


					ArrayList<MenuItems> menuItems = DBController.getMenuItems();//מושך את כל המשתמשים מהמסד נתונים למערך משתמשים
					System.out.println("MenuItems received from DB: " + menuItems.toString());
				   
					
					//RRRRRRRRRRRRRRRRRRRRRRRRRRRRRR
					
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
						        iterator.remove();  // Safely remove the item using the iterator
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
					//זה שייך למקרה של לוג-אין
					catch (IOException e) 
					{
						e.printStackTrace();
					}
					
					//RRRRRRRRRRRRRRRRRRRRRRRRRRRRRR
					
					
				    
				   
				    break;
  
				    
			   case BACK_HOME_CUSTOMER_PAGE:
				    System.out.println("Eco-Test1: We entered the BACK_HOME_CUSTOMER_PAGE case ");
				    System.out.println("\ntest666666666:  BOB SFOGGGGGGG Message received: " + request.getData() + " from " + client);

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
					
					//aaaaaaaaaaa
				   
				   

				   
				    
				    
				//לאחר לחיצה על כפתור להמשיך לתשלום בחלון של קונפיגורציית אספקה אנחנו רוצים לשמור את ההזמנה של הלקוח בטבלה של הזמנות מסעדה     
			   case CREATE_ORDER:    
				    System.out.println("Eco-Test1: We entered the CREATE_ORDER case ");
			      
				    restaurantOrderNew = RestaurantOrders.fromString(request.getData().toString());//ממיר את המחרוזת למופע של לקוח 
			        System.out.println("Eco-Test CREATE_ORDER: Print a check to see that we were able to convert the string to a user objec: "+restaurantOrderNew);

			        String orderSaved= DBController.insertRestaurantOrder(restaurantOrderNew);
			        System.out.println("Eco-Test CREATE_ORDER: Print QQQQQQQQQQQQQ: "+orderSaved);

				    
				    
				    client.sendToClient("44");

				   
				   break;
				   
				   
				   
				   
			   case GET_USER_ORDERS:
				    System.out.println("Eco-Test1: We entered the GET_USER_ORDERS case ");
				    
				    
				    costumer_all_orders= DBController.getOrdersByUserId((int)request.getData());
			        System.out.println("Eco-Test GET_USER_ORDERS: Print RRRRRR: "+costumer_all_orders);

				    
					answer.setData(costumer_all_orders.toString());
					answer.setOption(BiteOptions.Option.GET_USER_ORDERS);
					client.sendToClient(answer);
					

				   
				   break;
				   
				   
				   
			   case UPDATE_ORDER_STATUS_CUSTOMER:
				   
				    System.out.println("Eco-Test1: We entered the UPDATE_ORDER_STATUS_CUSTOMER case ");

				   
				   // costumer_all_orders= DBController.getOrdersByUserId((int)request.getData());

				    String result = DBController.updateOrderStatusToConfirmed((int)request.getData());
				    System.out.println(result);
				    
				   
				    client.sendToClient("66");

				   
				   break;


				    
				    
				   
			   case TEST_JSON:
				   

				   //AAAAAAAAAAAAAAAAAAA
				   int orderId = 1; // Replace with the order ID you want to retrieve
		            JSONArray orderJsonArray = DBController.getOrderWithJsonField(orderId);
		            if (orderJsonArray != null) 
		            {
		                System.out.println("Order JSON Array: " + orderJsonArray.toString(4));
		                
		                // Example: iterate over the JSON array
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
				    
				    
			   //HAFRADAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA

			   case LOGIN_RESTAURANT:
				   //AAAAAAA
					//Restaurant receivedRestaurant = (Restaurant) request;
					 Restaurant restaurantNoam = new Restaurant();
					 restaurantNoam = Restaurant.fromString(request.getData().toString());
					 //System.out.println(ANSI_BROWN + "Eco-Test:" + ANSI_RESET + "We entered the " + ANSI_BOLD + "LOGIN_RESTAURANT" + ANSI_RESET);

		            System.out.println("Eco-Test: we enterd LOGIN_RESTAURANT" );
		            try
		            {
		            	BiteOptions DBanser= new BiteOptions();
		            	DBanser=DBController.getRestaurantBySupplierId(restaurantNoam.getSupplierID());
		                if (DBanser != null) {
		                	System.out.println("DB returend: "+DBanser);
		                	//restaurantFromDB=(Restaurant) Dbandwer.getData().fromString();
		                   // System.out.println("Restaurant found: " + restaurantFromDB);
		                    client.sendToClient(DBanser);
		                } 
		                else 
		                {
		                    System.out.println("No restaurant found for supplier ID: " + restaurantNoam.getSupplierID());
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

               // Add other cases here as needed...
                   
                   
                   //EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE
                   
               case GET_RESTAURANT_ORDERS:          	   
            	   RestaurantOrders ResOrdNoam = new RestaurantOrders();
            	   ResOrdNoam = RestaurantOrders.fromString(request.getData().toString());
					System.out.println("Eco-Test: We entered GET_RESTAURANT_ORDERS: " + ResOrdNoam );
					BiteOptions returendOrdersNoam = DBController.getOrdersByRestaurantId(ResOrdNoam);
					if (returendOrdersNoam.getData()!=null) {
						System.out.println("Eco-Test: We recived from DB " + returendOrdersNoam );
						client.sendToClient(returendOrdersNoam);

					}
		            break;
		            /////////////noam2
               case CHANGE_ORDER_STATUS:
            	   RestaurantOrders ResstatNoam = new RestaurantOrders();
            	   ResstatNoam = RestaurantOrders.fromString(request.getData().toString());
					System.out.println("Eco-Test: We entered CHANGE_ORDER_STATUS: " + ResstatNoam );
					String returensts = DBController.updateOrderStatus(ResstatNoam);
					System.out.println("Eco-Test: returend from DB: " + returensts );
			
					client.sendToClient("88");

		            break;
		            ////////////////noam1
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
            	   
            	   //EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE
                   
                   
                   
       			
       			////eitannnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn
       			
       			
       			case FETCH_INCOME_REPORTS:
                   
                   //_Restaurantall_orders
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


                   
                   
                   
           }

				   
				   
				   
				   // client.sendToClient("77N");
				   //AAAAAAA
			
			
			
	
			
			
			
		}
		//זה שייך לסוויץ-קייס הראשי
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		
		
		
		
		
		
	/*
		//System.out.println("\ntest2: Message received: " + msg + " from " + client);
		int flagOrederFound = 0;
		int idexMsg = 0;

		
		
		if (msg instanceof List)
		{
			List<?> list = (List<?>) msg;
			if (list.size() == 1)// במסך הראשון אנחנו שולחים רק מספר הזמנה לראות אם זה קיים במסד נתונים
			{
				idexMsg = 0;
				if (!list.isEmpty() && list.get(idexMsg) instanceof String) 
				{
					// Debug statement
					System.out.println("Message is a list of strings");
					// System.out.println("Lang list MSG:"+list.size());
					// Handle order retrieval
					try 
					{
						ArrayList<RestaurantOrders> orders = DBController.showOrder();
						System.out.println("Orders received from DB: " + orders.toString());

						// Try parsing the order number to compare
						try
						{
							// Assuming the first element of the list contains the order number
							int orderNumberToCompare = Integer.parseInt((String) list.get(idexMsg));
							System.out.println("Order number to compare: " + orderNumberToCompare);

							for (RestaurantOrders order : orders) 
							{
								if (order.getOrderNumber() == orderNumberToCompare) 
								{
									System.out.println("SQLnumberOrder: " + order.getOrderNumber());
									System.out.println("Order: " + order);
									client.sendToClient(order.toString());
									flagOrederFound++;
								}

							}

							if (flagOrederFound == 0)
							{
								System.out.println("print flagOrederFound: " + flagOrederFound);

								client.sendToClient("-1");
								System.out.println("Invalid order number format!!!!0: " + list.get(idexMsg));
							}

						} 
						catch (NumberFormatException e)
						{
							System.out.println("Invalid order number format: " + list.get(idexMsg));
						}

						// Send confirmation to the client
						// client.sendToClient("order");// כרגע לא צריך את זה והשרת מחזיר את הרשומה
						// הרלוונטית רק במידה והיא קיימת במסד נתונים
						System.out.println("Sent 'order' to client");

						// Send the actual order list to the client
						// client.sendToClient(orders.toString());
						System.out.println("Sent order list to client");
					} 
					catch (IOException e)
					{
						e.printStackTrace();
					}
					return;
				}
			}
			

		
			
			//זה למקרה והמשתמש רוצה לעדכן שדות בהזמנה קיימת כלומר בדף סיכום הזמנה וחוזר דף אחורה
			else if (list.size() == 5) { // בחלון השני כאשר אנחנו לוחצים על שמירה,אז ההודעה שנשלחת היא באורך 5 איברים
				// לכן מספר ההזמנה במסד נתונים הוא במקום השני במערך הזה
				idexMsg = 1;

				System.out.println("In the case of a message of length 5");

				try {
					if (msg instanceof List) {
						ArrayList<?> arrayList = (ArrayList<?>) msg;

						System.out.println("Print MSG LISTArry" + msg + " " + msg.toString());

						ArrayList<String> orders = (ArrayList<String>) arrayList;

						System.out.println("Print MSG LIST-ORDER" + orders + " " + orders.toString());

						if (!orders.isEmpty()) {
							System.out.println("YESSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS");

							DBController.updateUsersOrderToDB(orders);
							client.sendToClient("The order data has been updated");

						} else {
							System.out.println(
									"Unsupported ArrayList type: " + arrayList.get(idexMsg).getClass().getName());
						}
					} else {
						System.out.println("Unsupported message type: " + msg.getClass().getName());
					}

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	
	*/
	
	
	
	
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