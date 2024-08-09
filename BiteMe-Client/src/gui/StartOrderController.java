package gui;

import java.io.IOException;

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

    @FXML
    private void initialize() 
    {
        // Initialize any logic here if necessary
        incorrectUsernameLabel.setVisible(false);
        incorrectPasswordLabel.setVisible(false);
    }

    
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

            
            MainPagesClientController MainPagesClientController = loader.getController();
            MainPagesClientController.loadUserClient(UserCustomer);

            // Uncomment and use if needed
        } catch (IOException e) {
            // Print the stack trace and show an error dialog
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
    
    

    
    @FXML
    private void handleNextButtonAction(ActionEvent event) 
    {
        // Logic for next button
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

        if (isUsernameValid && isPasswordValid) 
        {
            // Proceed to the next step
            System.out.println("Valid credentials. Proceeding to the next step.");
            
            
            
            FXMLLoader loader = new FXMLLoader();
            Pane root = null;
            Stage primaryStage = new Stage();
            try {
                // Load the FXML file
                loader.setLocation(getClass().getResource("/gui/RestaurantSelection.fxml"));

                root = loader.load();
                
                // Hide the current window
                ((Node) event.getSource()).getScene().getWindow().hide();

                // Set the new stage
                Scene scene = new Scene(root);
                primaryStage.setScene(scene);
                
    			primaryStage.setTitle("User-Portal -> New Order -> Select Restaurant");

                
                RestaurantSelectionController RestaurantSelectionController = loader.getController();
                RestaurantSelectionController.loadUserCustomer(UserCustomer);
                
                primaryStage.show();


                // Uncomment and use if needed
            } 
            catch (IOException e) 
            {
                // Print the stack trace and show an error dialog
                e.printStackTrace();
                showAlert("Error", "Could not load the Start Order page.");
            }
			
            
            
        }
    }

    private boolean validateUsername(String username) 
    {
        return UserCustomer.getUsername().equals(username) && username != null && !username.trim().isEmpty();
    }

    private boolean validatePassword(String password) 
    {
        // Replace with your actual password validation logic
        return UserCustomer.getPassword().equals(password) && password != null && !password.trim().isEmpty();
    }
    
    
    public void loadUserCustomer(User UserClient) 
    {
        this.UserCustomer = UserClient;
		System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb"+ this.UserCustomer.toString());

    }
    
    
}
