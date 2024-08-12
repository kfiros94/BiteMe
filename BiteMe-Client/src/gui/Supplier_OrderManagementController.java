package gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.application.Platform;
import entities.BiteOptions;
import entities.MenuItem;
import entities.Restaurant;
import entities.RestaurantOrders;
import entities.User;
import client.ChatClient;
import client.ClientUI; // Assume this is your client connection class

import java.awt.Label;
import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class Supplier_OrderManagementController {
	
	/////CHANGE NOAM//////
	final String ANSI_RESET = "\u001B[0m";
	final String ANSI_PURPLE = "\u001B[35m";
	/////////////////////
    // FXML injected fields
   
	//// TABLE ELEMENTS ////
	@FXML
    private TableView<RestaurantOrders> tableView;

    @FXML
    private TableColumn<RestaurantOrders, String> orderNumberColumn;

    @FXML
    private TableColumn<RestaurantOrders, String> typeColumn;
    
    @FXML
    private TableColumn<RestaurantOrders, String> itemNameColumn;
    
    @FXML
    private TableColumn<RestaurantOrders, String> changesColumn;
    
    @FXML
    private TableColumn<RestaurantOrders, String> receivedTimeColumn; //CHANGE is needed

    @FXML
    private TableColumn<RestaurantOrders, String> phoneNumberColumn;

    @FXML
    private TableColumn<RestaurantOrders, String> statusColumn;
    
    //// TEXT AND LABEL ELEMENTS ////////

    @FXML
    private TextField searchField;
    
    @FXML
    private Label msglabel;
    
    @FXML
    private Label errorlabel;
    
    //// BURRON ELEMENTS ////
    
    @FXML
    private Button btnSearch;
    
    @FXML
    private Button btnBack;
    
    @FXML
    private Button btnRefresh;
    
    @FXML
    private Button btnConfirm;
    
    @FXML
    private Button btnDeny;
    
    @FXML
    private Button btnDeliver;
    
    
    //// OTHER ELEMENTS ////

    @FXML
    private ImageView BiteMeLogo;

    

   //// Variables ////

    // ObservableList to hold the orders data
    private ObservableList<RestaurantOrders> orders = FXCollections.observableArrayList();
    private User cSupplier;
    private Restaurant cRestaurant;
    private ArrayList<RestaurantOrders> restaurantOrdersLocal = new ArrayList<RestaurantOrders>();
    private ArrayList<MenuItem> menuItemsLocal = new ArrayList<MenuItem>();
    
    @FXML
    public void initialize() {
        // Initialize table columns with cell value factories
        orderNumberColumn.setCellValueFactory(new PropertyValueFactory<>("orderNumber"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("deliveryType"));
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        changesColumn.setCellValueFactory(new PropertyValueFactory<>("changes"));
        receivedTimeColumn.setCellValueFactory(new PropertyValueFactory<>("receivedTime")); 
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        
        // Allow row selection
        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        // Set up search button action
        btnSearch.setOnAction(event -> handleSearch());
        // Set up refresh button action
        btnRefresh.setOnAction(event -> handleRefresh());

        // Load initial data
        //loadOrders();
    }
    
    
    public void start(Stage primaryStage) {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/Supplier_OrderManagement.fxml"));
            Parent root = loader.load();

            // Get the controller
            gui.Supplier_OrderManagementController controller = loader.getController();

            // Create the scene
            Scene scene = new Scene(root);

            // Set the scene to the stage
            primaryStage.setScene(scene);
            primaryStage.setTitle("Order Management");

            // Show the stage
            primaryStage.show();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads orders from the server
     */
    private void loadOrders() {
    	
    	RestaurantOrders order = new RestaurantOrders();
        //Sets the current Restaurant details inside order for data parsing.
        order.setRestaurant(cRestaurant.getName());
        order.setRestaurant_id(cRestaurant.getRestaurantID());
        // Create a request to get orders from the server
        BiteOptions request = new BiteOptions(order, BiteOptions.Option.RETRIEVE_MANAGE_ORDER_LIST);

        // Send request to server
        ClientUI.chat.accept(request);
        restaurantOrdersLocal.addAll(ChatClient.receivedOrders);
        handleServerResponse(restaurantOrdersLocal);
    }
    
    /**
     * Gets the restaurant's details from the previous page.
     * @param cRestaurant
     * @param cSupplier
     */
    public void setRestaurantInfo(Restaurant cRestaurant, User cSupplier) {
    	this.cRestaurant = cRestaurant;
    	this.cSupplier = cSupplier;
    	// Load initial data
        loadOrders();
    }
    
    /**
     * Handles the response from the server
     * @param response The response object from the server
     */
    public void handleServerResponse(ArrayList<RestaurantOrders> response) {
    	
    	if(response.isEmpty()||response == null) {
    		orders.clear();
    		tableView.setItems(orders);
    		errorlabel.setText("No Orders for this Restaurants!");
        	errorlabel.setVisible(true);
    		System.out.println("No Orders for this Restaurants!");
    		
    	}
    	else {
            orders.clear();
            orders.addAll(response);
            tableView.setItems(orders);
            msglabel.setText("Refresh was successfull!");
        	msglabel.setVisible(true);
        	System.out.println("Refresh was successfull!");
    	}
    	
    }

    /**
     * Searches for orders based on the search term
     */
    @FXML
    private void handleSearch() {
        String searchTerm = searchField.getText().trim();
        if (!searchTerm.isEmpty() || searchTerm.isBlank() ) {
            try {
                int searchOrderNumber = Integer.parseInt(searchTerm);
                // Filter the orders list based on the search term
                
                ObservableList<RestaurantOrders> originalList = orders;
                ObservableList<RestaurantOrders> filteredList = orders.filtered(order -> 
                    order.getOrder_number() == searchOrderNumber);
                if(filteredList.isEmpty()) { //Displays the original list if the search is empty
                	orders.clear();
                    orders.addAll(originalList);
                	tableView.setItems(orders);
                	errorlabel.setText("No such order exists!");
                	errorlabel.setVisible(true);
                }
                else { //Displays the filtered list.
                	orders.clear();
                    orders.addAll(filteredList);
                	tableView.setItems(orders);
                	msglabel.setText("Order Found!");
                	msglabel.setVisible(true);
                }
                
            } catch (NumberFormatException e) {
                // Handle the case where the input is not a valid number
                System.out.println("Invalid order number format");
            }
        } else {
            // If search field is empty, show all orders
            tableView.setItems(orders);
            errorlabel.setText("Please write an order number before searching!");
        	errorlabel.setVisible(true);
            System.out.println("Please write an order number before searching!");
        }
    }

    /**
     * Refreshes the orders list by reloading from the server
     * Activates by the push of the "Refresh" Button.
     */
    @FXML
    private void handleRefresh() {
        loadOrders();
    }
    
    // Method to handle Confirm button click
    @FXML
    private void handleConfirmedOrder() {
    	RestaurantOrders selectedOrder = tableView.getSelectionModel().getSelectedItem();
        if (selectedOrder != null) {
            selectedOrder.setStatus("preparation");
            tableView.refresh();
            msglabel.setText("Order status updated to 'preparation'.");
            msglabel.setVisible(true);
        } else {
        	errorlabel.setText("No order was Selected!");
            errorlabel.setVisible(true);
        }
    }
    
    // Method to handle Deny button click
    @FXML
    private void handleDenyOrder() {
    	RestaurantOrders selectedOrder = tableView.getSelectionModel().getSelectedItem();
        if (selectedOrder != null) {
            selectedOrder.setStatus("denied");
            tableView.refresh();
            msglabel.setText("Order status updated to 'denied'.");
            msglabel.setVisible(true);
        } else {
        	errorlabel.setText("No order was Selected!");
            errorlabel.setVisible(true);
        }
    }
    
 // Method to handle Deliver button click
    @FXML
    private void handleDeliverOrder() {
    	RestaurantOrders selectedOrder = tableView.getSelectionModel().getSelectedItem();
        if (selectedOrder != null) {
            selectedOrder.setStatus("delivered");
            tableView.refresh();
            msglabel.setText("Order status updated to 'delivered'.");
            msglabel.setVisible(true);
        } else {
        	errorlabel.setText("No order was Selected!");
            errorlabel.setVisible(true);
        }
    }
    
    /**
     * Handles the back button action
     * @param event The action event
     */
    @FXML
    private void handleBack(ActionEvent event) {
        try {
            // Load the FXML file for the Main Page Supplier
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/supplier/MainPageSupplier.fxml"));
            Parent root = loader.load();

            // Get the current stage from the event
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            
            // Set the new scene with the Main Page Supplier
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Main Page - Supplier");

            // Show the stage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Back button clicked");
    }
}