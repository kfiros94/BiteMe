package gui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import client.ChatClient;
import client.ClientUI;
import entities.BiteOptions;
import entities.RestaurantOrders;
/**
 * Controller class for handling the Order In Progress screen.
 * This class manages the TableView that displays the list of restaurant orders
 * and provides functionality to confirm delivery, refresh the order list,
 * and navigate back to the previous screen.
 * 
 * @author Kfir Amoyal
 * @author Noam Furman
 * @author Israel Ohayon
 * @author Eitan Zerbel
 * @author Yaniv Shatil
 * @author Omri Heit

 * @version August 2024
 */
public class OrderInProgressController {

    @FXML
    private TableView<RestaurantOrders> orderlist;

    @FXML
    private TableColumn<RestaurantOrders, String> Restaurant;

    @FXML
    private TableColumn<RestaurantOrders, String> orderList;

    @FXML
    private TableColumn<RestaurantOrders, String> orderAddress;

    @FXML
    private TableColumn<RestaurantOrders, String> CreationDate;

    @FXML
    private TableColumn<RestaurantOrders, Double> TotalPrice;

    @FXML
    private TableColumn<RestaurantOrders, String> DeliveryType;

    @FXML
    private TableColumn<RestaurantOrders, String> Status;

    @FXML
    private Button confirmDeliveryButton;

    @FXML
    private Button refreshButton;

    @FXML
    private Button backButton;

