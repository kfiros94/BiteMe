package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entities.ClientInfo;
import entities.Order;
import entities.User;

public class DBController {

    protected static Connection conn = null;

    /**
     * Establishes a connection to the database using the provided password.
     *
     * @param password the password for the database connection
     * @return true if the connection was successful, false otherwise
     */
    protected static boolean connectToDB(String password) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            System.out.println("Driver setup succeeded");
        } catch (Exception ex) {
            System.out.println("Driver setup failed: " + ex.getMessage());
            return false;
        }

        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/bitemeapp?serverTimezone=IST", "root", password);
            System.out.println("SQL connection succeeded");
            return true;
        } catch (SQLException ex) {
            System.out.println("SQL connection failed: " + ex.getMessage());
            return false;
        }
    }

    /**
     * Retrieves and returns a list of all orders from the database.
     *
     * @return an ArrayList of Order objects representing the orders in the database
     */
    protected static ArrayList<Order> showOrder() {
        System.out.println("in ShowOrder Function"); 
        ArrayList<Order> orders = new ArrayList<>();

        try {
            String query = "SELECT * FROM restaurant_orders"; // Enclose table name in backticks
            java.sql.Statement stmt = conn.createStatement();
            ResultSet ordersFromTable = stmt.executeQuery(query);
            System.out.println("Test 2"); 

            while (ordersFromTable.next()) {
                System.out.println("Test 3"); 
                String restaurantName = ordersFromTable.getString("restaurant");
                int orderNumber = ordersFromTable.getInt("Order_number");
                float totalPrice = ordersFromTable.getFloat("Total_price");
                int orderListNumber = ordersFromTable.getInt("Order_list_number");
                String orderAddress = ordersFromTable.getString("Order_address");
                System.out.println("Test 4"); 
                orders.add(new Order(restaurantName, orderNumber, totalPrice, orderListNumber, orderAddress));
            }
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Error returning order details: " + e.getMessage());
        }
        System.out.println("Test 5"); 
        return orders;
    }
    
    /**
     * Retrieves and returns a list of all users from the database.
     *
     * @return an ArrayList of User objects representing the users in the database
     */
    protected static ArrayList<User> showusers() {
        System.out.println("in ShowOrder Function"); 
        ArrayList<User> users = new ArrayList<>();

        try {
            String query = "SELECT * FROM users"; 
            java.sql.Statement stmt = conn.createStatement();
            ResultSet usersFromTable = stmt.executeQuery(query);
            System.out.println("Test 2"); 

            while (usersFromTable.next()) {
                System.out.println("Test 3"); 

                int userId = usersFromTable.getInt("user_id");
                String username = usersFromTable.getString("username");
                String password = usersFromTable.getString("password");
                String email = usersFromTable.getString("email");
                String phoneNumber = usersFromTable.getString("phone_number");
                String permission = usersFromTable.getString("permission");
                String branch = usersFromTable.getString("branch");
                boolean hasDiscountCode = usersFromTable.getBoolean("has_discount_code");
                int loggedIn = usersFromTable.getInt("logged_in");

                System.out.println("Test 4"); 
                users.add(new User(userId, username, password, email, phoneNumber, permission, branch, hasDiscountCode, loggedIn));
            }

            stmt.close();
        } catch (SQLException e) {
            System.out.println("Error returning order details: " + e.getMessage());
        }
        System.out.println("Test 5");
        return users;
    }    
    
    /**
     * Updates the order details in the database based on the provided new order details.
     *
     * @param newEditedOrders an ArrayList of strings representing the new order details
     * @return a string indicating whether the update was successful or not
     */
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
    
    /**
     * Updates the login status of a user in the database.
     *
     * @param userId      the ID of the user whose login status is to be updated
     * @param loginStatus the new login status of the user (e.g., 1 for logged in, 0 for logged out)
     * @return a string indicating whether the update was successful or not
     */
    protected static String updateUserLoginStatus(int userId, int loginStatus) {	
        System.out.println("Updating user login status");
        String sql = "UPDATE `users` SET logged_in = ? WHERE user_id = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, loginStatus);
            statement.setInt(2, userId);
            statement.executeUpdate();
            statement.close();
            return "Successfully updated login status";
        } catch (SQLException e) {
            System.out.println("Error updating login status: " + e.getMessage());
            return "Failed to update login status";
        }
    }  
}