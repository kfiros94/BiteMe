package gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class SupplyConfigurationController {



    @FXML
    private DatePicker supplyDatePicker;

    @FXML
    private ComboBox<String> supplyMethodComboBox;

    @FXML
    private TextField selfPickupNameField;

    @FXML
    private TextField selfPickupPhoneField;

    @FXML
    private TextField deliveryNameField;

    @FXML
    private TextField deliveryPhoneField;

    @FXML
    private TextField deliveryAddressField;

    @FXML
    private TextField additionalInfoField;

    @FXML
    private Label deliveryFeeLabel;



    @FXML
    private void initialize()
    {
        // Initialize the combo box with supply method options
        supplyMethodComboBox.getItems().addAll("Self Pick-Up", "Delivery");

        // Set default values or other initialization logic here
    }

    @FXML
    private void handleNextButtonAction() 
    {
        // Handle the action when the "Next" button is clicked
        // You can add logic to validate input, save data, etc.
        System.out.println("Next button clicked");

        // Example: Retrieving values from the fields
        String supplyMethod = supplyMethodComboBox.getValue();
        String supplyTime = supplyDatePicker.getValue() != null ? supplyDatePicker.getValue().toString() : "";
        String selfPickupName = selfPickupNameField.getText();
        String selfPickupPhone = selfPickupPhoneField.getText();
        String deliveryName = deliveryNameField.getText();
        String deliveryPhone = deliveryPhoneField.getText();
        String deliveryAddress = deliveryAddressField.getText();
        String additionalInfo = additionalInfoField.getText();

        // Print or process the collected data
        System.out.println("Supply Method: " + supplyMethod);
        System.out.println("Supply Time: " + supplyTime);
        System.out.println("Self Pick-Up Name: " + selfPickupName);
        System.out.println("Self Pick-Up Phone: " + selfPickupPhone);
        System.out.println("Delivery Name: " + deliveryName);
        System.out.println("Delivery Phone: " + deliveryPhone);
        System.out.println("Delivery Address: " + deliveryAddress);
        System.out.println("Additional Info: " + additionalInfo);
    }

    @FXML
    private void handleBackButtonAction()
    {
        // Handle the action when the "Back" button is clicked
        System.out.println("Back button clicked");
        // Add logic to navigate back to the previous screen if applicable
    }
}
