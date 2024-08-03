package supplier;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import client.ChatClient;
import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import logic.User;

public class MainPageSupplierController {

	@FXML
	private Button showOrdersButton;

	@FXML
	public void showOrders() {
		// Add logic to show orders
	}
	
	/*from main paige clinet controler
	 *  @FXML
    private void CloseButton(ActionEvent event) {
    	
    	// Send logout request to server with the user's ID
    	int usertologout=ChatClient.user1.getUserId();
        List<String> logoutRequest = Arrays.asList("logoutrequest", String.valueOf(usertologout));
        ClientUI.chat.accept(logoutRequest);
        
        ChatClient.user1 = new User(0, null, null, null, null, null, null, false, 0);// מאפס את המשתמש בשביל הניסיון התחברות הבא בתור

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/LogInUser.fxml"));

            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Log-In BiteMe");

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
	 */

}
