package manager;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import java.io.IOException;
import client.ChatClient;
import client.ClientUI;
import entities.BiteOptions;
import entities.Customer;
import entities.Restaurant;
import entities.User;
import gui.MainPagesClientController;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
/**
 * Controller class for creating a new user in the application.
 * 
 * @author Kfir Amoyal
 * @author Noam Furman
 * @author Israel Ohayon
 * @author Eitan Zerbel
 * @author Yaniv Shatil
 * @author Omri Heit

 * @version August 2024
 */
public class CreateNewCustomerController {

    @FXML private TextField Username;
    @FXML private TextField Password;
    @FXML private ComboBox<String> emailComboBox;
    @FXML private ComboBox<String> BranchComboBox;
    @FXML private ComboBox<String> AccountTypeComboBox;
    @FXML private Label pageTitlelabel;
    @FXML private Button saveItamButton;
    @FXML private Button cancelButton;
    @FXML private Label messageScreen;
    @FXML private TextField PhoneNumber;
    @FXML private ComboBox<String> PermissionComboBox;
    @FXML private TextField RestNameText;
    @FXML private TextField RestAddressText;
    @FXML private TextField CreditText;
    @FXML private TextField AddressText;

    
    @FXML private Label lbRetaurantName;
    @FXML private Label lbRetaurantAddress;
    @FXML private Label lbCreditCard;
    @FXML private Label lbAddress;

    
    private User UserClient;
    
    private User UserEmpty;
    
    private User UpdateUserByEmail;
    
    private Customer NewCustomer;
    
    private Restaurant NewRestaurant;