    private ObservableList<RestaurantOrders> orders;
    /**
     * Initializes the TableView columns with the appropriate properties from the RestaurantOrders entity.
     */
    @FXML
    private void initialize()
    {
        Restaurant.setCellValueFactory(new PropertyValueFactory<>("restaurant"));
        orderList.setCellValueFactory(new PropertyValueFactory<>("order_list"));
        orderAddress.setCellValueFactory(new PropertyValueFactory<>("order_address"));
        CreationDate.setCellValueFactory(new PropertyValueFactory<>("placing_order_date"));
        TotalPrice.setCellValueFactory(new PropertyValueFactory<>("total_price"));
        DeliveryType.setCellValueFactory(new PropertyValueFactory<>("delivery_type"));
        Status.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    

    /**
     * Handles the action of confirming the delivery of a selected order.
     * Displays various alerts depending on the status of the selected order.
     * 
     * @param event the action event triggered by the confirm delivery button
     */
    @FXML
    private void handleConfirmDelivery(ActionEvent event)
    {
        RestaurantOrders selectedOrder = orderlist.getSelectionModel().getSelectedItem();
        if (selectedOrder != null)
        {
            if (selectedOrder.getStatus().equals("delivered")) 
            {
                // Update the status of the selected order to 'Delivered'
                selectedOrder.setStatus("confirmed");
                orderlist.refresh(); // Refresh the TableView to reflect the status change
                
                System.out.println("The Order number is: " + selectedOrder.getOrder_number());
                BiteOptions option = new BiteOptions(selectedOrder.getOrder_number(), BiteOptions.Option.UPDATE_ORDER_STATUS_CUSTOMER);
                ClientUI.chat.accept(option);
                
             // Check if the order qualifies for a coupon
                LocalDateTime placingDateTime = LocalDateTime.parse(selectedOrder.getPlacing_order_date(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                LocalDateTime currentDateTime = LocalDateTime.now();

                long hoursDifference = ChronoUnit.HOURS.between(placingDateTime, currentDateTime);
                boolean qualifiesForCoupon = false;

                if (hoursDifference >= 1 || currentDateTime.toLocalDate().isAfter(placingDateTime.toLocalDate())) {
                    qualifiesForCoupon = true;
                }

                if (qualifiesForCoupon) {
                    System.out.println("Order qualifies for a coupon.");
                    // Update the user's discount count in the database
                    BiteOptions discountOption = new BiteOptions(ChatClient.user1.getUserId(), BiteOptions.Option.INCREMENT_DISCOUNT_COUNT);
                    ClientUI.chat.accept(discountOption);

                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Coupon Earned!");
                    alert.setHeaderText(null);
                    alert.setContentText("Sorry for the delay! You've earned a coupon for your next order.");
                    alert.showAndWait();
                }
            } 
            
            else if (selectedOrder.getStatus().equals("confirmed"))
            {
                // Show pop-up window for orders that are not delivered yet
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Order Status");
                alert.setHeaderText(null);
                alert.setContentText("The order is already confirmed");
                alert.showAndWait();
            }
            
            else if (selectedOrder.getStatus().equals("denied"))
            {
                // Show pop-up window for orders that are not delivered yet
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Order Status");
                alert.setHeaderText(null);
                alert.setContentText("The order is canceled by Restaurant");
                alert.showAndWait();
            }
            
            else if (selectedOrder.getStatus().equals("preparation"))
            {
                // Show pop-up window for orders that are not delivered yet
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Order Status");
                alert.setHeaderText(null);
                alert.setContentText("The order is being prepared at the restaurant, please wait");
                alert.showAndWait();
            }
            
            
            
            else
            {
                // Show pop-up window for orders that are not delivered yet
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Order Status");
                alert.setHeaderText(null);
                alert.setContentText("The restaurant has not yet confirmed receipt of your order");
                alert.showAndWait();
            }
        } 
        else
        {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.setContentText("Please select an order from the list.");
            alert.showAndWait();
        }
    }
    
    

    /**
     * Handles the action of refreshing the list of orders.
     * Fetches the latest orders from the server and updates the TableView.
     * 
     * @param event the action event triggered by the refresh button
     */
    @FXML
    private void handleRefresh(ActionEvent event)
    {
        // Implement the logic to refresh the list of orders
        // to fetch the latest data from the database
   
		BiteOptions option = new BiteOptions(ChatClient.user1.getUserId(), BiteOptions.Option.GET_USER_ORDERS);//kkkkkkk
	    ClientUI.chat.accept(option);
	    
        if (orderlist != null && orderlist.getItems() != null) 
        {
            orderlist.getItems().clear();
        }
	    
    	ChatClient.observableOrdersList.addAll(0, ChatClient.customer_all_orders1);
    	this.setOrders(ChatClient.observableOrdersList);
	    
	    
    
    
    
    }

    
    /**
     * Handles the action of navigating back to the previous screen.
     * Loads the main page client interface and hides the current window.
     * 
     * @param event the action event triggered by the back button
     */
    @FXML
    private void handleBack(ActionEvent event) 
    {

        // Logic for back button
        System.out.println("Back button clicked");

        FXMLLoader loader = new FXMLLoader();
        Pane root = null;
        Stage primaryStage = new Stage();
        try {
            // Load the FXML file
            loader.setLocation(getClass().getResource("/gui/MainPagesClient.fxml"));

            root = loader.load();
            
            // Hide the current window
            ((Node) event.getSource()).getScene().getWindow().hide();

            // Set the new stage
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
			primaryStage.setTitle("User-Portal");

            
           System.out.println("The user is:  " + ChatClient.user1);

           
           
           if (orderlist != null && orderlist.getItems() != null) 
           {
               orderlist.getItems().clear();
           }
			
            MainPagesClientController MainPagesClientController = loader.getController();
            MainPagesClientController.loadUserClient(ChatClient.user1);
            MainPagesClientController.initialize(ChatClient.user1.getUsername(), ChatClient.user1.getaccountStatus(), ChatClient.user1.getBranch());

            
        } 
        catch (IOException e)
        {
            // Print the stack trace and show an error dialog
            e.printStackTrace();
            showAlert("Error", "Could not load the Start Order page.");
        }
    	

    }
    
    /**
     * Displays an alert dialog with the specified title and message.
     * 
     * @param title the title of the alert dialog
     * @param message the content of the alert dialog
     */
    private void showAlert(String title, String message) 
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    
    
    

    /**
     * Sets the orders and populates the TableView.
     * 
     * @param orders the list of orders to display in the TableView
     */
    public void setOrders(ObservableList<RestaurantOrders> orders) 
    {
        this.orders = orders;
        orderlist.setItems(orders);
        
		System.out.println("BOB SPOG"+ this.orders);

    }

}
