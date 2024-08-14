package supplier;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import client.ChatClient;
import client.ClientUI;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.FormatStringConverter;
import entities.*;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Controller class for managing orders for a supplier in the restaurant's management GUI.
 * 
 * @author Kfir Amoyal
 * @author Noam Furman
 * @author Israel Ohayon
 * @author Eitan Zerbel
 * @author Yaniv Shatil
 * @author Omri Heit

 * @version August 2024
 */
public class Supplier_OrderManagementController {
	final String ANSI_RESET = "\u001B[0m";
	final String ANSI_PURPLE = "\u001B[35m";

	
	@FXML
	private Button backButton;
	@FXML
	private Button DenyButton;
	
	@FXML
	private Button deliverButton;
	
	@FXML
	private Button confirmButton;
	
	
	@FXML
	private Label resname;
	
	@FXML
	private Label errorlabel;
	
	
	@FXML
    private TableView<RestaurantOrders> orderTable;

    @FXML
    private TableColumn<RestaurantOrders, Integer> orderNumberColumn;

    @FXML
    private TableColumn<RestaurantOrders, Double> totalPriceColumn;

    @FXML
    private TableColumn<RestaurantOrders, String> orderListColumn;

    @FXML
    private TableColumn<RestaurantOrders, String> orderAddressColumn;

    @FXML
    private TableColumn<RestaurantOrders, Integer> userIdColumn;

    @FXML
    private TableColumn<RestaurantOrders, String> placingOrderDateColumn;

    @FXML
    private TableColumn<RestaurantOrders, String> statusColumn;

    @FXML
    private TableColumn<RestaurantOrders, String> deliveryTypeColumn;

    private User supplier;
    private User UserNotification;
    private static Integer currentUserId;
    private Restaurant restaurant;
    public static Supplier_OrderManagementController instance;
    private ObservableList<RestaurantOrders> resOrders;

