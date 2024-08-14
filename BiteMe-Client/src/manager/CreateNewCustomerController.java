package manager;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.io.IOException;

import client.ChatClient;
import entities.User;
import gui.MainPagesClientController;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;

public class CreateNewCustomerController {

    @FXML
    private TextField Username;

    @FXML
    private TextField Password;

    @FXML
    private TextField email;

    @FXML
    private ComboBox<String> Branch;

    @FXML
    private TextField accountType;

    @FXML
    private Label pageTitlelabel;

    @FXML
    private Button saveItamButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Label messageScreen;

    @FXML
    private TextField PhoneNumber;

    @FXML
    private TextField CreditCard;

    @FXML
    private TextField address;

    @FXML
    private TextField workAddress;
    
    
    private User UserClient; 


    // Method to initialize any additional settings or configurations
    @FXML
    private void initialize() {
        // Example: Initialize the Branch ComboBox with values
        Branch.getItems().addAll("North", "South", "East", "West");
    }

    // Event handler for the Save button
    @FXML
    private void handleSave(ActionEvent event) {
        // Perform save operation here, for example:
        String username = Username.getText();
        String password = Password.getText();
        String emailAddress = email.getText();
        String phoneNumber = PhoneNumber.getText();
        String creditCard = CreditCard.getText();
        String homeAddress = address.getText();
        String workAddr = workAddress.getText();
        String selectedBranch = Branch.getValue();
        String accountTypeValue = accountType.getText();

        // Add your logic to save this data (e.g., to a database)
        // For now, we'll just display a message
        if (username.isEmpty() || password.isEmpty() || emailAddress.isEmpty()) {
            messageScreen.setText("Please fill in all required fields.");
        } else {
            // Example: Save the data (you can replace this with your database logic)
            messageScreen.setText("Customer " + username + " created successfully.");
        }
    }

    // Event handler for the Cancel button
    @FXML
    private void handleCancel(ActionEvent event) 
    {
    	/*
        // Close the current window
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    	*/
    	
    	//ggggggggggggggg
    	
        // Logic for back button
        System.out.println("Back button clicked");

        FXMLLoader loader = new FXMLLoader();
        Pane root = null;
        Stage primaryStage = new Stage();
        try {
            // Load the FXML file
            loader.setLocation(getClass().getResource("/manager/MainPagesManger.fxml"));

            root = loader.load();
            
            // Hide the current window
            ((Node) event.getSource()).getScene().getWindow().hide();

            // Set the new stage
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
			primaryStage.setTitle("Admin-Portal");

            
           System.out.println("AGGGGGGGGGGGGGGGGG " + ChatClient.user1);

			
           MainPagesMangerController MainPagesMangerController = loader.getController();
           // MainPagesClientController.loadUserClient(UserCustomer);
           MainPagesMangerController.loadUserClient(ChatClient.user1);

            
            
            // Uncomment and use if needed
        } catch (IOException e)
        {
            // Print the stack trace and show an error dialog
            e.printStackTrace();
            showAlert("Error", "Could not load the Start Order page.");
        }
    	
    	//ggggggggggggggg
    	
    
    }
    
    private void showAlert(String title, String message) 
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    
    public void loadUserClient(User UserClient) 
    {
        this.UserClient = UserClient;
		System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"+ this.UserClient.toString());

    }
    
    
    
}
