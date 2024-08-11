package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entities.ClientInfo;
import entities.MenuItems;
import entities.RestaurantOrders;
import entities.Restaurant;
import entities.User;

import org.json.JSONArray;
import org.json.JSONObject;





public class DBController 
{

    protected static Connection conn = null;

    // Method to connect to the database
    protected static boolean connectToDB(String password) 
    {
        try 
        {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            System.out.println("Driver setup succeeded");
        } 
        catch (Exception ex) 
        {
            System.out.println("Driver setup failed: " + ex.getMessage());
            return false;
        }

        try 
        {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/bitemedb?serverTimezone=IST", "root", password);
            System.out.println("SQL connection succeeded");
            return true;
        } 
        catch (SQLException ex) 
        {
            System.out.println("SQL connection failed: " + ex.getMessage());
            return false;
        }
    }

    
    
    /*
    protected static ArrayList<RestaurantOrders> showOrder() 
    {
        System.out.println("in ShowOrder Function"); // TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT
        ArrayList<RestaurantOrders> orders = new ArrayList<>();

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
                orders.add(new RestaurantOrders(restaurantName, orderNumber, totalPrice, orderListNumber, orderAddress));
            }
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Error returning order details: " + e.getMessage());
        }
        System.out.println("Test 5"); // TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT
        return orders;
    }
    */
    
    
    
    /*
    
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
    */
    
    
    
    
    
    //return users table
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
                String accountStatus = usersFromTable.getString("account_status");


                System.out.println("Test 4"); // TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT
                users.add(new User(userId, username, password, email, phoneNumber, permission, branch, hasDiscountCode, loggedIn,accountStatus));
            }

            stmt.close();
        } catch (SQLException e) {
            System.out.println("Error returning order details: " + e.getMessage());
        }
        System.out.println("Test 5"); // TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT
        return users;
    }

    
    
    
    
    
    //KKKKKKKKKKKKKKKKKKKKKK

    // New method to return restaurants table
    protected static ArrayList<Restaurant> showRestaurants(String branch) 
    {
        System.out.println("in ShowRestaurants Function for branch: " + branch);
        ArrayList<Restaurant> restaurants = new ArrayList<>();

        try {
            String query = "SELECT * FROM restaurants WHERE branch = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, branch);
            ResultSet rs = stmt.executeQuery();
            System.out.println("Query executed successfully");

            while (rs.next()) {
                System.out.println("Processing restaurant record");
                int restaurantID = rs.getInt("restaurant_id");
                String name = rs.getString("name");
                String address = rs.getString("address");
                String phoneNumber = rs.getString("phone_number");
                int supplierID = rs.getInt("supplierID");

                // Add the restaurant to the list
                restaurants.add(new Restaurant(restaurantID, name, address, phoneNumber, branch, supplierID));
            }
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Error retrieving restaurant details: " + e.getMessage());
        }
        System.out.println("Returning list of restaurants");
        return restaurants;
    }

    
    //KKKKKKKKKKKKKKKKKKKK
    
    protected static ArrayList<Restaurant> showAllRestaurants() {
        System.out.println("in ShowAllRestaurants Function");
        ArrayList<Restaurant> restaurants = new ArrayList<>();

        try {
            String query = "SELECT * FROM restaurants";
            java.sql.Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            System.out.println("Query executed successfully");

            while (rs.next()) {
                System.out.println("Processing restaurant record");
                int restaurantID = rs.getInt("restaurant_id");
                String name = rs.getString("name");
                String address = rs.getString("address");
                String phoneNumber = rs.getString("phone_number");
                String branch = rs.getString("branch");
                int supplierID = rs.getInt("supplierID");

                restaurants.add(new Restaurant(restaurantID, name, address, phoneNumber, branch, supplierID));
            }
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Error retrieving restaurant details: " + e.getMessage());
        }
        System.out.println("Returning list of all restaurants");
        return restaurants;
    }
    
  
    

    
    
    
    //method to update in the data base when user is logd-in or out
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

    
    
    
    //LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL
    protected static ArrayList<MenuItems> getMenuItems() 
    {
        System.out.println("Retrieving all menu items from the database");
        ArrayList<MenuItems> menuItemsList = new ArrayList<>();

        String query = "SELECT item_id, restaurant_id, name, description, price, category, possible_changes FROM menuitems";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int itemId = rs.getInt("item_id");
                int restaurantId = rs.getInt("restaurant_id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                double price = rs.getDouble("price");
                String category = rs.getString("category");

                // Parsing the JSON field 'possible_changes' into an ArrayList<String>
                String possibleChangesJson = rs.getString("possible_changes");
                ArrayList<String> possibleChangesList = new ArrayList<>();
                JSONArray jsonArray = new JSONArray(possibleChangesJson);
                for (int i = 0; i < jsonArray.length(); i++) {
                    possibleChangesList.add(jsonArray.getString(i));
                }

                // Creating a MenuItems object and adding it to the list
                MenuItems menuItem = new MenuItems(itemId, restaurantId, name, description, price, category, possibleChangesList);
                menuItemsList.add(menuItem);
            }

            stmt.close();
        } catch (SQLException e) {
            System.out.println("Error retrieving menu items: " + e.getMessage());
        }

        return menuItemsList;
    }

    
    //LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL
    
    
    
    
    

    
    // Method to retrieve a specific order with a JSON field
    protected static JSONArray getOrderWithJsonField(int orderId)
    {
        JSONArray orderDetails = null;

        String query = "SELECT order_list FROM restaurant_orders WHERE Order_number = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) 
        {
            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) 
            {
                String jsonOrderList = rs.getString("order_list");
                orderDetails = new JSONArray(jsonOrderList); // Parse the JSON array
            }

            stmt.close();
        } 
        catch (SQLException e) 
        {
            System.out.println("Error retrieving order details: " + e.getMessage());
        }

        return orderDetails;
    }
    

    
    
    
    
  
    
    
}