package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import entities.BiteOptions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import entities.ClientInfo;
import entities.RestaurantOrders;
import entities.User;


/**
 * Controller class for the main client page.
 * Handles interactions with the user interface for starting orders, viewing orders in progress,
 * viewing order history, and logging out.
 * 
 * @author Kfir Amoyal
 * @author Noam Furman
 * @author Israel Ohayon
 * @author Eitan Zerbel
 * @author Yaniv Shatil
 * @author Omri Heit

 * @version August 2024
 */ 
public class MainPagesClientController 
{
	
	@FXML
	private Label lbUserName;
	@FXML
	private Label lbMyStatus;
	@FXML
	private Label lbMybranch;
	@FXML
	private ImageView imageView;
	
    private RestaurantOrders s;// Field for storing the current restaurant order
    private User UserClient;// Field for storing the current user


 
    /**
     * Initializes the controller with user-specific information.
     * 
     * @param userName The user's name.
     * @param accountStatus The user's account status.
     * @param branch The branch the user is associated with.
     */ 
 	public void initialize(String userName, String accountStatus , String branch)
 	{
 		
 		lbUserName.setText(userName);
 		lbMyStatus.setText(accountStatus);
 		lbMybranch.setText(branch);
 		
 		
 	}
 	
 	/**
     * Loads a restaurant order into the controller.
     * 
     * @param s1 The restaurant order to be loaded.
     */
    public void loadOrder(RestaurantOrders s1)
    {
        this.s = s1;
      
    }
    
    /**
     * Loads the client user into the controller.
     * 
     * @param UserClient The user to be loaded.
     */ 
    public void loadUserClient(User UserClient) 
    {
        this.UserClient = UserClient;
		System.out.println("The UserClient: "+ this.UserClient.toString());

    }

    
    /**
     * Handles the action of starting a new order.
     * Loads the StartOrder.fxml and switches to the start order screen.
     * 
     * @param event The event triggered by clicking the start order button.
     */
    @FXML
    private void StartOrder(ActionEvent event) 
    {
        FXMLLoader loader = new FXMLLoader();
        Pane root = null;
        Stage primaryStage = new Stage();
        try {
            // Load the FXML file
            loader.setLocation(getClass().getResource("/gui/StartOrder.fxml"));

            root = loader.load();
            
            // Hide the current window
            ((Node) event.getSource()).getScene().getWindow().hide();

            // Set the new stage
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
            primaryStage.setTitle("User-Portal -> New Order");
            
    		System.out.println("JJJJJJJJJJJJ"+ UserClient);
    		System.out.println("AJJJJJJJJJJJJ"+ ChatClient.user1);



            StartOrderController startOrderController = loader.getController();
            
            
            if (ChatClient.user1 != null) 
            {
                startOrderController.loadUserCustomer(ChatClient.user1);
            } 
            else 
            {
                System.err.println("Error: UserClient is null in MainPagesClientController");
            }
            

        } 
        catch (IOException e) 
        {
            e.printStackTrace();
            showAlert("Error", "Could not load the Start Order page.");
        }
    }
    
    
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    
    
    /**
     * Handles the action of viewing orders in progress.
     * Loads the OrderInProgress.fxml and switches to the order in progress screen.
     * 
     * @param event The event triggered by clicking the order in progress button.
     */ 
    @FXML
    private void OrderInProgress(ActionEvent event) 
    {
        FXMLLoader loader = new FXMLLoader();
        Pane root = null;
        Stage primaryStage = new Stage();
        try {
            // Load the FXML file
            loader.setLocation(getClass().getResource("/gui/OrderInProgress.fxml"));

            root = loader.load();
            
            // Hide the current window
            ((Node) event.getSource()).getScene().getWindow().hide();

            // Set the new stage
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
            primaryStage.setTitle("User-Portal -> Order In Progress");
            
    		System.out.println("WWWW"+ UserClient);
    		System.out.println("AWWWWW"+ ChatClient.user1);

            
            
            if (ChatClient.user1 != null) 
            {

            	
        		BiteOptions option = new BiteOptions(ChatClient.user1.getUserId(), BiteOptions.Option.GET_USER_ORDERS);//kkkkkkk
        	    ClientUI.chat.accept(option);
            	
            	
            	OrderInProgressController OrderInProgressController = loader.getController();
            	ChatClient.observableOrdersList.addAll(0, ChatClient.customer_all_orders1);
            	OrderInProgressController.setOrders(ChatClient.observableOrdersList);
            	
            } 
            else 
            {
                System.err.println("Error: UserClient is null in MainPagesClientController");
            }
            

        } 
        catch (IOException e) 
        {
            e.printStackTrace();
            showAlert("Error", "Could not load the Start Order page.");
        }
    	
    	
    }
    
    /**
     * Handles the action of viewing the order history.
     * Currently not implemented.
     * 
     * @param event The event triggered by clicking the view order history button.
     */
    @FXML
    private void ViewOrderHistory(ActionEvent event) 
    {
    	
    }
    

    /**
     * Handles the action of logging out.
     * Logs the user out and redirects to the login screen.
     * 
     * @param event The event triggered by clicking the close button.
     * @throws IOException If an input or output exception occurs.
     */
    @FXML
    private void CloseButton(ActionEvent event) throws IOException 
    {
    	
		BiteOptions option = new BiteOptions(ChatClient.user1.toString(), BiteOptions.Option.LOGOUT);//kkkkkkk
	    ClientUI.chat.accept(option);

        
        ChatClient.user1 = new User(0, null, null, null, null, null, null, false, 0,null);// מאפס את המשתמש בשביל הניסיון התחברות הבא בתור

        try 
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/LogInUser.fxml"));

            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Log-In BiteMe");

            stage.setScene(scene);
            stage.show();
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
   
    
    }

    
}
