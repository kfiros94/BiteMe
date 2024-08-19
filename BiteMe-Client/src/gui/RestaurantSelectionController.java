package gui;

import java.io.IOException;
import java.util.ArrayList;

import client.ClientUI;
import client.ChatClient;

import entities.User;
import entities.BiteOptions;
import entities.Restaurant;
import entities.RestaurantOrders;
import javafx.application.Platform;
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
/**
 * Controller class for the restaurant selection screen in the application.
 * Handles the logic for selecting a restaurant and branch, and navigating between different screens in the GUI.
 * 
 * @author Kfir Amoyal
 * @author Noam Furman
 * @author Israel Ohayon
 * @author Eitan Zerbel
 * @author Yaniv Shatil
 * @author Omri Heit

 * @version August 2024
 */
public class RestaurantSelectionController
{

    private User UserCustomer;
    private Restaurant restaurant = new Restaurant(0,"0","0","0","0",0);
    private ArrayList<Restaurant> restaurantslocal = new  ArrayList<Restaurant>();
    private RestaurantOrders restaurantOrders = new RestaurantOrders();
	
    private  String selectedBranch;
    
    
    
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
    /**
     * Initializes the controller class. Sets up the event handlers for the
     * ComboBoxes used for selecting a branch and a restaurant.
     */
    @FXML
    private void initialize() 
    {
        branchComboBox.setOnAction(e -> updateRestaurantsForSelectedBranch());
        restaurantComboBox.setOnAction(e -> handleRestaurantSelection());
    }
    //SpongeBob
    /**
     * Handles the back button action, navigating the user back to the start order page.
     * @param event The ActionEvent triggered by clicking the back button.
     */
    @FXML
    private void handleBackButtonAction(ActionEvent event) 
    {
        // Logic for back button
        System.out.println("Back button clicked");
        
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
        
    }
    
    /**
     * Shows an alert dialog with the specified title and message.
     * @param title The title of the alert dialog.
     * @param message The message to display in the alert dialog.
     */ 
    private void showAlert(String title, String message) 
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    
    /**
     * Handles the next button action, navigating the user to the menu selection page.
     * @param event The ActionEvent triggered by clicking the next button.
     */
    @FXML
    private void handleNextButtonAction(ActionEvent event) 
    {
        // Logic for next button
        System.out.println("Next button clicked");
        
        System.out.println("print Restaurnttttt:"+restaurant.toString());

		BiteOptions option = new BiteOptions(restaurant.toString(), BiteOptions.Option.GET_SELECTED_REST_MENU);//kkkkkkk

		ClientUI.chat.accept(option);

		
		restaurantOrders.setRestaurant_id(restaurant.getRestaurantID());
		restaurantOrders.setRestaurant(restaurant.getName());
		restaurantOrders.setBranch(selectedBranch);
        System.out.println("print UPDATE RestaurantOrders:"+restaurantOrders.toString());
        
        FXMLLoader loader = new FXMLLoader();
        Pane root = null;
        Stage primaryStage = new Stage();
        try 
        {
            // Load the FXML file
            loader.setLocation(getClass().getResource("/gui/SelectFromRestMenu.fxml"));

            root = loader.load();
            
            // Hide the current window
            ((Node) event.getSource()).getScene().getWindow().hide();

            // Set the new stage
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
			primaryStage.setTitle("User-Portal -> New Order -> Select Restaurant -> Select Menu Items");
			
	            
			SelectFromRestMenuController SelectFromRestMenuController = loader.getController();
			SelectFromRestMenuController.loadRestaurant(restaurantslocal);
			SelectFromRestMenuController.loadRestaurantOrders(restaurantOrders); //טוען לדף הבא את המופע של ההזמנה,מדף לדף אני אוסף מידע
	        SelectFromRestMenuController.loadUserCustomer(UserCustomer);


            // Uncomment and use if needed
        } 
        catch (IOException e) 
        {
            // Print the stack trace and show an error dialog
            e.printStackTrace();
            showAlert("Error", "Could not load the Start Order page.");
        }

 
    }
    
    
    /**
     * Loads the available restaurants into the restaurant ComboBox.
     */
    private void loadRestaurants() 
    {
    	restaurantComboBox.getItems().clear();
        if (ChatClient.restaurants != null && !ChatClient.restaurants.isEmpty()) {
            for (Restaurant restaurant : ChatClient.restaurants) {
                restaurantComboBox.getItems().add(restaurant.getName());
            }
        } else {
            System.out.println("No restaurants available or restaurants not loaded yet.");
        }
         //Kfir
    }

