package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import entities.ClientInfo;
import entities.Order;
import entities.Restaurant;
import entities.RestaurantOrders;
import entities.User;


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
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/bitemedb?serverTimezone=IST", "root", password);
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
    
 // Method to fetch income reports for a specific branch
    protected static String getIncomeReports(String branch) {
        System.out.println("Fetching detailed income reports for branch: " + branch);
        StringBuilder report = new StringBuilder();
        double totalIncome = 0.0;

        try {
            // Modify the query to group by restaurant and sum the income for each
            String query = "SELECT restaurant, SUM(total_price) AS income FROM restaurant_orders WHERE branch = ? GROUP BY restaurant";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, branch);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String restaurantName = rs.getString("restaurant");
                double income = rs.getDouble("income");
                totalIncome += income;

                // Append the income from each restaurant to the report
                report.append(restaurantName).append(": ").append(income).append("\n");
                
            }

            // Append the total income for the branch to the report
            report.append("Total income for branch ").append(branch).append(": ").append(totalIncome).append("\n");
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Error fetching income reports: " + e.getMessage());
            return "Error fetching income reports.";
        }

        
        return report.toString();
    }

    // Method to fetch order reports for a specific branch
    protected static ArrayList<RestaurantOrders> getOrderReports(String branch) {
        System.out.println("Fetching order reports for branch: " + branch);
        ArrayList<RestaurantOrders> orderReports = new ArrayList<>();

        try {
            String query = "SELECT * FROM restaurant_orders WHERE branch = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, branch);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                RestaurantOrders order = new RestaurantOrders(
                        rs.getString("restaurant"),
                        rs.getInt("order_number"),
                        rs.getDouble("total_price"),
                        rs.getString("order_list"),
                        rs.getString("order_address"),
                        rs.getInt("user_id"),
                        rs.getInt("restaurant_id"),
                        rs.getString("placing_order_date"),
                        rs.getString("status"),
                        rs.getString("delivery_type"),
                        rs.getString("order_requested_date"),
                        rs.getString("full_name"),
                        rs.getString("phone_number"),
                        rs.getString("branch"),
                        rs.getString("order_received")
                );
                orderReports.add(order);
                System.out.println("Added order: " + order);
            }
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Error fetching order reports: " + e.getMessage());
        }

        System.out.println("Finished fetching order reports. Total orders found: " + orderReports.size());
        return orderReports;
    }

    // Method to fetch performance reports for a specific branch
    public static String getPerformanceReports(String branch) {
        StringBuilder performanceReport = new StringBuilder();
        System.out.println("Fetching performance reports for branch: " + branch);

        try {
            String query = "SELECT restaurant, order_number, placing_order_date, order_received FROM restaurant_orders WHERE branch = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, branch);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String restaurant = rs.getString("restaurant");
                int orderNumber = rs.getInt("order_number");
                String placingOrderDate = rs.getString("placing_order_date");
                String orderReceivedDate = rs.getString("order_received");

                // Calculate the difference between order time and received time
                LocalDateTime orderTime = LocalDateTime.parse(placingOrderDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                LocalDateTime receivedTime = LocalDateTime.parse(orderReceivedDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                Duration duration = Duration.between(orderTime, receivedTime);

                String status = duration.toHours() >= 1 ? "Late" : "On-time";

                performanceReport.append("Restaurant: ").append(restaurant)
                                 .append(" - Order Number: ").append(orderNumber)
                                 .append(" - Status: ").append(status)
                                 .append("\n");

                System.out.println("Added order to performance report: " + restaurant + ", Order Number: " + orderNumber + ", Status: " + status);
            }

            stmt.close();
        } catch (SQLException e) {
            System.out.println("Error fetching performance reports: " + e.getMessage());
        }

        return performanceReport.toString();
    }

    
    
  
    
    
}