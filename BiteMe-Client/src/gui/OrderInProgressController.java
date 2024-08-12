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

import client.ChatClient;
import client.ClientUI;
import entities.BiteOptions;
import entities.RestaurantOrders;

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
                
                System.out.println("AGLLLLLLLLLLLL " + selectedOrder.getOrder_number());
                BiteOptions option = new BiteOptions(selectedOrder.getOrder_number(), BiteOptions.Option.UPDATE_ORDER_STATUS_CUSTOMER);
                ClientUI.chat.accept(option);
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
            // Optionally, you can add another alert for when no order is selected
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.setContentText("Please select an order from the list.");
            alert.showAndWait();
        }
    }
    
    



    @FXML
    private void handleRefresh(ActionEvent event)
    {
        // Implement the logic to refresh the list of orders
        // You might want to fetch the latest data from the database
        // Example:
        // orders = DBController.getOrdersInProgress();
        // orderlist.setItems(orders);
   
		BiteOptions option = new BiteOptions(ChatClient.user1.getUserId(), BiteOptions.Option.GET_USER_ORDERS);//kkkkkkk
	    ClientUI.chat.accept(option);
	    
        if (orderlist != null && orderlist.getItems() != null) 
        {
            orderlist.getItems().clear();
        }
	    
    	ChatClient.observableOrdersList.addAll(0, ChatClient.customer_all_orders1);
    	this.setOrders(ChatClient.observableOrdersList);
	    
	    
    
    
    
    }

    
    
    
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

            
           System.out.println("AGGGGGGGGGGGGGGGGG " + ChatClient.user1);

           //orderlist.
           
           
           if (orderlist != null && orderlist.getItems() != null) 
           {
               orderlist.getItems().clear();
           }
			
            MainPagesClientController MainPagesClientController = loader.getController();
            MainPagesClientController.loadUserClient(ChatClient.user1);
            MainPagesClientController.initialize(ChatClient.user1.getUsername(), ChatClient.user1.getaccountStatus(), ChatClient.user1.getBranch());

            
            
            // Uncomment and use if needed
        } 
        catch (IOException e)
        {
            // Print the stack trace and show an error dialog
            e.printStackTrace();
            showAlert("Error", "Could not load the Start Order page.");
        }
    	

    }
    
    
    private void showAlert(String title, String message) 
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    
    
    

    // A method to set orders and populate the TableView
    public void setOrders(ObservableList<RestaurantOrders> orders) 
    {
        this.orders = orders;
        orderlist.setItems(orders);
        
		System.out.println("BOB SPOG"+ this.orders);

    }

    // Optionally, a method to load the previous screen
    private void loadPreviousScreen() 
    {
        // Logic to load the previous screen, such as using FXMLLoader
        // Example:
        // FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/PreviousScreen.fxml"));
        // Parent root = loader.load();
        // Stage stage = new Stage();
        // stage.setTitle("Previous Screen");
        // stage.setScene(new Scene(root));
        // stage.show();
    }
}
