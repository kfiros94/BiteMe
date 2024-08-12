package gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class EnterIPController {

    @FXML
    private TextField ipTextField;

    @FXML
    private Button continueButton;

    @FXML
    private Label errorLabel;

    @FXML
    public void handleContinue(ActionEvent event) {
        String ipAddress = ipTextField.getText().trim();
        if (ipAddress.isEmpty()) {
            errorLabel.setText("IP Address is required");
            return;
        }

        // Set the IP address in the ClientUI or ChatClient
        ClientUI.chat.setMyhost(ipAddress);

        // Load the next page for user details
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/LogInUser.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) continueButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Enter User Details");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            errorLabel.setText("Failed to load the login page.");
        }
    }
}
