package supplier;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.application.Platform;

import entities.BiteOptions;
import entities.Order;
import entities.Restaurant;
import entities.User;
import client.ClientUI; // Assume this is your client connection class

import java.util.ArrayList;



public class Supplier_OrderManagementController {
	
	final String ANSI_RESET = "\u001B[0m";
	final String ANSI_PURPLE = "\u001B[35m";

    // FXML injected fields
    @FXML
    private TableView<Order> tableView;

    @FXML
    private TableColumn<Order, String> orderNumberColumn;

    @FXML
    private TableColumn<Order, String> typeColumn;

    @FXML
    private TableColumn<Order, String> orderEtaColumn;

    @FXML
    private TableColumn<Order, String> phoneNumberColumn;

    @FXML
    private TableColumn<Order, String> statusColumn;

    @FXML
    private TextField searchField;

    @FXML
    private Button btnSearch;

    @FXML
    private ImageView BiteMeLogo;

    @FXML
    private Button btnBack1;

    @FXML
    private Button btnRefresh;

    // ObservableList to hold the orders data
    private ObservableList<Order> orders = FXCollections.observableArrayList();
    private User supllier;
    private Restaurant restaurant;
    
    public void setSupplier(User userSupplier) {
        this.supllier = userSupplier;
    	System.out.println(ANSI_PURPLE + "Supplier_OrderManagementController" + ANSI_RESET + ": initialized for supplier - " + supllier.getUsername());

    }

    public void setRestaurant(Restaurant restaurant) {
      	 this.restaurant = restaurant;
        // loadOrders();

    	
    }
    @FXML
    public void initialize() {
        // Initialize table columns with cell value factories
        orderNumberColumn.setCellValueFactory(new PropertyValueFactory<>("orderNumber"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("deliveryType"));
        orderEtaColumn.setCellValueFactory(new PropertyValueFactory<>("placingOrderDate"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Set up search button action
       // btnSearch.setOnAction(event -> searchOrder());

        // Set up refresh button action
       // btnRefresh.setOnAction(event -> refreshOrders());

        // Load initial data
    }

    /**
     * Loads orders from the server
     */
    /*
    private void loadOrders() {
        // Create a dummy order to use in the request
        Order dummyOrder = new Order();
        dummyOrder.setRestaurant(restaurant.getName()); // Set the restaurant name
        //why not by id
        
        // Create a request to get orders from the server
        BiteOptions request = new BiteOptions(dummyOrder, BiteOptions.Option.RETRIEVE_MANAGE_ORDER_LIST);

        // Send request to server
        ClientUI.chat.accept(request);

        // The response will be handled in the handleServerResponse method
    }*/

    /**
     * Handles the response from the server
     * @param response The response object from the server
     */
    public void handleServerResponse(Object response) {
        if (response instanceof BiteOptions) {
            BiteOptions biteResponse = (BiteOptions) response;
            if (biteResponse.getOption() == BiteOptions.Option.RETRIEVE_MANAGE_ORDER_LIST) {
                ArrayList<Order> receivedOrders = (ArrayList<Order>) biteResponse.getData();
                // Update UI on JavaFX Application Thread
                Platform.runLater(() -> {
                    orders.clear();
                    orders.addAll(receivedOrders);
                    tableView.setItems(orders);
                });
            }
        }
    }

    /**
     * Searches for orders based on the search term
     */
    
    /*
    private void searchOrder() {
        String searchTerm = searchField.getText().trim();
        if (!searchTerm.isEmpty()) {
            // Filter the orders list based on the search term
            ObservableList<Order> filteredList = orders.filtered(order -> 
                order.getOrderNumber().toString().contains(searchTerm));
            tableView.setItems(filteredList);
        } else {
            // If search field is empty, show all orders
            tableView.setItems(orders);
        }
    }*/

    /**
     * Refreshes the orders list by reloading from the server
     */
    /*
    private void refreshOrders() {
        loadOrders();
    }
    */

    /**
     * Handles the close button action
     * @param event The action event
     */
    @FXML
    private void CloseButton(ActionEvent event) {
        // TODO: Implement close functionality
        System.out.println("Close button clicked");
    }

    /**
     * Handles the back button action
     * @param event The action event
     */
    @FXML
    private void backButton(ActionEvent event) {
        // TODO: Implement back functionality
        System.out.println("Back button clicked");
    }
}