    /**
     * Initializes the controller class. This method is automatically called
     * after the FXML file has been loaded.
     */
    @FXML
    private void initialize() {
      

        UserEmpty = new User(0, "-1", "-1", null, null, null, null, false, 0, null); // kkkkkkk

        BiteOptions option = new BiteOptions(UserEmpty.toString(), BiteOptions.Option.CREATE_USER);
        ClientUI.chat.accept(option);

       
        //Initialize the Branch ComboBox with values
        BranchComboBox.getItems().addAll("North", "South", "Central");
        AccountTypeComboBox.getItems().addAll("Private", "Branch Manager", "Business", "CEO", "supplier");
        PermissionComboBox.getItems().addAll("customer", "supplier", "admin");

        // Update emailComboBox with emails from ChatClient.userEmptyArry
        for (User user : ChatClient.userEmptyArry) {
            if (user.getEmail() != null && !user.getEmail().isEmpty()) {
                emailComboBox.getItems().add(user.getEmail());
            }
        }
        emailComboBox.setEditable(true);

        // Add listener to PermissionComboBox to show/hide fields based on selection
        PermissionComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            setFieldsVisibility(newValue);
        });

        // Initially hide all conditional fields
        setAllFieldsInvisible();
    }

    
    
    /**
     * Sets all conditional fields to invisible.
     * This is used to reset the form before showing relevant fields.
     */
    private void setAllFieldsInvisible() {
        lbRetaurantName.setVisible(false);
        lbRetaurantAddress.setVisible(false);
        lbCreditCard.setVisible(false);
        RestNameText.setVisible(false);
        RestAddressText.setVisible(false);
        CreditText.setVisible(false);
        AddressText.setVisible(false);
        lbAddress.setVisible(false);
    }
    /**
     * Sets the visibility of fields based on the selected permission.
     * 
     * @param permission The selected permission type.
     */
    private void setFieldsVisibility(String permission) {
        setAllFieldsInvisible();
        if ("customer".equals(permission)) 
        {
            setCustomerFieldsVisible(true);
        } 
        else if ("supplier".equals(permission)) 
        {
            setSupplierFieldsVisible(true);
        }
    }
    /**
     * Sets the visibility of customer-specific fields.
     * 
     * @param visible True to make fields visible, false to hide them.
     */
    private void setCustomerFieldsVisible(boolean visible) {
        lbCreditCard.setVisible(visible);
        CreditText.setVisible(visible);
        AddressText.setVisible(visible);
        lbAddress.setVisible(visible);
    }
    /**
     * Sets the visibility of supplier-specific fields.
     * 
     * @param visible True to make fields visible, false to hide them.
     */
    private void setSupplierFieldsVisible(boolean visible) {
        lbRetaurantName.setVisible(visible);
        lbRetaurantAddress.setVisible(visible);
        RestNameText.setVisible(visible);
        RestAddressText.setVisible(visible);
    }

    /**
     * Handles the save action when the save button is clicked.
     * Validates input fields and creates a new user in the system.
     * 
     * @param event The action event triggered by clicking the save button.
     */
    @FXML
    private void handleSave(ActionEvent event) {
        String username = Username.getText();
        String password = Password.getText();
        String email = emailComboBox.getEditor().getText();
        String phoneNumber = PhoneNumber.getText();
        String selectedBranch = BranchComboBox.getValue();
        String accountType = AccountTypeComboBox.getValue();
        String permission = PermissionComboBox.getValue();

        if (username.isEmpty() || password.isEmpty() || email.isEmpty() || phoneNumber.isEmpty() 
            || selectedBranch == null || accountType == null || permission == null) {
            messageScreen.setText("Please fill in all required fields.");
            return;
        }

        // Validate email format
        if (!isValidEmail(email)) {
            messageScreen.setText("Please enter a valid email address.");
            return;
        }

        // Additional validation for customer and supplier
        if ("customer".equals(permission)) {
            if (CreditText.getText().isEmpty()) {
                messageScreen.setText("Please enter credit card information.");
                return;
            }
        } else if ("supplier".equals(permission)) {
            if (RestNameText.getText().isEmpty() || RestAddressText.getText().isEmpty()) {
                messageScreen.setText("Please fill in all supplier fields.");
                return;
            }
        }
        

        
        UpdateUserByEmail = new User(0, username, password, email, phoneNumber, permission, selectedBranch, false, 0, accountType); // kkkkkkk

        
        BiteOptions option = new BiteOptions(UpdateUserByEmail.toString(), BiteOptions.Option.CLASIFY_UPDATE_USER_BY_EMAIL);
        ClientUI.chat.accept(option);
        
        

        if(permission.equals("customer"))
        {
        	
        	NewCustomer = new Customer(0,username,password,email,phoneNumber,selectedBranch,accountType,CreditText.getText(),AddressText.getText(),"null",0,ChatClient.UserIdUpdate);
              System.out.println("handleSave: "+NewCustomer);
              
              BiteOptions option1 = new BiteOptions(NewCustomer.toString(), BiteOptions.Option.UPDATE_CUSTOMER);
              ClientUI.chat.accept(option1);
    	
              
              
        }
        
        if(permission.equals("supplier"))
        {
        	
        	NewRestaurant = new Restaurant(0,RestNameText.getText(), RestAddressText.getText(), phoneNumber, selectedBranch, ChatClient.UserIdUpdate);
              System.out.println("handleSave2: "+NewRestaurant);
              
              
              BiteOptions option2 = new BiteOptions(NewRestaurant.toString(), BiteOptions.Option.UPDATE_RESTAURANT);
              ClientUI.chat.accept(option2);
    	
  
        }
        
        // Add logic to save the user data (e.g., to a database)
        messageScreen.setText("User " + username + " created successfully.");
    }
    
    
    /**
     * Validates the format of an email address.
     * 
     * @param email The email address to validate.
     * @return true if the email is valid, false otherwise.
     */
    private boolean isValidEmail(String email) {
        // Simple email validation
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    /**
     * Handles the cancel action when the cancel button is clicked.
     * Returns to the main manager page.
     * 
     * @param event The action event triggered by clicking the cancel button.
     */
    @FXML
    private void handleCancel(ActionEvent event) {
        System.out.println("Back button clicked");

        FXMLLoader loader = new FXMLLoader();
        try {
            loader.setLocation(getClass().getResource("/manager/MainPagesManger.fxml"));
            Pane root = loader.load();
            
            ((Node) event.getSource()).getScene().getWindow().hide();

            Stage primaryStage = new Stage();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
            primaryStage.setTitle("Admin-Portal");

            System.out.println("AGGGGGGGGGGGGGGGGG " + ChatClient.user1);
            
            MainPagesMangerController mainPagesMangerController = loader.getController();
            mainPagesMangerController.loadUserClient(ChatClient.user1);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Could not load the Manager Main Page.");
        }
    }
    /**
     * Displays an alert dialog with the given title and message.
     * 
     * @param title The title of the alert dialog.
     * @param message The message to display in the alert dialog.
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
    /**
     * Loads the user client information into the controller.
     * 
     * @param userClient The User object representing the current client.
     */
    public void loadUserClient(User userClient) {
        this.UserClient = userClient;
        System.out.println("User client loaded: " + this.UserClient.toString());
    }
}