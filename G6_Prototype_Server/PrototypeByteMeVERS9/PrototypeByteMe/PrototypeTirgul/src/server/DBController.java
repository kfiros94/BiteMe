package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

//import common.User;
import logic.Order;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;
import logic.ClientInfo;

public class DBController extends AbstractServer{

    protected static Connection conn = null;

    /*here we will define necessary controllers 
     * for the Data-Base
     * example: private DBUserController dbUser=new DBUserController();
*/
    public DBController(int port) {
		super(port);
	}
    
    
    
    // Method to connect to the database
    public static String connectToDB(String ip, String port, String db_name, String db_user, String db_password) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            System.out.println("Driver setup succeeded");
        } catch (Exception ex) {
            System.out.println("Driver setup failed: " + ex.getMessage());
            return "Server Login Failed";
        }

        try {
            conn = DriverManager.getConnection("jdbc:mysql://" + ip + "/" + db_name + "?serverTimezone=IST", db_user, db_password);//changed
            System.out.println("SQL connection succeeded");
            return "SQL connection succeed";
        } catch (SQLException ex) {
            System.out.println("SQL connection failed: " + ex.getMessage());
            return "SQLException: " + ex.getMessage();
        }
    }
    
    public static String disconnectDB()
    {
		try {
			conn.close();
			return "SQL Disconnected Successfuly";
		} catch (Exception e) {
			return "Couldn't disconnect from SQL";
		}
	}
    
    @Override
	/**

	   * This method handles any messages received from the client.
	   *
	   * @param msg The message received from the client.
	   * @param client The connection from which the message originated.
	   * @param 
	   */
	public void handleMessageFromClient(Object msg, ConnectionToClient client)
    {
	    System.out.println("Message received: " + msg + " from " + client); 
	    
	    //check what message was received
    	
    	
    	
    }
    
    
    
    
    
    

    protected static ArrayList<Order> showOrder() {
        System.out.println("in ShowOrder Function"); // TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT
        ArrayList<Order> orders = new ArrayList<>();

        try {
            String query = "SELECT * FROM restaurant_orders"; // Enclose table name in backticks
            java.sql.Statement stmt = conn.createStatement();
            ResultSet ordersFromTable = stmt.executeQuery(query);
            System.out.println("Test 2"); // TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT

            while (ordersFromTable.next()) {
                System.out.println("Test 3"); // TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT
                String restaurantName = ordersFromTable.getString("restaurant");
                int orderNumber = ordersFromTable.getInt("Order_number");
                float totalPrice = ordersFromTable.getFloat("Total_price");
                int orderListNumber = ordersFromTable.getInt("Order_list_number");
                String orderAddress = ordersFromTable.getString("Order_address");
                System.out.println("Test 4"); // TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT
                orders.add(new Order(restaurantName, orderNumber, totalPrice, orderListNumber, orderAddress));
            }
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Error returning order details: " + e.getMessage());
        }
        System.out.println("Test 5"); // TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT
        return orders;
    }

    
    
  
    
    
    
    protected static String updateUsersOrderToDB(ArrayList<String> newEditedOrders) {
        System.out.println("SpongeBob squerpant!!!!!");
        String sql = "UPDATE `restaurant_orders` SET Total_price = ?, Order_address = ? WHERE Order_number = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setFloat(1, Float.parseFloat(newEditedOrders.get(2)));
            statement.setString(2, newEditedOrders.get(4));
            statement.setInt(3, Integer.parseInt(newEditedOrders.get(1)));
            statement.executeUpdate();
            statement.close();
            return "Successfully saved";
        } catch (SQLException var12) {
            return "failed to save";
        }
    }



	
    
    
    
  
    
    
}
