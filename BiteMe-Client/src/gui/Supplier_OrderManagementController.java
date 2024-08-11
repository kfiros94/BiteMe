package gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
import entities.Order;
import entities.Restaurant;
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
    private TableView<Order> tableView;

    @FXML
    private TableColumn<Order, String> orderNumberColumn;

    @FXML
    private TableColumn<Order, String> typeColumn;
    
    @FXML
    private TableColumn<Order, String> itemsColumn;
    
    @FXML
    private TableColumn<Order, String> orderReceivedColumn; //CHANGE is needed

    @FXML
    private TableColumn<Order, String> phoneNumberColumn;

    @FXML
    private TableColumn<Order, String> statusColumn;
    
    //// TEXT AND LABEL ELEMENTS ////////

    @FXML
    private TextField searchField;
    
    @FXML
    private Label msgLabel;
    
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

    

    

    // ObservableList to hold the orders data
    private ObservableList<Order> orders = FXCollections.observableArrayList();
    
    private User cSupplier;
    private Restaurant cRestaurant;
    
    private ArrayList<Order> orderslocal = new ArrayList<Order>();
    
    @FXML
    public void initialize() {
        // Initialize table columns with cell value factories
        orderNumberColumn.setCellValueFactory(new PropertyValueFactory<>("orderNumber"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("deliveryType"));
        //CHANGE is needed
        orderReceivedColumn.setCellValueFactory(new PropertyValueFactory<>("placingOrderDate")); 
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Set up search button action
        btnSearch.setOnAction(event -> handleSearch());

        // Set up refresh button action
        btnRefresh.setOnAction(event -> handleRefresh());

        // Load initial data
        loadOrders();
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
    	
        Order order = new Order();
        //Sets the current Restaurant details inside order for data parsing.
        order.setRestaurantName(cRestaurant.getName());
        order.setRestaurantID(cRestaurant.getRestaurantID());
        // Create a request to get orders from the server
        BiteOptions request = new BiteOptions(order, BiteOptions.Option.RETRIEVE_MANAGE_ORDER_LIST);

        // Send request to server
        ClientUI.chat.accept(request);
        orderslocal.addAll(ChatClient.receivedOrders);
        handleServerResponse(orderslocal);
    }
    
    /**
     * Gets the restaurant's details from the previous page.
     * @param cRestaurant
     * @param cSupplier
     */
    
    
    public void setRestaurantInfo(Restaurant cRestaurant, User cSupplier) {
    	this.cRestaurant = cRestaurant;
    	this.cSupplier = cSupplier;
    }
    
    /**
     * Handles the response from the server
     * @param response The response object from the server
     */
    public void handleServerResponse(ArrayList<Order> response) {
    	
    	if(response.isEmpty()||response == null) {
    		orders.clear();
    		tableView.setItems(orders);
    		System.out.println("No Orders for this Restaurants!");
    	}
    	else {
    		Platform.runLater(() -> {
                orders.clear();
                orders.addAll(response);
                tableView.setItems(orders);
            });
    	}
    	
    }

    /**
     * Searches for orders based on the search term
     */
    @FXML
    private void handleSearch() {
        String searchTerm = searchField.getText().trim();
        if (!searchTerm.isEmpty()) {
            try {
                int searchOrderNumber = Integer.parseInt(searchTerm);
                // Filter the orders list based on the search term
                ObservableList<Order> filteredList = orders.filtered(order -> 
                    order.getOrderNumber() == searchOrderNumber);
                tableView.setItems(filteredList);
            } catch (NumberFormatException e) {
                // Handle the case where the input is not a valid number
                System.out.println("Invalid order number format");
            }
        } else {
            // If search field is empty, show all orders
            tableView.setItems(orders);
            System.out.println("Please write an order number before searching ");
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