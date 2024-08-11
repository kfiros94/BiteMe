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

    @FXML
    private void initialize() 
    {
        branchComboBox.setOnAction(e -> updateRestaurantsForSelectedBranch());
        restaurantComboBox.setOnAction(e -> handleRestaurantSelection());
    }
    //SpongeBob
    /*
    private void loadRestaurantsForBranch() {
        String selectedBranch = branchComboBox.getValue();
        restaurantComboBox.getItems().clear();
        if (selectedBranch != null && ChatClient.restaurants != null && !ChatClient.restaurants.isEmpty()) {
            for (Restaurant restaurant : ChatClient.restaurants) {
                if (restaurant.getBranch().equals(selectedBranch)) {
                    restaurantComboBox.getItems().add(restaurant.getName());
                }
            }
        } else {
            System.out.println("No restaurants available or restaurants not loaded yet.");
        }
    }
    */

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
        System.out.println("print Restaurnttttt:"+restaurant.toString());

		BiteOptions option = new BiteOptions(restaurant.toString(), BiteOptions.Option.GET_SELECTED_REST_MENU);//kkkkkkk

		ClientUI.chat.accept(option);
		
		//aaaaa
		
		restaurantOrders.setRestaurant_id(restaurant.getRestaurantID());
		restaurantOrders.setRestaurant(restaurant.getName());
		restaurantOrders.setBranch(selectedBranch);
        System.out.println("print UPDATE RestaurantOrders:"+restaurantOrders.toString());

		
		//aaaaa

		//selectedBranch
        //HHHHHHHHHHHHHHHHHHHHHHHHHHHHH
        
        

        
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

            // Uncomment and use if needed
        } 
        catch (IOException e) 
        {
            // Print the stack trace and show an error dialog
            e.printStackTrace();
            showAlert("Error", "Could not load the Start Order page.");
        }

 
    }
    
    
    
    
    

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

    
    private void loadBranches() 
    {
        branchComboBox.getItems().clear();
        branchComboBox.getItems().addAll("North", "South", "Central"); // Add all possible branches
        if (UserCustomer != null) {
            branchComboBox.setValue(UserCustomer.getBranch()); // Set the user's branch as default
        }
    }
    

    //זאת מתודת עזר שקוראים לה מהקונטרולר הקודם לאחר יצירת מופע של הקונטרולר הזה של בחירת מסעדה
    //מה שקורה כאן זה 2 דברים במקביל שזה ממש טוב לנו
    //טוענים מידע מהקונטרולר של התחלת הזמנה לשדה משתמש של הקונטרולר הזה 
    //וגם אנחנו מבצעים פנייה לשרת לבקש את כל המסעדות שיש במסד נתונים כהכנה לעבודה עם הדף הנוכחי
    public void loadUserCustomer(User UserClient) 
    {
        this.UserCustomer = UserClient;
        System.out.println("Loading user customer: " + this.UserCustomer.toString());
        
        //aaaaaaaaaaaaaa
        
        restaurantOrders.setUser_id(UserCustomer.getUserId());
        System.out.println("Loading user restaurantOrders: " + restaurantOrders.toString());

        //aaaaaaaaaaaaa

        // Fetch restaurants from server for all branches
        BiteOptions option = new BiteOptions("ALL", BiteOptions.Option.SELECT_RESTAURANT);
        ClientUI.chat.accept(option);// המתנה פעילה עד לתשובה מהשרת

        restaurantslocal.addAll(0, ChatClient.restaurants);
        
        Platform.runLater(() -> {
            loadBranches();
            updateRestaurantsForSelectedBranch();
        });
    }
    
    
    public void loadRestaurant(ArrayList<Restaurant> restaurants) 
    {
        this.restaurantslocal = restaurants;
		System.out.println("uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu"+ this.restaurantslocal.toString());

    }
    
    public void updateRestaurantList() 
    {
        Platform.runLater(() -> {
            loadRestaurants();
        });
    }
    
    private void updateRestaurantsForSelectedBranch() 
    {
        selectedBranch = branchComboBox.getValue();//aaaaaaaaaaaaaaaaaaaaaa
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
    
    
    
    
    
    //UUUUUUUUUUUUUUUUUUUUUU
    
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
    
    //UUUUUUUUUUUUUUUUUUUUU
    
    
}
