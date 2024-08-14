package supplier;

import java.io.IOException;
import java.util.ArrayList;
import client.ChatClient;
import client.ClientUI;
import entities.BiteOptions;
import entities.Restaurant;
import entities.RestaurantOrders;
import entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class SupplierOrderManagementController {
	@FXML
	private ImageView BiteMeLogo;
	@FXML
	private Button btnBack;
	@FXML
	private Button btnRefresh;
	@FXML
	private Button btnConfirm;
	@FXML
	private Button btnDeny;
	@FXML
	private Button btnOrderReady;
	@FXML
	private Button btnSearch;
	@FXML
	private TableView<RestaurantOrders> Table;
	@FXML
	private TableColumn<RestaurantOrders, Integer> orderNumberColumn;
	@FXML
	private TableColumn<RestaurantOrders, String> typeColumn;
	@FXML
	private TableColumn<RestaurantOrders, String> itemsColumn;
	@FXML
	private TableColumn<RestaurantOrders, String> orderReceivedColumn;
	@FXML
	private TableColumn<RestaurantOrders, String> phoneNumberColumn;
	@FXML
	private TableColumn<RestaurantOrders, String> statusColumn;
	@FXML
	private Label txtSearch;
	@FXML
	private Label msgError;
	@FXML
	private Label msgConfirm;
	@FXML
	private TextField searchField;
	
	//// Variables //// 

    // ObservableList to hold the orders data
    private ObservableList<RestaurantOrders> resOrders = FXCollections.observableArrayList();
    private User cSupplier;
    private Restaurant cRestaurant;
    private ArrayList<RestaurantOrders> restaurantOrdersLocal = new ArrayList<RestaurantOrders>();
    public static SupplierOrderManagementController instance;
    
    /**
     * 
     */
    @FXML
    public void initialize() {
    	System.out.println("INSIDE -> initialize");
    	instance = this;
        // Initialize table columns with cell value factories
    	orderNumberColumn.setCellValueFactory(new PropertyValueFactory<>("order_number"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("delivery_type"));
        itemsColumn.setCellValueFactory(new PropertyValueFactory<>("order_list")); 
        orderReceivedColumn.setCellValueFactory(new PropertyValueFactory<>("order_received")); 
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phone_number"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        
        // Allow row selection
        Table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }
    
    /**
     * Gets the restaurant's details from the previous page.
     * @param cRestaurant
     * @param cSupplier
     */
    public void initializeTableData(Restaurant cRestaurant, User cSupplier) {
    	System.out.println("INSIDE -> initializeTableData");
    	this.cRestaurant = cRestaurant;
    	this.cSupplier = cSupplier;
    	// Load initial data
        loadOrders();
    }
    
    /**
     * 
     * @return cRestaurant
     */
    public Restaurant getRestaurant() {
    	return cRestaurant;
    }
    
    /**
     * 
     * @return cSupplier
     */
    public User getSupplier() {
    	return cSupplier;
    }
    
    /**
     * Loads orders from the server
     */
    private void loadOrders() {
    	System.out.println("INSIDE -> loadOrders");
    	RestaurantOrders order = new RestaurantOrders();
        //Sets the current Restaurant details (from Login)  inside "order" for data parsing.
        //order.setRestaurant(cRestaurant.getName());
        order.setRestaurant_id(cRestaurant.getRestaurantID());
        // Create a request to get orders from the server
        System.out.println("KAKIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII");
        System.out.println("loadOrders -> " + cRestaurant.getRestaurantID() );
        BiteOptions request = new BiteOptions(order.toString(), BiteOptions.Option.RETRIEVE_MANAGE_ORDER_LIST);
        //BiteOptions request = new BiteOptions(cRestaurant.getRestaurantID(), BiteOptions.Option.RETRIEVE_MANAGE_ORDER_LIST);
        System.out.println("INSIDE -> loadOrders -> accept");
        // Send request to server
        ClientUI.chat.accept(request);
        restaurantOrdersLocal.addAll(ChatClient.receivedOrders);
        //handleServerResponse(restaurantOrdersLocal);
    }
    
    /**
     * 
     * @return instance of SupplierOrderManagementController
     */
    public static SupplierOrderManagementController getController() {
		return instance;
	}
    
    
    /**
     * Handles the response from the server
     * @param answer The response object from the server
     */
    /*public void handleServerResponse(ArrayList<RestaurantOrders> answer) {
    	
    	//ObservableList<RestaurantOrders> orders = FXCollections.observableArrayList();
    	
    	if(answer == null || answer.isEmpty()) {
    		resOrders.clear();
    		Table.setItems(resOrders);
    		msgError.setText("No Orders for this Restaurants!");
    		msgError.setVisible(true);
    		System.out.println("No Orders for this Restaurants!");
    	}
    	else {
    		resOrders.clear();
            for (RestaurantOrders restOrder : answer) {
                // Extracting and adding the necessary fields
                int orderNumber = restOrder.getOrder_number();
                String deliveryType = restOrder.getDelivery_type();
                String items = rebuildOrderList(restOrder.getOrder_list()); // Use the method to rebuild order list
                String orderReceived = restOrder.getOrder_received();
                String phoneNumber = restOrder.getPhone_number();
                String status = restOrder.getStatus();
                RestaurantOrders updatedOrder = new RestaurantOrders(orderNumber, deliveryType, items, orderReceived, phoneNumber, status);
                System.out.println("handleServerResponse -> LOOP -> updatedOrder:" + updatedOrder.toString());
                resOrders.add(updatedOrder);
            }
            Table.setItems(resOrders);
            msgConfirm.setText("Refresh was successfull!");
            msgConfirm.setVisible(true);
        	System.out.println("END OF handleServerResponse" );
    	}
    	
    }*/
    
    /**
     * 
     * @param answer
     */
    public void handleServerResponse(BiteOptions answer) {
    	msgError.setVisible(false);
    	msgConfirm.setVisible(false);
    	ArrayList<RestaurantOrders> resOrdersDB = RestaurantOrders.fromStringArray(answer.getData().toString());
    	System.out.println("handleServerResponse -> loadRestaurantOrders :" + resOrdersDB);
    	ObservableList<RestaurantOrders> observableOrdersList = FXCollections.observableArrayList(resOrdersDB);
        // Step 3: Load the ObservableList into the TableView
    	System.out.println("END OF handleServerResponse" );
    	setOrders(observableOrdersList);
    }
    
    /**
     * 
     * @param orders
     */
    public void setOrders(ObservableList<RestaurantOrders> orders) {
    	msgError.setVisible(false);
    	msgConfirm.setVisible(false);
    	this.resOrders = orders;
        Table.setItems(orders);
        System.out.println("Orders loaded: " + this.resOrders);
        msgConfirm.setText("Data Imported Successfully!");
        msgConfirm.setVisible(true);
    }
    
    /**
     * Method to rebuild order list from JSON-like string to formatted string
     * @param orderListString JSON-like string containing order items
     * @return A formatted string suitable for display in the itemsColumn
     */
    /*private String rebuildOrderList(String orderListString) {
        StringBuilder rebuiltOrderList = new StringBuilder();
        try {
            // Parse the string (assuming the input is a valid JSON array)
            org.json.JSONArray jsonArray = new org.json.JSONArray(orderListString);

            for (int i = 0; i < jsonArray.length(); i++) {
                org.json.JSONObject itemObject = jsonArray.getJSONObject(i);
                String itemName = itemObject.getString("item");
                org.json.JSONArray changesArray = itemObject.getJSONArray("changes");

                List<String> changes = new ArrayList<>();
                for (int j = 0; j < changesArray.length(); j++) {
                    changes.add(changesArray.getString(j));
                }

                rebuiltOrderList.append("Item: ").append(itemName).append(", Changes: ").append(changes).append("\n");
            }
        } catch (org.json.JSONException e) {
            e.printStackTrace();
        }
        System.out.println("rebuildOrderList -> AFTER LOOP -> rebuiltOrderList:" + rebuiltOrderList.toString().trim());
        return rebuiltOrderList.toString().trim(); // Return the formatted string without trailing newline
    }*/
    
    /**
     * Searches for orders based on the search term
     */
    @FXML
    private void handleSearch() {
    	msgError.setVisible(false);
    	msgConfirm.setVisible(false);
    	String searchTerm = searchField.getText().trim();
        if (!searchTerm.isEmpty()) {
            try {
                int searchOrderNumber = Integer.parseInt(searchTerm);
                // Filter the orders list based on the search term
                
                ObservableList<RestaurantOrders> originalList = resOrders;
                ObservableList<RestaurantOrders> filteredList = resOrders.filtered(order -> 
                    order.getOrder_number() == searchOrderNumber);
                if(filteredList.isEmpty()) { //Displays the original list if the search is empty
                	msgError.setVisible(false);
                	msgConfirm.setVisible(false);
                	resOrders.clear();
                    resOrders.addAll(originalList);
                	Table.setItems(resOrders);
                	msgError.setText("No such order exists!");
                	msgError.setVisible(true);
                }
                else { //Displays the filtered list.
                	msgError.setVisible(false);
                	msgConfirm.setVisible(false);
                	resOrders.clear();
                    resOrders.addAll(filteredList);
                	Table.setItems(resOrders);
                	msgConfirm.setText("Order Found!");
                	msgConfirm.setVisible(true);
                }
                
            } catch (NumberFormatException e) {
                // Handle the case where the input is not a valid number
                System.out.println("Invalid order number format");
                msgError.setText("Invalid order number format");
                msgError.setVisible(true);
            }
        } else {
            // If search field is empty, show all orders
            Table.setItems(resOrders);
            msgError.setText("Please write an order number before searching!");
            msgError.setVisible(true);
            System.out.println("Please write an order number before searching!");
        }
    }

    /**
     * Refreshes the orders list by reloading from the server
     * Activates by the push of the "Refresh" Button.
     */
    @FXML
    private void handleRefresh() {
    	msgError.setVisible(false);
    	msgConfirm.setVisible(false);
    	resOrders.clear();
    	loadOrders();
    }
    
    /**
     * Method to handle Confirm button click
     */
    @FXML
    private void handleConfirmedOrder() {
    	msgError.setVisible(false);
    	msgConfirm.setVisible(false);
    	RestaurantOrders selectedOrder = Table.getSelectionModel().getSelectedItem();
        if (selectedOrder != null) {
            selectedOrder.setStatus("preparation");
            Table.refresh();
            msgConfirm.setText("Order status updated to 'preparation'.");
            msgConfirm.setVisible(true);
        } else {
        	msgError.setText("No order was Selected!");
        	msgError.setVisible(true);
        }
    }
    
    
    /**
     *  Method to handle Deny button click
     */
    @FXML
    private void handleDenyOrder() {
    	msgError.setVisible(false);
    	msgConfirm.setVisible(false);
    	RestaurantOrders selectedOrder = Table.getSelectionModel().getSelectedItem();
        
    	if(selectedOrder == null) {
    		msgError.setText("No order was Selected!");
        	msgError.setVisible(true);
        	System.out.println("No order selected.");
    	}
    	else if("confirmed".equalsIgnoreCase(selectedOrder.getStatus())){
    		msgError.setText("You can't deny a confirmed order.");
        	msgError.setVisible(true);
        	System.out.println("Supplier tried to deny a confirmed order.");
    	}
    	else if ("denied".equalsIgnoreCase(selectedOrder.getStatus())) {
    		msgError.setText("You can't deny a denied order.");
        	msgError.setVisible(true);
            System.out.println("Supplier tried to deny a denied order.");
    	}
    	else {
    		msgError.setVisible(false);
            selectedOrder.setStatus("denied");
            changeStatus(selectedOrder);           
            System.out.println("Order status updated to denied for order: " + selectedOrder.getOrder_number());
            int userid=selectedOrder.getUser_id();
            getUserDetails(userid);
        }
    	
    	
    	if (selectedOrder != null) {
            selectedOrder.setStatus("denied");
            Table.refresh();
            msgConfirm.setText("Order status updated to 'denied'.");
            msgConfirm.setVisible(true);
        } else {
        	msgError.setText("No order was Selected!");
        	msgError.setVisible(true);
        }
    }
    
    /**
     * Method to handle Deliver button click
     */
    @FXML
    private void handleDeliverOrder() {
    	msgError.setVisible(false);
    	msgConfirm.setVisible(false);
    	RestaurantOrders selectedOrder = Table.getSelectionModel().getSelectedItem();
        if (selectedOrder != null) {
            selectedOrder.setStatus("delivered");
            Table.refresh();
            msgConfirm.setText("Order status updated to 'delivered'.");
            msgConfirm.setVisible(true);
        } else {
        	msgError.setText("No order was Selected!");
        	msgError.setVisible(true);
        }
    }
    
    /**
     * 
     * @param id
     */
    private void getUserDetails(int id){
    	
    	User user =new User();
    	user.setUserId(id);
    	
    	BiteOptions RequestRestaurantOrds = new BiteOptions(user.toString(),BiteOptions.Option.GET_USER_FOR_NOTIFICATION);
        System.out.println("BiteOptions to send the server: " + RequestRestaurantOrds);

		ClientUI.chat.accept(RequestRestaurantOrds);
    }
    
    /**
     * 
     * @param received
     */
    public void setUserDetails(User received){
    	User userformail = new User();
    	userformail = received;
        System.out.println("settUserDitales: " + userformail);
    }
    
    /**
     * 
     * @param newStatRes
     */
    public void changeStatus(RestaurantOrders newStatRes) {
    	BiteOptions changeresorder = new BiteOptions(newStatRes.toString(),BiteOptions.Option.CHANGE_ORDER_STATUS);
        System.out.println("BiteOptions to send the server: " + changeresorder);
		ClientUI.chat.accept(changeresorder);
		Table.refresh();
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
            MainPageSupplierController controller = loader.getController();
            System.out.println("INSIDE BACK");
            controller.setUserSupplier(getSupplier());
            controller.setRestaurant(getRestaurant());
            // Set the new scene with the Main Page Supplier
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Main Page - Supplier");
            
            // Show the stage
            stage.show();
            System.out.println("INSIDE BACK -> SHOW");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Back button clicked");
    }
}
