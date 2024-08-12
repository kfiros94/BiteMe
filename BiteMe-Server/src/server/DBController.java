package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Statement;
import entities.BiteOptions;
import entities.ClientInfo;
import entities.MenuItem;
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
    
    /*
    protected static ArrayList<RestaurantOrders> showOrder() {
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
                orders.add(new RestaurantOrders(restaurantName, orderNumber, totalPrice, null, orderAddress, orderListNumber, orderListNumber, orderAddress, orderAddress, orderAddress, orderAddress, null));
            }
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Error returning order details: " + e.getMessage());
        }
        System.out.println("Test 5");
        return orders;
    }*/
    
    /**
     * Gets the specific order management data for Restaurant order management page.
     * @param order
     * @return ArrayList<Order> orders
     */
    protected static BiteOptions getOrderManagementInfo(RestaurantOrders orders) {
        System.out.println("in getOrderManagmentInfo Function"); 
        ArrayList<RestaurantOrders> restaurantOrders = new ArrayList<RestaurantOrders>();
        
        /*String query = "SELECT order_number, order_list, user_id, status, delivery_type, phone_number, order_received" +
                "FROM restaurant_orders" + 
    			"WHERE restaurant_id = ?";*/
        String query = "SELECT *" +
                "FROM restaurant_orders" + 
    			"WHERE restaurant_id = ?";
        try(PreparedStatement stmt = conn.prepareStatement(query)) {
        	
            stmt.setInt(1, orders.getRestaurant_id());
            ResultSet resultSet = stmt.executeQuery();
            System.out.println("getOrderManagment query"); 
            
            while (resultSet.next()) {
            	
            	String restaurant = resultSet.getString("restaurant");
            	int orderNumber = resultSet.getInt("order_number");
            	double total_price = resultSet.getDouble("total_price");
                String orderList = resultSet.getString("order_list");
            	String order_address = resultSet.getString("order_address");
                int userId = resultSet.getInt("user_id");
                int restaurantID = resultSet.getInt("restaurant_id");
                String placing_order_date = resultSet.getString("placing_order_date");
                String status = resultSet.getString("status");
                String deliveryType = resultSet.getString("delivery_type");
                String order_requested_date = resultSet.getString("order_requested_date");
                String full_name = resultSet.getString("full_name");
                String phoneNumber = resultSet.getString("phone_number");
                String branch = resultSet.getString("branch");
                String orderReceived = resultSet.getString("order_received");
                
                // Create a new Order object with the fetched data
                RestaurantOrders order = new RestaurantOrders(restaurant, orderNumber, total_price, orderList, order_address, userId, restaurantID, placing_order_date, status, deliveryType, order_requested_date, full_name, phoneNumber, branch, orderReceived);
                
                // Add the Order object to the orders list
                restaurantOrders.add(order);
                
                // Print the order details (for debugging purposes)
                System.out.println("Fetched Order: " + orders);
            	/*RestaurantOrders tempOrder = new Order();
            	//Inserting the order values from DB to Order class.
            	tempOrder.setOrderNumber(resultSet.getInt("order_number"));
            	tempOrder.setDeliveryType(resultSet.getString("delivery_type"));
            	
                //TODO: ADD ORDER_LIST INFO GETTER!!!!!!!!!!!!!!!!!//
            	
            	tempOrder.setPlacingOrderDate(resultSet.getString("placing_order_date"));
                tempOrder.setStatus(resultSet.getString("status"));
                tempOrder.setPlacingOrderDate(resultSet.getString("phone_number"));
                RestaurantOrders.add(tempOrder); //adding the order to the ArrayList.*/
            }
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Error returning Order Managment List: " + e.getMessage());
        }
        System.out.println("OrderManagment was SUCCESSFULL!!!"); 
        BiteOptions bite = new BiteOptions(restaurantOrders.toString(), BiteOptions.Option.RETRIEVE_MANAGE_ORDER_LIST);
        return bite;
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

    // Method to get restaurant by supplier ID
    protected static BiteOptions getRestaurantBySupplierId(int supplierId) {
        System.out.println("DBController: getRestaurantBySupplierId Function for supplier id: " + supplierId);
        Restaurant restaurant = null;

        try {
            String query = "SELECT * FROM restaurants WHERE supplierID = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, supplierId);
            
            ResultSet rs = stmt.executeQuery(); // Correct method for SELECT queries
            System.out.println("in db in getRestaurantBySupplierId");

            if (rs.next()) {
                System.out.println("Statement found restaurant");

                int restaurantId = rs.getInt("restaurant_id");
                String name = rs.getString("name");
                String address = rs.getString("address");
                String phoneNumber = rs.getString("phone_number");
                String branch = rs.getString("branch");
                int supplierID = rs.getInt("supplierID");

                restaurant = new Restaurant(restaurantId, name, address, phoneNumber, branch, supplierID);
                System.out.println("restaurant: " + restaurant);
            }
            else {
                // No restaurant found, create a new Restaurant object with the message
                restaurant = new Restaurant(0, "Restaurant isn't open", null, null, null, supplierId);
            }

            stmt.close();
        } catch (SQLException e) {
            System.out.println("Error returning restaurant details: " + e.getMessage());
        }
        BiteOptions restaurantfound = new BiteOptions(restaurant.toString(), BiteOptions.Option.LOGIN_RESTAURANT);
        System.out.println("sending BiteOptions from db: "+restaurantfound);
        return restaurantfound;
    }
    
    public static BiteOptions  getMenuItemsByRestaurantId(int restaurantId) throws SQLException {
    	ArrayList<MenuItem> menuItems = new ArrayList<>();
        String query = "SELECT * FROM menuitems WHERE restaurant_id = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, restaurantId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int itemId = rs.getInt("item_id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                float price = rs.getFloat("price");
                String category = rs.getString("category");
                String possibleChanges = rs.getString("possible_changes");

                MenuItem menuItem = new MenuItem(itemId, restaurantId, name, description, price, category, possibleChanges);
                menuItems.add(menuItem);
                System.out.println("in db in getMenuItemsByRestaurantId item: " + menuItem);
            }
            
            // Create BiteOptions object
            BiteOptions menuOptions = new BiteOptions(menuItems, BiteOptions.Option.SHOW_MENU_RESTAURANT);
            System.out.println("BiteOptions created: "+menuOptions);
            return menuOptions;

        } catch (SQLException e) {
            System.out.println("Error retrieving menu items: " + e.getMessage());
            return new BiteOptions(null, BiteOptions.Option.SHOW_MENU_RESTAURANT); // Return an empty list on error
        }
    }
    
    public static BiteOptions removeMenuItem(MenuItem itam ) {//itemDeleteRequest
    	//String str=itemDeleteRequest.getData().toString();
    	//MenuItem itam= MenuItem.fromString(str);
        System.out.println("Removing menu item : " + itam);
        String sql = "DELETE FROM menuitems WHERE item_id = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, itam.getItemId());
            int rowsAffected = statement.executeUpdate();
            statement.close();
            
            if (rowsAffected > 0) {
                System.out.println("Menu item " + itam + " removed successfully.");
                return new BiteOptions("success",BiteOptions.Option.DELETE_ITEM_MENU );
            } else {
                System.out.println("No menu item found: " + itam);
                return new BiteOptions("fail",BiteOptions.Option.DELETE_ITEM_MENU );
            }
        } catch (SQLException e) {
            System.out.println("Error removing menu item: " + e.getMessage());
            return new BiteOptions("fail",BiteOptions.Option.DELETE_ITEM_MENU );
        }
    }
    
    public static BiteOptions saveOrUpdateMenuItem(BiteOptions menuItemOption) {
        MenuItem menuItem = MenuItem.fromString(menuItemOption.getData().toString());
        int restaurantId = menuItem.getRestaurantItamId();
        Integer itemId = menuItem.getItemId(); // Note: itemId can be null or 0 for new items

        try {
            //if (itemId == null || itemId == 0) {
            if ( itemId == 0) {
                // Insert new item, let the database generate the ID
                String insertQuery = "INSERT INTO menuitems (restaurant_id, name, description, price, category, possible_changes) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement insertStmt = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
                insertStmt.setInt(1, restaurantId);
                insertStmt.setString(2, menuItem.getName());
                insertStmt.setString(3, menuItem.getDescription());
                insertStmt.setFloat(4, menuItem.getPrice());
                insertStmt.setString(5, menuItem.getCategory());
                insertStmt.setString(6, menuItem.getPossibleChanges());

                int rowsInserted = insertStmt.executeUpdate();
                
                if (rowsInserted > 0) {
                    // Retrieve the generated item_id
                    ResultSet generatedKeys = insertStmt.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        int generatedId = generatedKeys.getInt(1);
                        menuItem.setItemId(generatedId); // Update the menuItem with the generated ID
                    }
                }
                insertStmt.close();
                
                return rowsInserted > 0 ? new BiteOptions(menuItem.toString(), menuItemOption.getOption()) : new BiteOptions(null, menuItemOption.getOption());
            } else {
                // Update existing item
                String updateQuery = "UPDATE menuitems SET name = ?, description = ?, price = ?, category = ?, possible_changes = ? WHERE restaurant_id = ? AND item_id = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
                updateStmt.setString(1, menuItem.getName());
                updateStmt.setString(2, menuItem.getDescription());
                updateStmt.setFloat(3, menuItem.getPrice());
                updateStmt.setString(4, menuItem.getCategory());
                updateStmt.setString(5, menuItem.getPossibleChanges());
                updateStmt.setInt(6, restaurantId);
                updateStmt.setInt(7, itemId);

                int rowsAffected = updateStmt.executeUpdate();
                updateStmt.close();

                return rowsAffected > 0 ? menuItemOption : new BiteOptions(null, menuItemOption.getOption());
            }
        } catch (SQLException e) {
            System.out.println("Error saving or updating menu item: " + e.getMessage());
            return new BiteOptions(null, menuItemOption.getOption());
        }
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

    
    
  
    
    
}