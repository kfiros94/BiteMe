package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

//import common.User;
import logic.Order;
import logic.ClientInfo;

public class DBController {

    protected static Connection conn = null;

    // Method to connect to the database
    protected static boolean connectToDB(String password) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            System.out.println("Driver setup succeeded");
        } catch (Exception ex) {
            System.out.println("Driver setup failed: " + ex.getMessage());
            return false;
        }

        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/bitemeprototype?serverTimezone=IST", "root", password);
            System.out.println("SQL connection succeeded");
            return true;
        } catch (SQLException ex) {
            System.out.println("SQL connection failed: " + ex.getMessage());
            return false;
        }
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
