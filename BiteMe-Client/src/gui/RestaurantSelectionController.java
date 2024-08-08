package gui;

import java.io.IOException;
import java.util.ArrayList;

import client.ClientUI;
import client.ChatClient;

import entities.User;
import entities.BiteOptions;
import entities.Restaurant;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class RestaurantSelectionController
{

    private User UserCustomer;
    private Restaurant restaurant = new Restaurant(0,"0","0","0","0",0);
    private ArrayList<Restaurant> restaurantslocal = new  ArrayList<Restaurant>();
	
    @FXML
    private ComboBox<String> restaurantComboBox;

    @FXML
    private ComboBox<String> branchComboBox;

    @FXML
    private Button backButton;

    @FXML
    private Button nextButton;

    @FXML
    private Label selectRestaurantLabel;

    @FXML
    private Label selectBranchLabel;

    @FXML
    private void initialize() 
    {
        // Initialize any logic here if necessary
        loadRestaurants();
        restaurantComboBox.setOnAction(e -> loadBranches());
    }

    @FXML
    private void handleBackButtonAction(ActionEvent event) 
    {
        // Logic for back button
        System.out.println("Back button clicked");
        
        
        //aaaaaaaaaaaaaaaaaaaa
        FXMLLoader loader = new FXMLLoader();
        Pane root = null;
        Stage primaryStage = new Stage();
        try 
        {
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

            
            StartOrderController StartOrderController = loader.getController();
            StartOrderController.loadUserCustomer(UserCustomer);

            // Uncomment and use if needed
        } 
        catch (IOException e) 
        {
            // Print the stack trace and show an error dialog
            e.printStackTrace();
            showAlert("Error", "Could not load the Start Order page.");
        }
        
        //aaaaaaaaaaaaaaaaaaaaaa
        
    }
    
    
    private void showAlert(String title, String message) 
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    

    @FXML
    private void handleNextButtonAction(ActionEvent event) 
    {
        // Logic for next button
        System.out.println("Next button clicked");
        
        
        //HHHHHHHHHHHHHHHHHHHHHHHHHHHHHH
		BiteOptions option = new BiteOptions(restaurant.toString(), BiteOptions.Option.SELECT_RESTAURANT);//kkkkkkk

		ClientUI.chat.accept(option);

        //HHHHHHHHHHHHHHHHHHHHHHHHHHHHH
        
        

        String selectedRestaurant = restaurantComboBox.getValue();
        String selectedBranch = branchComboBox.getValue();

        if (selectedRestaurant == null || selectedBranch == null) 
        {
            System.out.println("Please select both a restaurant and a branch.");
            // Optionally, display an alert to the user
            return;
        }

        // Proceed to the next step
        System.out.println("Selected Restaurant: " + selectedRestaurant);
        System.out.println("Selected Branch: " + selectedBranch);
        // Add your navigation logic here
    }

    private void loadRestaurants() 
    {
        System.out.println("AAAAXXXXSelected Restaurant: XXXXXXX ");

       //Replace with actual data loading logic
      // restaurantComboBox.getItems().addAll("Restaurant A", "Restaurant B", "Restaurant C");
     //  restaurantComboBox.getItems().addAll(ChatClient.restaurants.get(0).getName());

    	//restaurantComboBox.getItems().clear(); // Clear any existing items
        
       // restaurantslocal=ChatClient.restaurants;
        restaurantslocal.addAll(ChatClient.restaurants);
        
    	int i = 0;
    	
        System.out.println("BBBBBXXXXSelected Restaurant: " + ChatClient.restaurants.toString());

    	
        for ( i = 0; i < ChatClient.restaurants.size(); i++) 
        {
            String restaurantName = ChatClient.restaurants.get(i).getName();
            System.out.println("AAAAXXXXSelected Restaurant: " + restaurantName);
            restaurantComboBox.getItems().add(restaurantName);
        }
        
     //  restaurantslocal.addAll(ChatClient.restaurants);
    }

    
    private void loadBranches() 
    {
        // Clear previous branches
        branchComboBox.getItems().clear();

        // Replace with actual data loading logic
        if ("Restaurant A".equals(restaurantComboBox.getValue())) 
        {
            branchComboBox.getItems().addAll("Branch A1", "Branch A2");
        } 
        else if ("Restaurant B".equals(restaurantComboBox.getValue())) 
        {
            branchComboBox.getItems().addAll("Branch B1", "Branch B2");
        } 
        else if ("Restaurant C".equals(restaurantComboBox.getValue())) 
        {
            branchComboBox.getItems().addAll("Branch C1", "Branch C2");
        }
    }
    
    
    public void loadUserCustomer(User UserClient) 
    {
        this.UserCustomer = UserClient;
		System.out.println("cccccccccccccccccccccccccccccccccccccc"+ this.UserCustomer.toString());

    }
    
    
    public void loadRestaurant(ArrayList<Restaurant> restaurants) 
    {
        this.restaurantslocal = restaurants;
		System.out.println("uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu"+ this.restaurantslocal.toString());

    }
    
    
    
    
}
