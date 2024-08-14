package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entities.BiteOptions;
import entities.ClientInfo;
import entities.Customer;
import entities.MenuItem;
import entities.MenuItems;
import entities.RestaurantOrders;
import entities.Restaurant;
import entities.User;

import org.json.JSONArray;
import org.json.JSONObject;


/**
 * DBController class handles all database-related operations such as connecting
 * to the database, retrieving and updating records, and managing database
 * transactions.
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
public class DBController 
{

    protected static Connection conn = null;

    /**
     * Connects to the database using the given password.
     * 
     * @param password The password for the database.
     * @return true if the connection is successful, false otherwise.
     */
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

    
    /**
     * Retrieves all users from the users table.
     * 
     * @return An ArrayList of User objects.
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
                String accountStatus = usersFromTable.getString("account_status");


                System.out.println("Test 4"); 
                users.add(new User(userId, username, password, email, phoneNumber, permission, branch, hasDiscountCode, loggedIn,accountStatus));
            }

            stmt.close();
        } catch (SQLException e) {
            System.out.println("Error returning order details: " + e.getMessage());
        }
        System.out.println("Test 5"); 
        return users;
    }

    
  

    /**
     * Retrieves a list of restaurants associated with a specific branch.
     * 
     * @param branch The branch to filter the restaurants by.
     * @return An ArrayList of Restaurant objects.
     */
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

    
    /**
     * Retrieves a list of all restaurants.
     * 
     * @return An ArrayList of Restaurant objects.
     */
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
    
  
    

    
    
    
    /**
     * Updates the login status of a user.
     * 
     * @param userId      The ID of the user.
     * @param loginStatus The new login status.
     * @return A message indicating success or failure.
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

    
    
    /**
     * Retrieves all menu items from the database.
     * 
     * @return An ArrayList of MenuItems objects.
     */
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

    
    
    
    
    
    

    
    /**
     * Retrieves the order list of a specific order as a JSON array.
     * 
     * @param orderId The ID of the order.
     * @return A JSONArray containing the order details.
     */
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
    
    /**
     * Retrieves a user by their ID.
     * 
     * @param userId The ID of the user.
     * @return A User object if found, null otherwise.
     */
    protected static User getUserById(int userId) {
        System.out.println("Retrieving user with ID: " + userId);
        User user = null;

        String query = "SELECT * FROM users WHERE user_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                user = new User(
                    rs.getInt("user_id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("email"),
                    rs.getString("phone_number"),
                    rs.getString("permission"),
                    rs.getString("branch"),
                    rs.getBoolean("has_discount_code"),
                    rs.getInt("logged_in"),
                    rs.getString("account_status")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving user details: " + e.getMessage());
        }

        return user;
    }
    
    

    
    /**
     * Inserts a new restaurant order into the database.
     * 
     * @param order The RestaurantOrders object containing order details.
     * @return A message indicating success or failure.
     */
    protected static String insertRestaurantOrder(RestaurantOrders order)
    {
        String insertQuery = "INSERT INTO restaurant_orders (restaurant, order_number, total_price, order_list, " +
                "order_address, user_id, restaurant_id, placing_order_date, status, delivery_type, " +
                "order_requested_date, full_name, phone_number, branch, order_received) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(insertQuery)) {
            stmt.setString(1, order.getRestaurant());
            stmt.setInt(2, order.getOrder_number());
            stmt.setDouble(3, order.getTotal_price());
            stmt.setString(4, order.getOrder_list()); // Assuming order_list is stored as a JSON string
            stmt.setString(5, order.getOrder_address());
            stmt.setInt(6, order.getUser_id());
            stmt.setInt(7, order.getRestaurant_id());
            stmt.setString(8, order.getPlacing_order_date()); // Ensure the string is in 'YYYY-MM-DD HH:MM:SS' format
            stmt.setString(9, order.getStatus());
            stmt.setString(10, order.getDelivery_type());
            stmt.setString(11, order.getOrder_requested_date()); // Ensure the string is in 'YYYY-MM-DD HH:MM:SS' format
            stmt.setString(12, order.getFull_name());
            stmt.setString(13, order.getPhone_number());
            stmt.setString(14, order.getBranch());
            stmt.setString(15, order.getOrder_received()); // Ensure the string is in 'YYYY-MM-DD HH:MM:SS' format

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                return "Order inserted successfully.";
            } else {
                return "Failed to insert order.";
            }
        } catch (SQLException e) {
            System.out.println("Error inserting order: " + e.getMessage());
            return "Failed to insert order.";
        }
    }
    
    
    
    
    
    
    /**
     * Retrieves all orders placed by a specific user.
     * 
     * @param userId The ID of the user.
     * @return An ArrayList of RestaurantOrders objects.
     */
    protected static ArrayList<RestaurantOrders> getOrdersByUserId(int userId) {
        ArrayList<RestaurantOrders> ordersList = new ArrayList<>();

        String query = "SELECT restaurant, order_number, total_price, order_list, order_address, user_id, restaurant_id, " +
                       "placing_order_date, status, delivery_type, order_requested_date, full_name, phone_number, branch, " +
                       "order_received FROM restaurant_orders WHERE user_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                RestaurantOrders order = new RestaurantOrders();
                order.setRestaurant(rs.getString("restaurant"));
                order.setOrder_number(rs.getInt("order_number"));
                order.setTotal_price(rs.getDouble("total_price"));
                order.setOrder_list(rs.getString("order_list"));
                order.setOrder_address(rs.getString("order_address"));
                order.setUser_id(rs.getInt("user_id"));
                order.setRestaurant_id(rs.getInt("restaurant_id"));
                order.setPlacing_order_date(rs.getString("placing_order_date"));
                order.setStatus(rs.getString("status"));
                order.setDelivery_type(rs.getString("delivery_type"));
                order.setOrder_requested_date(rs.getString("order_requested_date"));
                order.setFull_name(rs.getString("full_name"));
                order.setPhone_number(rs.getString("phone_number"));
                order.setBranch(rs.getString("branch"));
                order.setOrder_received(rs.getString("order_received"));

                ordersList.add(order);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Error retrieving orders by user ID: " + e.getMessage());
        }

        return ordersList;
    }

    
    
    
    /**
     * Updates the status of an order to "confirmed".
     * 
     * @param orderNumber The number of the order to update.
     * @return A message indicating success or failure.
     */
    protected static String updateOrderStatusToConfirmed(int orderNumber) 
    {
        String updateQuery = "UPDATE restaurant_orders SET status = 'confirmed' WHERE order_number = ?";

        try (PreparedStatement stmt = conn.prepareStatement(updateQuery)) 
        {
            stmt.setInt(1, orderNumber);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
            	
                return "Order status updated to 'confirmed' successfully.";
            } 
            else
            {
                return "No order found with the specified order_number.";
            }
        } 
        catch (SQLException e) 
        {
            System.out.println("Error updating order status: " + e.getMessage());
            return "Failed to update order status.";
        }
    }

    
    
    /**
     * Retrieves a restaurant by its supplier ID.
     * 
     * @param supplierId The supplier ID associated with the restaurant.
     * @return A BiteOptions object containing the restaurant details.
     */
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
    
    
    /**
     * Removes a menu item from the database.
     * 
     * @param itam The MenuItem object to be removed.
     * @return A BiteOptions object indicating the result of the operation.
     */
    public static BiteOptions removeMenuItem(MenuItem itam) {
        System.out.println("Removing menu item : " + itam);
        String sql = "DELETE FROM menuitems WHERE item_id = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, itam.getItemId());
            int rowsAffected = statement.executeUpdate();
            statement.close();

            if (rowsAffected > 0) {
                System.out.println("Menu item " + itam + " removed successfully.");
                return new BiteOptions("success", BiteOptions.Option.DELETE_ITEM_MENU);
            } else {
                System.out.println("No menu item found: " + itam);
                return new BiteOptions("fail", BiteOptions.Option.DELETE_ITEM_MENU);
            }
        } catch (SQLException e) {
            System.out.println("Error removing menu item: " + e.getMessage());
            return new BiteOptions("fail", BiteOptions.Option.DELETE_ITEM_MENU);
        }
    }

    /**
     * Saves or updates a menu item in the database.
     * 
     * @param menuItemOption A BiteOptions object containing the menu item details.
     * @return A BiteOptions object indicating the result of the operation.
     */
    public static BiteOptions saveOrUpdateMenuItem(BiteOptions menuItemOption) {
        MenuItem menuItem = MenuItem.fromString(menuItemOption.getData().toString());
        int restaurantId = menuItem.getRestaurantItamId();
        Integer itemId = menuItem.getItemId(); // Note: itemId can be null or 0 for new items

        try {
            if (itemId == 0) {
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
    

    /**
     * Retrieves all menu items associated with a specific restaurant ID.
     * 
     * @param restaurantId The ID of the restaurant.
     * @return A BiteOptions object containing the menu items.
     * @throws SQLException If there is an error accessing the database.
     */
    public static BiteOptions  getMenuItemsByRestaurantId(int restaurantId) throws SQLException {
        List<MenuItem> menuItems = new ArrayList<>();
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
                System.out.println("getMenuItemsByRestaurantId "+menuItem);

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
    
    
    /**
     * Retrieves a restaurant by its supplier ID.
     * 
     * @param supplierId The supplier ID associated with the restaurant.
     * @return A BiteOptions object containing the restaurant details.
     */
    protected static BiteOptions getRestaurantBySupplierId1(int supplierId) {
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
            } else {
                // No restaurant found, create a new Restaurant object with the message
                restaurant = new Restaurant(0, "Restaurant isn't open", null, null, null, supplierId);
            }

            stmt.close();
        } catch (SQLException e) {
            System.out.println("Error returning restaurant details: " + e.getMessage());
        }
        BiteOptions restaurantfound = new BiteOptions(restaurant.toString(), BiteOptions.Option.LOGIN_RESTAURANT);
        System.out.println("sending BiteOptions from db: " + restaurantfound);
        return restaurantfound;
    }
    
    
    
    /**
     * Retrieves all orders associated with a specific branch.
     * 
     * @param branchName The name of the branch.
     * @return An ArrayList of RestaurantOrders objects.
     */
    protected static ArrayList<RestaurantOrders> getOrdersByBranch(String branchName)
    {
        ArrayList<RestaurantOrders> ordersList = new ArrayList<>();

        String query = "SELECT restaurant, order_number, total_price, order_list, order_address, user_id, restaurant_id, " +
                       "placing_order_date, status, delivery_type, order_requested_date, full_name, phone_number, branch, " +
                       "order_received FROM restaurant_orders WHERE branch = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, branchName);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                RestaurantOrders order = new RestaurantOrders();
                order.setRestaurant(rs.getString("restaurant"));
                order.setOrder_number(rs.getInt("order_number"));
                order.setTotal_price(rs.getDouble("total_price"));
                order.setOrder_list(rs.getString("order_list"));
                order.setOrder_address(rs.getString("order_address"));
                order.setUser_id(rs.getInt("user_id"));
                order.setRestaurant_id(rs.getInt("restaurant_id"));
                order.setPlacing_order_date(rs.getString("placing_order_date"));
                order.setStatus(rs.getString("status"));
                order.setDelivery_type(rs.getString("delivery_type"));
                order.setOrder_requested_date(rs.getString("order_requested_date"));
                order.setFull_name(rs.getString("full_name"));
                order.setPhone_number(rs.getString("phone_number"));
                order.setBranch(rs.getString("branch"));
                order.setOrder_received(rs.getString("order_received"));

                ordersList.add(order);
            }

            rs.close();
        } catch (SQLException e) {
            System.out.println("Error retrieving orders by branch: " + e.getMessage());
        }

        return ordersList;
    }

    
    
    
    
    /**
     * Retrieves all restaurant orders for a given restaurant ID.
     * 
     * @param restaurantOrder The restaurant order containing the restaurant ID.
     * @return A BiteOptions object containing an ArrayList of RestaurantOrders.
     */
    public static BiteOptions getOrdersByRestaurantId(RestaurantOrders restaurantOrder) {
        System.out.println("DBController: Entered getOrdersByRestaurantId method.");
        
        ArrayList<RestaurantOrders> ordersList = new ArrayList<>();
        String query = "SELECT * FROM restaurant_orders WHERE restaurant_id = ?";
        
        System.out.println("DBController: Preparing to execute query to fetch orders for restaurant ID: " + restaurantOrder.getRestaurant_id());

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, restaurantOrder.getRestaurant_id());
            System.out.println("DBController: Query prepared: " + stmt.toString());

            ResultSet rs = stmt.executeQuery();
            System.out.println("DBController: Query executed successfully.");

            while (rs.next()) {
                RestaurantOrders order = new RestaurantOrders();
                order.setOrder_number(rs.getInt("order_number"));
                order.setRestaurant_id(rs.getInt("restaurant_id"));
                order.setTotal_price(rs.getDouble("total_price"));
                order.setOrder_list(rs.getString("order_list"));
                order.setOrder_address(rs.getString("order_address"));
                order.setUser_id(rs.getInt("user_id"));
                order.setPlacing_order_date(rs.getString("placing_order_date"));
                order.setStatus(rs.getString("status"));
                order.setDelivery_type(rs.getString("delivery_type"));
                order.setOrder_requested_date(rs.getString("order_requested_date"));
                order.setFull_name(rs.getString("full_name"));
                order.setPhone_number(rs.getString("phone_number"));
                order.setBranch(rs.getString("branch"));
                order.setOrder_received(rs.getString("order_received"));

                ordersList.add(order);
                System.out.println("DBController: Added order to list: " + order);
            }
            rs.close();
            System.out.println("DBController: Finished processing ResultSet.");
            
            if (ordersList.isEmpty()) {
                System.out.println("DBController: No orders found for restaurant_id: " + restaurantOrder.getRestaurant_id());
                return new BiteOptions(null, BiteOptions.Option.GET_RESTAURANT_ORDERS);
            } else {
                System.out.println("DBController: Found " + ordersList.size() + " orders for restaurant_id: " + restaurantOrder.getRestaurant_id());
            }

        } catch (SQLException e) {
            System.out.println("DBController: Error retrieving restaurant orders: " + e.getMessage());
        }

        // Create a BiteOptions object to return
        BiteOptions result = new BiteOptions(ordersList, BiteOptions.Option.GET_RESTAURANT_ORDERS);
        System.out.println("DBController: Created BiteOptions object with orders list. Returning result.");

        return result;
    }
    
    
    /**
     * Updates the status of a restaurant order.
     * 
     * @param order The RestaurantOrders object containing the order details.
     * @return A message indicating success or failure.
     */
    protected static String updateOrderStatus(RestaurantOrders order) {
        String updateQuery = "UPDATE restaurant_orders SET status = ? WHERE order_number = ?";
        
        System.out.println("Debug: Starting updateOrderStatus method.");
        System.out.println("Debug: Received order object - " + order.toString());

        try (PreparedStatement stmt = conn.prepareStatement(updateQuery)) {
            // Set the status from the RestaurantOrders object
            System.out.println("Debug: Setting status in prepared statement to '" + order.getStatus() + "'.");
            stmt.setString(1, order.getStatus());

            // Set the order_number from the RestaurantOrders object
            System.out.println("Debug: Setting order_number in prepared statement to '" + order.getOrder_number() + "'.");
            stmt.setInt(2, order.getOrder_number());

            // Execute the update
            int affectedRows = stmt.executeUpdate();
            System.out.println("Debug: Executed update statement, affectedRows = " + affectedRows);

            if (affectedRows > 0) {
                String successMessage = "Order status updated to '" + order.getStatus() + "' successfully.";
                System.out.println("Debug: " + successMessage);
                return successMessage;
            } else {
                String noOrderMessage = "No order found with the specified order_number.";
                System.out.println("Debug: " + noOrderMessage);
                return noOrderMessage;
            }
        } catch (SQLException e) {
            String errorMessage = "Error updating order status: " + e.getMessage();
            System.out.println("Debug: " + errorMessage);
            return "Failed to update order status.";
        }
    }
    

    /**
     * Retrieves a list of users with negative values for username and password.
     *
     * @return An ArrayList of User objects containing the retrieved users.
     */
    protected static ArrayList<User> getUsersByNegativeValues() {
        ArrayList<User> usersList = new ArrayList<>();

        String query = "SELECT user_id, username, password, email, phone_number, permission, branch, has_discount_code, logged_in, account_status " +
                       "FROM users WHERE " +
                       "CAST(username AS SIGNED) < 0 AND " +
                       "CAST(password AS SIGNED) < 0";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setPhoneNumber(rs.getString("phone_number"));
                user.setPermission(rs.getString("permission"));
                user.setBranch(rs.getString("branch"));
                user.setHasDiscountCode(rs.getBoolean("has_discount_code"));
                user.setLoggedIn(rs.getInt("logged_in"));
                user.setaccountStatus(rs.getString("account_status"));

                usersList.add(user);
            }

            rs.close();
        } catch (SQLException e) {
            System.out.println("Error retrieving users with negative username and password: " + e.getMessage());
        }

        return usersList;
    }

    
    
    
    /**
     * Updates a user with negative username and password values by their email and returns the updated user ID.
     *
     * @param userToUpdate The User object containing the updated information.
     * @return The user ID of the updated user, or -1 if the update failed.
     */
    protected static int updateUserByEmailWithNegativeUsernamePasswordReturnId(User userToUpdate) {
        int updatedUserId = -1;

        // Check if the email is provided in the user object
        if (userToUpdate.getEmail() == null || userToUpdate.getEmail().isEmpty()) {
            System.out.println("Email is required to update the user.");
            return updatedUserId;
        }

        String updateQuery = "UPDATE users SET username = ?, password = ?, email = ?, phone_number = ?, permission = ?, branch = ?, " +
                             "has_discount_code = ?, logged_in = ?, account_status = ? " +
                             "WHERE CAST(username AS SIGNED) < 0 AND CAST(password AS SIGNED) < 0 AND email = ? LIMIT 1";

        String selectQuery = "SELECT user_id FROM users WHERE CAST(username AS SIGNED) < 0 AND CAST(password AS SIGNED) < 0 AND email = ? LIMIT 1";

        try {
            // Start a transaction
            conn.setAutoCommit(false);

            // First, select the user_id of the record to be updated
            try (PreparedStatement selectStmt = conn.prepareStatement(selectQuery)) {
                selectStmt.setString(1, userToUpdate.getEmail());
                ResultSet rs = selectStmt.executeQuery();

                if (rs.next()) {
                    updatedUserId = rs.getInt("user_id");
                } else {
                    System.out.println("No matching user record found with negative username and password for the provided email.");
                    conn.rollback();
                    return updatedUserId;
                }
            }

            // Then, update the record with the new values
            try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                updateStmt.setString(1, userToUpdate.getUsername());
                updateStmt.setString(2, userToUpdate.getPassword());
                updateStmt.setString(3, userToUpdate.getEmail());
                updateStmt.setString(4, userToUpdate.getPhoneNumber());
                updateStmt.setString(5, userToUpdate.getPermission());
                updateStmt.setString(6, userToUpdate.getBranch());
                updateStmt.setBoolean(7, userToUpdate.isHasDiscountCode());
                updateStmt.setInt(8, userToUpdate.getLoggedIn());
                updateStmt.setString(9, userToUpdate.getaccountStatus());
                updateStmt.setString(10, userToUpdate.getEmail());

                int affectedRows = updateStmt.executeUpdate();

                if (affectedRows > 0) {
                    // Commit the transaction
                    conn.commit();
                    System.out.println("User record updated successfully. User ID: " + updatedUserId);
                } else {
                    updatedUserId = -1;
                    System.out.println("Failed to update the user record.");
                    conn.rollback();
                }
            }

        } catch (SQLException e) {
            System.out.println("Error updating user with negative username and password: " + e.getMessage());
            try {
                conn.rollback();
            } catch (SQLException rollbackEx) {
                System.out.println("Error rolling back transaction: " + rollbackEx.getMessage());
            }
        } finally {
            try {
                // Restore the default commit behavior
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("Error setting auto-commit to true: " + e.getMessage());
            }
        }

        return updatedUserId;
    }

    
    
    
    
    /**
     * Inserts a new customer record into the database.
     *
     * @param customer The Customer object containing the information to be inserted.
     * @return A message indicating success or failure, along with the generated customer ID if successful.
     */
    protected static String insertCustomer(Customer customer) {
        String insertQuery = "INSERT INTO customers (username, password, email, phone_number, branch, account_type, credit_card, " +
                             "address, work_address, has_discount_code, userID) " +
                             "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, customer.getUsername());
            stmt.setString(2, customer.getPassword());
            stmt.setString(3, customer.getEmail());
            stmt.setString(4, customer.getPhoneNumber());
            stmt.setString(5, customer.getBranch());
            stmt.setString(6, customer.getAccountType());
            stmt.setString(7, customer.getCredit_card());
            stmt.setString(8, customer.getAddress());
            stmt.setString(9, customer.getWork_address());
            stmt.setInt(10, customer.getHas_discount_code());
            stmt.setInt(11, customer.getUserID());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                // Get the generated customer_id
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int customerId = generatedKeys.getInt(1);
                        return "Customer inserted successfully with ID: " + customerId;
                    }
                }
            } else {
                return "Failed to insert customer record.";
            }
        } catch (SQLException e) {
            System.out.println("Error inserting customer record: " + e.getMessage());
            return "Failed to insert customer record.";
        }

        return "Customer inserted, but failed to retrieve the generated ID.";
    }

    
    
    
    /**
     * Inserts a new restaurant record into the database.
     *
     * @param restaurant The Restaurant object containing the information to be inserted.
     * @return A message indicating success or failure, along with the generated restaurant ID if successful.
     */
    protected static String insertRestaurant(Restaurant restaurant) {
        String insertQuery = "INSERT INTO restaurants (name, address, phone_number, branch, supplierID) " +
                             "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, restaurant.getName());
            stmt.setString(2, restaurant.getAddress());
            stmt.setString(3, restaurant.getPhoneNumber());
            stmt.setString(4, restaurant.getBranch());
            stmt.setInt(5, restaurant.getSupplierID());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                // Get the generated restaurant_id
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int restaurantId = generatedKeys.getInt(1);
                        return "Restaurant inserted successfully with ID: " + restaurantId;
                    }
                }
            } else {
                return "Failed to insert restaurant record.";
            }
        } catch (SQLException e) {
            System.out.println("Error inserting restaurant record: " + e.getMessage());
            return "Failed to insert restaurant record.";
        }

        return "Restaurant inserted, but failed to retrieve the generated ID.";
    }


    
}