    /**
     * Initializes the controller, setting up the table columns for displaying order data.
     */
    @FXML
    private void initialize() {
    	
    	instance=this;
    	
    	// Initialize table columns
        orderNumberColumn.setCellValueFactory(new PropertyValueFactory<>("order_number"));
        totalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("total_price"));
        orderListColumn.setCellValueFactory(new PropertyValueFactory<>("order_list"));
        orderAddressColumn.setCellValueFactory(new PropertyValueFactory<>("order_address"));
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("user_id"));
        placingOrderDateColumn.setCellValueFactory(new PropertyValueFactory<>("placing_order_date"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        deliveryTypeColumn.setCellValueFactory(new PropertyValueFactory<>("delivery_type"));

        orderTable.setEditable(true);
    }
    
   
    /**
     * Sets the supplier user for this controller.
     *
     * @param userSupplier The supplier user.
     */
    public void setSupplier(User userSupplier) {
        this.supplier = userSupplier;
    	System.out.println(ANSI_PURPLE + "Supplier_OrderManagementController" + ANSI_RESET + ": initialized for supplier - " + supplier.getUsername());

    }

    /**
     * Sets the restaurant for this controller and loads its orders.
     *
     * @param restaurant The restaurant entity.
     */
    public void setRestaurant(Restaurant restaurant) {
      	 this.restaurant = restaurant;
      	  resname.setText(restaurant.getName());
     	
		loadRestaurantOrders();
 	
    }
    
    /**
     * Gets the instance of this controller.
     *
     * @return The current instance of Supplier_OrderManagementController.
     */
    public static Supplier_OrderManagementController getController() {
		return instance;
	}

   
    /**
     * Sends a request to the server to get the orders for the restaurant.
     */
    public void loadRestaurantOrders() {
    	RestaurantOrders resordsRequest = new RestaurantOrders();
    	resordsRequest.setRestaurant_id(restaurant.getRestaurantID());

		BiteOptions RequestRestaurantOrds = new BiteOptions(resordsRequest.toString(),BiteOptions.Option.GET_RESTAURANT_ORDERS);
        System.out.println("BiteOptions to send the server: " + RequestRestaurantOrds);

		ClientUI.chat.accept(RequestRestaurantOrds);
   	
    }
    
    /**
     * Loads the restaurant orders into the table after receiving them from the server.
     *
     * @param returendOrdersNoam The response containing the orders from the server.
     */
    public void loadRestaurantOrders(BiteOptions returendOrdersNoam) {

    	ArrayList<RestaurantOrders> orderListFromDB =RestaurantOrders.fromStringArray(returendOrdersNoam.getData().toString());

    	System.out.println("loadRestaurantOrders :"+orderListFromDB);
        // Step 2: Convert the list to an ObservableList
        ObservableList<RestaurantOrders> observableOrdersList = FXCollections.observableArrayList(orderListFromDB);

        // Step 3: Load the ObservableList into the TableView
        setOrders(observableOrdersList);
    }
    
    /**
     * Sets the list of restaurant orders and updates the order table.
     * 
     * <p>This method takes an ObservableList of RestaurantOrders, assigns it to the
     * resOrders field, and then sets this list as the items for the orderTable.
     * After updating, it prints a confirmation message to the console.</p>
     *
     * @param orders An ObservableList of RestaurantOrders to be set and displayed.
     *               This list will replace any existing orders in the table.
     */
    public void setOrders(ObservableList<RestaurantOrders> orders) {
        this.resOrders = orders;
        orderTable.setItems(orders);
        System.out.println("Orders loaded: " + this.resOrders);
    }


    
    
    /**
     * Handles the back button action
     * 
     * @param event The action event
     */
    @FXML
    private void handleBack(ActionEvent event) {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/supplier/MainPageSupplier.fxml"));
            Parent root = loader.load();

            // Get the controller for the main page
            MainPageSupplierController mainPageController = loader.getController();
            mainPageController.setUserSupplier(supplier);
            mainPageController.setRestaurant(restaurant);

            // Set the scene and show the main page
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Supplier Main Page - BiteMe");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Handles the action of the deny button, denying the selected order.
     *
     * @param event The action event.
     */ 
    @FXML
    private void handleDeny(ActionEvent event) {
        RestaurantOrders selectedOrder = orderTable.getSelectionModel().getSelectedItem();

        if (selectedOrder == null) {
            errorlabel.setText("No Order selected");
            errorlabel.setVisible(true);
            System.out.println("No order selected.");
        } else {
            if ("delivered".equalsIgnoreCase(selectedOrder.getStatus())) {
                errorlabel.setText("You can't deny a delivered order.");
                errorlabel.setVisible(true);
                System.out.println("Supplier tried to deny a delivered order.");
            } else if ("denied".equalsIgnoreCase(selectedOrder.getStatus())) {
                errorlabel.setText("You can't deny a denied order.");
                errorlabel.setVisible(true);
                System.out.println("Supplier tried to deny a denied order.");
            } else {
                errorlabel.setVisible(false);
                selectedOrder.setStatus("denied");
                changeStatus(selectedOrder);           
                System.out.println("Order status updated to denied for order: " + selectedOrder.getOrder_number());
                
                // Extract and save the user_id
                currentUserId = selectedOrder.getUser_id();
                
                // Audit printout
                System.out.println("AUDIT: Denied order for userID: " + currentUserId);
                
                // Send email notification
                sendEmail(currentUserId, "Order Denied", "We're sorry, but your order has been denied.");
                
                // Get user details for notification
                getUserDetails(currentUserId);
            }
        }
    }
    /**
     * Handles the action of the confirm button, confirming the selected order.
     *
     * @param event The action event.
     */
    @FXML
    private void handleConfirm(ActionEvent event) {
        RestaurantOrders selectedOrder = orderTable.getSelectionModel().getSelectedItem();

        if (selectedOrder == null) {
            errorlabel.setText("No Order selected");
            errorlabel.setVisible(true);
            System.out.println("No order selected.");
        } else {
            if ("pending".equalsIgnoreCase(selectedOrder.getStatus())) {
                errorlabel.setVisible(false);
                System.out.println("Supplier tries to confirm an order.");
                selectedOrder.setStatus("preparation");
                changeStatus(selectedOrder);
                
                // Extract and save the user_id
                currentUserId = selectedOrder.getUser_id();
                
                // Audit printout
                System.out.println("AUDIT: Confirmed order for userID: " + currentUserId);
                
                // Send email notification
                sendEmail(currentUserId, "Order Confirmed", "Your order has been confirmed.");
                
                // Get user details for notification
                getUserDetails(currentUserId);
            } else {
                errorlabel.setText("Only pending orders can be confirmed");
                errorlabel.setVisible(true);
                System.out.println("Only pending orders can be confirmed: " + selectedOrder.getOrder_number());
            }
        }
    }
    /**
     * Handles the action of the deliver button, marking the selected order as delivered.
     *
     * @param event The action event.
     */
    @FXML
    private void handleDeliver(ActionEvent event) {
        RestaurantOrders selectedOrder = orderTable.getSelectionModel().getSelectedItem();

        if (selectedOrder == null) {
            errorlabel.setText("No Order selected");
            errorlabel.setVisible(true);
            System.out.println("No order selected.");
        } else {
            if ("preparation".equalsIgnoreCase(selectedOrder.getStatus())) {
                System.out.println("Supplier tries to deliver an order.");
                selectedOrder.setStatus("delivered");
                changeStatus(selectedOrder);
                
                // Extract and save the user_id
                currentUserId = selectedOrder.getUser_id();
                
                // Audit printout
                System.out.println("AUDIT: Delivered order for userID: " + currentUserId);
                
                // Send email notification
                sendEmail(currentUserId, "Order Delivered", "Your order has been delivered.");
                
                // Get user details for notification
                getUserDetails(currentUserId);
            } else {
                errorlabel.setText("Only confirmed orders can be delivered");
                errorlabel.setVisible(true);
                System.out.println("Only confirmed orders can be delivered: " + selectedOrder.getOrder_number());
            }
        }
    }

    
    /**
     * Sends a request to the server to get the user details for the notification.
     *
     * @param userId The ID of the user.
     */
    private void getUserDetails(int userId) {
        User user = new User();
        user.setUserId(userId);
        
        BiteOptions RequestUserDetails = new BiteOptions(user.toString(), BiteOptions.Option.GET_USER_FOR_NOTIFICATION);
        System.out.println("BiteOptions to send the server: " + RequestUserDetails);

        ClientUI.chat.accept(RequestUserDetails);
    }
    

    /**
     * Sets the user details and simulates sending a message to the user.
     *
     * @param receivedUser The user details received from the server.
     */
    public void settUserDitales(User receivedUser) {
        if (receivedUser != null) {
            String userEmail = receivedUser.getEmail();
            System.out.println("setUserDetails: User email received: " + userEmail);

            // Use Platform.runLater to ensure UI updates occur on the JavaFX Application Thread
            Platform.runLater(() -> {
                simulateMessageSending(userEmail, "Order Status Update", "Your order status has been updated.");
            });
        }
    }

    /**
    * Simulates sending a message by showing an alert with the details.
    *
    * @param recipient The recipient's email address.
    * @param subject   The subject of the message.
    * @param message   The content of the message.
    */
    public void simulateMessageSending(String recipient, String subject, String message) {
        // Create an alert to simulate the message sending
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Simulation");
        alert.setHeaderText("Simulated Message Sending");
        alert.setContentText("Recipient: " + recipient + "\nSubject: " + subject + "\nMessage: " + message);

        // Show the alert
        alert.showAndWait();
    }
    
   
    	
    
    /**
     * Sends a request to the server to change the status of an order.
     *
     * @param newStatRes The order with the new status.
     */
    public void changeStatus(RestaurantOrders newStatRes) {
    	
    	BiteOptions changeresorder = new BiteOptions(newStatRes.toString(),BiteOptions.Option.CHANGE_ORDER_STATUS);
        System.out.println("BiteOptions to send the server: " + changeresorder);
		ClientUI.chat.accept(changeresorder);
	    orderTable.refresh();

    	
    	
    
    }
    /**
     * Gets the current user ID.
     *
     * @return The ID of the current user.
     */
    public static Integer getCurrentUserId() {
        return currentUserId;
    }



    
    /**
     * Simulates sending an email to the user with the specified subject and body.
     *
     * @param userId  The ID of the user.
     * @param subject The subject of the email.
     * @param body    The body of the email.
     */
    private void sendEmail(int userId, String subject, String body) {
        // In a real application, you would retrieve the user's email address from the database
        String recipientEmail = "user" + userId + "@example.com"; // Placeholder email
        
        // Simulating email sending without actual network connection
        System.out.println("Simulating email send:");
        System.out.println("To: " + recipientEmail);
        System.out.println("Subject: " + subject);
        System.out.println("Body: " + body);
        
        // In a real application, you would use a proper email library or service here
        
        System.out.println("Email sent successfully to user " + userId);
        
        // Show a simulation alert
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Email Sent Simulation");
            alert.setHeaderText("Email Notification Simulated");
            alert.setContentText("An email would have been sent to " + recipientEmail + "\nSubject: " + subject + "\nBody: " + body);
            alert.showAndWait();
        });
    }
    
    /**
     * Handles the refresh action for restaurant orders.
     * 
     * <p>This method is triggered by a user action, typically clicking a refresh button.
     * If a restaurant is currently selected (not null), it reloads the restaurant orders
     * by calling the loadRestaurantOrders method.</p>
     *
     * @param event The ActionEvent object representing the user's action that triggered this method.
     *              This parameter is not used in the current implementation but is required for FXML.
     */
    @FXML
    private void handleRefresh(ActionEvent event) {
    	if (restaurant!=null) {
    		loadRestaurantOrders();
    	}
    }

    
    
}
