package gui;

import java.io.IOException;

import client.ChatClient;
import client.ClientUI;
import entities.BiteOptions;
import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
/**
 * Controller class for handling the Start Order GUI.
 * This class manages the events and interactions for the Start Order screen.
 * 
 * @author Kfir Amoyal
 * @author Noam Furman
 * @author Israel Ohayon
 * @author Eitan Zerbel
 * @author Yaniv Shatil
 * @author Omri Heit

 * @version August 2024
 */
public class StartOrderController 
{
	
    private User UserCustomer;


    @FXML
    private Label incorrectUsernameLabel;

    @FXML
    private Label incorrectPasswordLabel;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private Button backButton;

    @FXML
    private Button nextButton;
    /**
     * Initializes the Start Order controller.
     * This method is automatically called after the FXML file has been loaded.
     */
    @FXML
    private void initialize() 
    {
        // Initialize any logic here if necessary
        incorrectUsernameLabel.setVisible(false);
        incorrectPasswordLabel.setVisible(false);
    }

    /**
     * Handles the action when the back button is clicked.
     * This method navigates the user back to the main client page.
     *
     * @param event The event that triggered this action.
     */
    @FXML
    private void handleBackButtonAction(ActionEvent event) 
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

            
            System.out.println("print for UserCustomer: " + UserCustomer);
        

			
            MainPagesClientController MainPagesClientController = loader.getController();
            MainPagesClientController.loadUserClient(ChatClient.user1);
            MainPagesClientController.initialize(UserCustomer.getUsername(), UserCustomer.getaccountStatus(), UserCustomer.getBranch());

            
            
            
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Could not load the Start Order page.");
        }
    	

    }
    /**
     * Shows an alert dialog with the specified title and message.
     *
     * @param title   The title of the alert dialog.
     * @param message The message to be displayed in the alert dialog.
     */
    private void showAlert(String title, String message) 
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    

    /**
     * Handles the action when the next button is clicked.
     * This method validates the user input and navigates the user to the restaurant selection page.
     *
     * @param event The event that triggered this action.
     */   
    @FXML
    private void StartOrderhandleNextButtonAction(ActionEvent event) 
    {
        System.out.println("Next button clicked");

        String username = usernameField.getText();
        String password = passwordField.getText();

        boolean isUsernameValid = validateUsername(username);
        boolean isPasswordValid = validatePassword(password);

        if (!isUsernameValid) 
        {
            incorrectUsernameLabel.setVisible(true);
        } 
        else 
        {
            incorrectUsernameLabel.setVisible(false);
        }

        if (!isPasswordValid) 
        {
            incorrectPasswordLabel.setVisible(true);
        } 
        else 
        {
            incorrectPasswordLabel.setVisible(false);
        }

        if (isUsernameValid && isPasswordValid && UserCustomer != null) 
        {
            FXMLLoader loader = new FXMLLoader();
            Pane root = null;
            Stage primaryStage = new Stage();
            try {
                loader.setLocation(getClass().getResource("/gui/RestaurantSelection.fxml"));
                root = loader.load();
                
                // Hide the current window
                ((Node) event.getSource()).getScene().getWindow().hide();

                // Set the new stage
                Scene scene = new Scene(root);
                primaryStage.setScene(scene);
                primaryStage.setTitle("User-Portal -> New Order -> Select Restaurant");

                RestaurantSelectionController restaurantSelectionController = loader.getController();
                restaurantSelectionController.loadUserCustomer(UserCustomer);

                // Show the new stage
                primaryStage.show();

            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Error", "Could not load the Restaurant Selection page.");
            }
        }
        else if (UserCustomer == null)
        {
            showAlert("Error", "User information is not loaded properly.");
        }
    }
    /**
     * Validates the entered username against the stored user information.
     *
     * @param username The entered username.
     * @return true if the username is valid, false otherwise.
     */
    private boolean validateUsername(String username) 
    {
        return UserCustomer.getUsername().equals(username) && username != null && !username.trim().isEmpty();
    }
    /**
     * Validates the entered password against the stored user information.
     *
     * @param password The entered password.
     * @return true if the password is valid, false otherwise.
     */
    private boolean validatePassword(String password) 
    {
        return UserCustomer.getPassword().equals(password) && password != null && !password.trim().isEmpty();
    }
    
    /**
     * Loads the user information into the controller.
     *
     * @param UserClient The user information to be loaded.
     */
    public void loadUserCustomer(User UserClient) 
    {
        this.UserCustomer = UserClient;
		System.out.println("Loaded user's info: "+ this.UserCustomer.toString());

    }
    
    
}