    /**
     * Loads the available branches into the branch ComboBox.
     */
    private void loadBranches() 
    {
        branchComboBox.getItems().clear();
        branchComboBox.getItems().addAll("North", "South", "Central"); // Add all possible branches
        if (UserCustomer != null) {
            branchComboBox.setValue(UserCustomer.getBranch()); // Set the user's branch as default
        }
    }
    

    /**
     * Loads the user data into the controller and initializes the restaurant orders.
     * Fetches the list of restaurants from the server.
     * @param UserClient The user object containing the user data.
     */
    public void loadUserCustomer(User UserClient) 
    {
        this.UserCustomer = UserClient;
        System.out.println("Loading user customer: " + this.UserCustomer.toString());

        
        restaurantOrders.setUser_id(UserCustomer.getUserId());
        System.out.println("Loading user restaurantOrders: " + restaurantOrders.toString());


        // Fetch restaurants from server for all branches
        BiteOptions option = new BiteOptions("ALL", BiteOptions.Option.SELECT_RESTAURANT);
        ClientUI.chat.accept(option);//  Wait until reply from server

        restaurantslocal.addAll(0, ChatClient.restaurants);
        
        Platform.runLater(() -> {
            loadBranches();
            updateRestaurantsForSelectedBranch();
        });
    }
    
    /**
     * Loads the list of restaurants into the controller.
     * @param restaurants The list of restaurants to load.
     */ 
    public void loadRestaurant(ArrayList<Restaurant> restaurants) 
    {
        this.restaurantslocal = restaurants;
		System.out.println("Loaded restaurant: "+ this.restaurantslocal.toString());

    }
    /**
     * Updates the restaurant list in the ComboBox based on the selected branch.
     */
    public void updateRestaurantList() 
    {
        Platform.runLater(() -> {
            loadRestaurants();
        });
    }
    /**
     * Updates the list of restaurants in the restaurant ComboBox based on the selected branch.
     */
    private void updateRestaurantsForSelectedBranch() 
    {
        selectedBranch = branchComboBox.getValue();
        System.out.println("Selected Branch: " + selectedBranch);
        restaurantComboBox.getItems().clear();
        if (selectedBranch != null && ChatClient.restaurants != null) {
            for (Restaurant restaurant : ChatClient.restaurants) {
                if (restaurant.getBranch().equals(selectedBranch)) {
                    restaurantComboBox.getItems().add(restaurant.getName());
                }
            }
        }
        // Clear the selected restaurant when branch changes
        restaurantComboBox.setValue(null);
    }
    /**
     * Handles the selection of a restaurant from the ComboBox.
     */
    private void handleRestaurantSelection() 
    {
        String selectedRestaurant = restaurantComboBox.getValue();
        if (selectedRestaurant != null) {
            // Do something with the selected restaurant
            System.out.println("Selected restaurant: " + selectedRestaurant);
            
            restaurant.setName(selectedRestaurant);
            
			for (Restaurant restaurantToFind : ChatClient.restaurants) 
			{
				if(restaurant.getName().equals(restaurantToFind.getName()))
				{
					restaurant.setRestaurantID(restaurantToFind.getRestaurantID());
				}
			}
            
        }
    }
    
    
    /**
     * Reloads the restaurants and sets the branch in the ComboBox.
     * Fetches the list of restaurants from the server.
     */ 
    public void reloadRestaurantsAndSetBranch() {
        // Fetch restaurants from server for all branches
        BiteOptions option = new BiteOptions("ALL", BiteOptions.Option.SELECT_RESTAURANT);
        ClientUI.chat.accept(option);

        restaurantslocal.clear();
        restaurantslocal.addAll(ChatClient.restaurants);
        
        Platform.runLater(() -> {
            loadBranches();
            updateRestaurantsForSelectedBranch();
            if (UserCustomer != null) {
                branchComboBox.setValue(UserCustomer.getBranch());
            }
        });
    }
    
    
    
}
