package server;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//import common.User;
import guiPro.ServerPortFrameControllerPro;
import logic.ClientInfo;
import logic.Order;
import logic.User;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

public class EchoServerPro extends AbstractServer {

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
	public EchoServerPro(int port, ServerPortFrameControllerPro controller) {
		super(port);
		this.Servercontroller = controller;
	}

	/**
	 * This method handles any message received from the client
	 *
	 * @param msg    The message received from the client
	 * @param client The connection from which the message originated
	 */
/*
	public void handleMessageFromClient(Object msg, ConnectionToClient client) {
		System.out.println("\ntest2: Message received: " + msg + " from " + client);
		
		int flagOrederFound = 0;
		int idexMsg = 0;

		if (msg instanceof List)
		{
			List<?> list = (List<?>) msg;
			if (list.size() == 1)// במסך הראשון אנחנו שולחים רק מספר הזמנה לראות אם זה קיים במסד נתונים
			{
				idexMsg = 0;
				if (!list.isEmpty() && list.get(idexMsg) instanceof String) {
					// Debug statement
					System.out.println("Message is a list of strings");
					// System.out.println("Lang list MSG:"+list.size());
					// Handle order retrieval
					try {
						ArrayList<Order> orders = DBController.showOrder();
						System.out.println("Orders received from DB: " + orders.toString());

						// Try parsing the order number to compare
						try {
							// Assuming the first element of the list contains the order number
							int orderNumberToCompare = Integer.parseInt((String) list.get(idexMsg));
							System.out.println("Order number to compare: " + orderNumberToCompare);

							for (Order order : orders) {
								if (order.getOrderNumber() == orderNumberToCompare) {
									System.out.println("SQLnumberOrder: " + order.getOrderNumber());
									System.out.println("Order: " + order);
									client.sendToClient(order.toString());
									flagOrederFound++;
								}

							}

							if (flagOrederFound == 0) {
								System.out.println("print flagOrederFound: " + flagOrederFound);

								client.sendToClient("-1");
								System.out.println("Invalid order number format!!!!0: " + list.get(idexMsg));
							}

						} catch (NumberFormatException e) {
							System.out.println("Invalid order number format: " + list.get(idexMsg));
						}

						// Send confirmation to the client
						// client.sendToClient("order");// כרגע לא צריך את זה והשרת מחזיר את הרשומה
						// הרלוונטית רק במידה והיא קיימת במסד נתונים
						System.out.println("Sent 'order' to client");

						// Send the actual order list to the client
						// client.sendToClient(orders.toString());
						System.out.println("Sent order list to client");
					} catch (IOException e) {
						e.printStackTrace();
					}
					return;
			}
			else if (list.size() == 2 && "logoutrequest".equals(list.get(0)))
			{
                System.out.println("logoutrequest list  "+list);///

		        int userId = Integer.parseInt(list.get(1).toString());
		        handleLogoutRequest(userId, client);
		        return;
		    } 
			} 
			else if (list.size() == 2) { // התקבל שם משתמש וסיסמא
				if (!list.isEmpty() && list.get(0) instanceof String && list.get(1) instanceof String) {
					// Debug statement
					System.out.println("Message is a list of strings");
					try {
						ArrayList<User> users = DBController.showusers();
						System.out.println("Users received from DB: " + users.toString());

						String usernameToCompare = (String) list.get(0);
						String passwordToCompare = (String) list.get(1);

						System.out.println("User details to compare: " + usernameToCompare + " " + passwordToCompare);

						boolean usernameFound = false;
						for (User user : users) {
							if (user.getUsername().equals(usernameToCompare)) {
								usernameFound = true;
								if (user.getPassword().equals(passwordToCompare)) {
									System.out.println("SQLUserFound: " + user.getUsername());
									DBController.updateUserLoginStatus(user.getUserId(), 1); // Update login status to 1
									client.sendToClient(user.toString());
									return;
								} else {
									client.sendToClient("-2");
									System.out.println("Incorrect password for username: " + usernameToCompare);
									return;
								}
							}
						}

						if (!usernameFound) {
							System.out.println("Username not found: " + usernameToCompare);
							client.sendToClient("-1");
						}

					} catch (IOException e) {
						e.printStackTrace();
					}
					return;
				}
			} else if (list.size() == 5) { // בחלון השני כאשר אנחנו לוחצים על שמירה,אז ההודעה שנשלחת היא באורך 5 איברים
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
	}
	*/

	
	public void handleMessageFromClient(Object msg, ConnectionToClient client) {
		System.out.println("\ntest2: Message received: " + msg + " from " + client);
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
						ArrayList<Order> orders = DBController.showOrder();
						System.out.println("Orders received from DB: " + orders.toString());

						// Try parsing the order number to compare
						try
						{
							// Assuming the first element of the list contains the order number
							int orderNumberToCompare = Integer.parseInt((String) list.get(idexMsg));
							System.out.println("Order number to compare: " + orderNumberToCompare);

							for (Order order : orders) 
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
			else if (list.size() == 2 && "logoutrequest".equals(list.get(0)))
			{
                System.out.println("logoutrequest list  "+list);///

		        int userId = Integer.parseInt(list.get(1).toString());
		        handleLogoutRequest(userId, client);
		        return;
		    } 
			 
			else if (list.size() == 2) { // התקבל שם משתמש וסיסמא
				if (!list.isEmpty() && list.get(0) instanceof String && list.get(1) instanceof String) {
					// Debug statement
					System.out.println("Message is a list of strings");
					try {
						ArrayList<User> users = DBController.showusers();
						System.out.println("Users received from DB: " + users.toString());

						String usernameToCompare = (String) list.get(0);
						String passwordToCompare = (String) list.get(1);

						System.out.println("User details to compare: " + usernameToCompare + " " + passwordToCompare);

						boolean usernameFound = false;
						for (User user : users) {
							if (user.getUsername().equals(usernameToCompare)) {
								usernameFound = true;
								if (user.getPassword().equals(passwordToCompare)) {
									System.out.println("SQLUserFound: " + user.getUsername());
									DBController.updateUserLoginStatus(user.getUserId(), 1); // Update login status to 1
									client.sendToClient(user.toString());
									return;
								} else {
									client.sendToClient("-2");
									System.out.println("Incorrect password for username: " + usernameToCompare);
									return;
								}
							}
						}

						if (!usernameFound) {
							System.out.println("Username not found: " + usernameToCompare);
							client.sendToClient("-1");
						}

					} catch (IOException e) {
						e.printStackTrace();
					}
					return;
				}
			} else if (list.size() == 5) { // בחלון השני כאשר אנחנו לוחצים על שמירה,אז ההודעה שנשלחת היא באורך 5 איברים
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
	}
//////////////////
    /**
     * Handles user logout by updating the logged_in status in the database.
     * 
     * @param userId The ID of the user to log out.
     * @param client The connection to the client that sent the request.
     */
    private void handleLogoutRequest(int userId, ConnectionToClient client) {
    	System.out.println("handleLogoutRequest method for "+userId);
        String result = DBController.updateUserLoginStatus(userId, 0);
        try {
            client.sendToClient(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    ////////////////

	private ArrayList<Order> parsingTheData(ArrayList<?> data) {
		ArrayList<Order> users = new ArrayList<>();
		for (Object obj : data) {
			if (obj instanceof Order) {
				users.add((Order) obj);
			}
		}
		return users;
	}

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
