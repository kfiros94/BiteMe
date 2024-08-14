
package supplier;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import client.ChatClient;
import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import entities.*;
/**
 * Controller class for the main page of the supplier's interface.
 * Handles actions related to viewing orders, editing items, and logging out.
 * 
 * @author Kfir Amoyal
 * @author Noam Furman
 * @author Israel Ohayon
 * @author Eitan Zerbel
 * @author Yaniv Shatil
 * @author Omri Heit

 * @version August 2024
 */
public class MainPageSupplierController {
	
	final String ANSI_RESET = "\u001B[0m";
	final String ANSI_PURPLE = "\u001B[35m";
	
	@FXML
	private Button showOrdersButton;
	@FXML
	private Button EditItamButton;
	@FXML
	private Button LogOutButton;
		
	@FXML
    private Label resnamelbl;
	
	private User UserSupllier;
    private Restaurant Srestaurant;
    public static MainPageSupplierController controller;

    /**
     * Initializes the controller class.
     * Sets the static controller reference to this instance.
     */
    @FXML
    public void initialize() {
        controller = this;
    }
    
    /**
     * Sets the supplier user for this controller.
     *
     * @param userSupplier The user who is a supplier.
     */
    public void setUserSupplier(User userSupplier) {
        this.UserSupllier = userSupplier;
    	System.out.println(ANSI_PURPLE + "MainPageSupplierController" + ANSI_RESET + ": initialized for supplier - " + UserSupllier.getUsername());

    }
    
    /**
     * Sets the restaurant for this controller. If the restaurant is null,
     * it will request the restaurant data from the server based on the supplier ID.
     *
     * @param restaurant The restaurant associated with the supplier.
     */
    public void setRestaurant(Restaurant restaurant) {
    	//Handles the case for LoginUserController, retrieves the restaurant by supplier ID from the database
    	if (restaurant==null) { 
       	 this.Srestaurant = new Restaurant();
    	 Srestaurant.setSupplierID(UserSupllier.getUserId());
 	    try {
 	        System.out.println("Sending request to server with supplier ID: " + UserSupllier.getUserId());
 			BiteOptions restaurantLogin = new BiteOptions(Srestaurant.toString(), BiteOptions.Option.LOGIN_RESTAURANT);

 	        ClientUI.chat.accept(restaurantLogin);
 	        
 	        System.out.println("Request to activate getRestaurantBySupplierId sent to server");
 	    } catch (Exception e) {
 	        e.printStackTrace();
 	        System.out.println("Failed to send request to server.");
 	    }

 	    System.out.println(Srestaurant);
 	    resnamelbl.setText(Srestaurant.getName());
        	
        }
    	// Set received restaurant details
        else {
        	
        	this.Srestaurant = restaurant;      
            if (resnamelbl != null) {
                resnamelbl.setText(restaurant.getName());
            }
        }
            
    }
    
    
	


    /**
     * Handles the action when the "Show Orders" button is clicked.
     * Loads the Supplier_OrderManagement.fxml and passes the supplier and restaurant information to the next controller.
     *
     * @param event The action event triggered by clicking the button.
     */
	@FXML
	public void showOrders(ActionEvent event) {
		 System.out.println(Srestaurant.getName() + " enters Supplier_OrderManagementController");
		    try {
		        FXMLLoader loader = new FXMLLoader(getClass().getResource("/supplier/Supplier_OrderManagement.fxml"));
		        Parent root = loader.load();

		        Supplier_OrderManagementController controller = loader.getController();
		        
		        // Set User and restaurant to the controller-Supplier_OrderManagementController
		        controller.setSupplier(UserSupllier);
		        controller.setRestaurant(Srestaurant); 


		        Scene scene = new Scene(root);
		        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		        stage.setScene(scene);
		        stage.setTitle("Order Management - BiteMe");
		        stage.show();
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
	}
	
    
    
    /**
     * Handles the action when the "Edit Item" button is clicked.
     * Loads the SupplierEditItam.fxml and passes the supplier and restaurant information to the next controller.
     *
     * @param event The action event triggered by clicking the button.
     */
    @FXML
    public void EditItamButton(ActionEvent event) 
    {
        
    	System.out.println(Srestaurant.getName()+" want to edit manue");
   try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/supplier/SupplierEditItam.fxml"));
            Parent root = loader.load();

	        // Set User and restaurant to the controller-SupplierEditItamController
            SupplierEditItamController controller = loader.getController();
            controller.setUserRestaurant(Srestaurant,UserSupllier);
            //controller.loadMenuItems();

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Edit Item - BiteMe");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
    /**
     * Handles the action when the "Log Out" button is clicked.
     * Logs out the user and resets the current user and restaurant information.
     * Loads the LogInUser.fxml for the login screen.
     *
     * @param event The action event triggered by clicking the button.
     */
	 @FXML
    private void LogOutButton(ActionEvent event) 
	 { 	
    	BiteOptions userLOGOUT = new BiteOptions(ChatClient.user1.toString(), BiteOptions.Option.LOGOUT);       
        ChatClient.user1 = new User(0, null, null, null, null, null, null, false, 0,null);// מאפס את המשתמש בשביל הניסיון התחברות הבא בתור
        Srestaurant=null;
        UserSupllier=null;////?
        ClientUI.chat.accept(userLOGOUT);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/LogInUser.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Log-In BiteMe");

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}

